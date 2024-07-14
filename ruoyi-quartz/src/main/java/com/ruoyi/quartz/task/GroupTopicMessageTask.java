package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.ruoyi.common.core.domain.entity.TgMessageTask;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.bot.TgLongPollingBot;
import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.domain.TgMessageInfoTask;
import com.ruoyi.system.handler.TRX2EneryTransferHandler;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.mapper.TgMessageTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component("groupTopicMessageTask")
public class GroupTopicMessageTask {
    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;
    @Autowired
    private TRX2EneryTransferHandler trx2EneryTransferHandler;

    @Autowired
    private TgMessageTaskMapper tgMessageTaskMapper;

    @Autowired(required = false)
    private TgLongPollingBot longPollingBot;
    @Autowired
    private SendContent sendContent;

    public void doGroupTopicMessageTask() {

        TgMessageTask tgMessageTask = new TgMessageTask();
        tgMessageTask.setStatus(1L);
        tgMessageTask.setNextRunTime(DateUtils.getNowDate());
        List<TgMessageInfoTask> tgMessageInfoTaskList = tgMessageTaskMapper.selectTgMessageTaskAndInfoList(tgMessageTask);

        List<CompletableFuture> completableFutureList = new ArrayList<>();

        for (TgMessageInfoTask tgMessageInfoTask : tgMessageInfoTaskList) {

            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                try {
                    doSendTgMessageInfo(tgMessageInfoTask);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).exceptionally(e -> {
                throw new RuntimeException("广播TG消息异常", e);
            });
            completableFutureList.add(completableFuture);
        }

        CompletableFuture.allOf(completableFutureList.stream().toArray(CompletableFuture[]::new)).join();
    }

    private void doSendTgMessageInfo(TgMessageInfoTask tgMessageInfoTask) throws TelegramApiException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        Integer executionStragy = tgMessageInfoTask.getExecutionStragy();
        Integer messageType = tgMessageInfoTask.getMessageType();
        TgMessageTask tgMessageTask = new TgMessageTask();
        tgMessageTask.setIdTgMessageTask(tgMessageInfoTask.getIdTgMessageTask());
        if (0 == executionStragy) {
            //周期执行
            doSendTgMessageInfo(tgMessageInfoTask, messageType);

            DateTime nextRuntime = getNextRuntime(tgMessageInfoTask);
            tgMessageTask.setNextRunTime(nextRuntime);
        } else {
            //执行一次的
//            sendCommonTgMessage(tgMessageInfoTask);
            doSendTgMessageInfo(tgMessageInfoTask, messageType);
            tgMessageTask.setStatus(3L);
        }
        tgMessageTaskMapper.updateTgMessageTask(tgMessageTask);
    }

    private void doSendTgMessageInfo(TgMessageInfoTask tgMessageInfoTask, Integer messageType) throws TelegramApiException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        if (1 == messageType) {
            //trx兑换消息
            trx2EneryTransferHandler.topicGroupMessage(tgMessageInfoTask);
        } else if (2 == messageType) {
            //usdt兑换消息
            usdt2TrxTransferHandler.topicGroupMessage(tgMessageInfoTask);
        } else {
            //普通消息
            sendCommonTgMessage(tgMessageInfoTask);
        }
    }

    private  DateTime getNextRuntime(TgMessageInfoTask tgMessageInfoTask) {
        String intervalTime = tgMessageInfoTask.getIntervalTime();
        int length = intervalTime.length();
        String intervalTimeNumber = intervalTime.substring(0, length - 1);
        String intervalTimeUnit = intervalTime.substring(length - 1, length);
        DateField dateField = null;
        if ("h".equalsIgnoreCase(intervalTimeUnit)) {
            dateField = DateField.HOUR;
        }else if ("m".equalsIgnoreCase(intervalTimeUnit)){
             dateField = DateField.MINUTE;
        }else if ("s".equalsIgnoreCase(intervalTimeUnit)){
             dateField = DateField.SECOND;
        }
        DateTime nextRuntime = DateUtil.offset(DateUtil.date(), dateField, NumberUtil.parseInt(intervalTimeNumber));
        return nextRuntime;
    }

    private void sendCommonTgMessage(TgMessageInfoTask tgMessageInfoTask) throws TelegramApiException {
        if (longPollingBot == null) {
            return;
        }
        String message = tgMessageInfoTask.getMessageInfo();
        String chatId = tgMessageInfoTask.getChatIds();
        List<String> chatIdList = Arrays.asList(chatId.split(","));
        for (String groupChatId : chatIdList) {
            SendMessage sendMessage = sendContent.messageText(groupChatId, message, ParseMode.MARKDOWN);
            longPollingBot.execute(sendMessage);
        }
    }


}
