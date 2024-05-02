package com.ruoyi.system.bot.handle;

import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserChatHandle {

    @Autowired
    private SendContent sendContent;
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;


    public void doHandle(AbsSender sender, Update update) throws TelegramApiException, NoSuchAlgorithmException, IOException, InvalidKeyException {

        String text = update.getMessage().getText();
        if ("/start".equals(text)) {
            startCommandMessage(sender, update);
        } else if ("\uD83E\uDDE9TRX兑换".equals(text)) {
            String configValue = configService.selectConfigByKey("sys.usdt.group.topic");
            configValue = StringUtils.isEmpty(configValue) ? "configKey is null" : configValue;
            String message = "";
            if (StringUtils.isNotEmpty(configValue)){
                BigDecimal tenUsdtToTrx = usdt2TrxTransferHandler.getOneUsdtToTrx().multiply(BigDecimal.TEN);
                Map<String, Object> arguments = new HashMap<>();
                arguments.put("tenUsdtToTrx", tenUsdtToTrx);
                StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
                message = substitutor.replace(configValue);
            }
            SendMessage sendMessage = sendContent.messageText(update.getMessage().getChatId().toString(), message, ParseMode.MARKDOWN);
            sender.execute(sendMessage);
        } else if ("⏳能量闪租".equals(text)) {
            SendMessage message = getSendMessage(update, "sys.energy.notice");
            sender.execute(message);
        } else if ("\uD83D\uDC69\u200D\uD83C\uDFED联系客服".equals(text)) {
            SendMessage message = getSendMessage(update, "sys.customer.service.notice");
            message.setReplyToMessageId(update.getMessage().getMessageId());
            sender.execute(message);
        } else {
            log.info("收到消息了:{}", text);
            SendMessage message = sendContent.messageText(update.getMessage().getChatId().toString(), "收到消息了:" + text, ParseMode.MARKDOWN);
            sender.execute(message);
        }
    }

    private SendMessage getSendMessage(Update update, String configKey) {
        String configValue = configService.selectConfigByKey(configKey);
        configValue = StringUtils.isEmpty(configValue) ? "configKey is null" : configValue;
        SendMessage message = sendContent.messageText(update.getMessage().getChatId().toString(), configValue, ParseMode.MARKDOWN);
        return message;
    }

    private void startCommandMessage(AbsSender sender, Update update) throws TelegramApiException {
        // 创建一个 ReplyKeyboardMarkup 对象，用于存储按钮
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // 创建一个按钮行
        KeyboardRow row = new KeyboardRow();
        row.add("\uD83E\uDDE9TRX兑换");
        row.add("⏳能量闪租");
        row.add("\uD83D\uDC69\u200D\uD83C\uDFED联系客服");
        keyboard.add(row);

        // 将按钮行添加到 ReplyKeyboardMarkup 对象中
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);


        // 设置按钮显示在输入框底部

//            SendMessage message = new SendMessage();
//            message.setChatId(update.getMessage().getChatId().toString());
//            message.setText("Please select an option:");
//            message.setParseMode(ParseMode.MARKDOWN);
//            message.setReplyMarkup(keyboardMarkup);
        SendMessage message = getSendMessage(update, "sys.start.notice");
        message.setReplyMarkup(keyboardMarkup);
        sender.execute(message);
    }


}
