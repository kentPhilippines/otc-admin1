package com.ruoyi.system.bot;

import com.ruoyi.system.bot.handle.UserChatHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class CustomBotFunction {

    @Autowired
    private UserChatHandle userChatHandle;


    /**
     * 对消息进行处理
     *
     * @param sender
     * @param update
     */
    public void mainFunc(AbsSender sender, Update update) throws TelegramApiException, IOException, NoSuchAlgorithmException, InvalidKeyException {

//
//        if (update.getChatMember() != null && "left".equals(update.getChatMember().getOldChatMember().getStatus())
//                && "member".equals(update.getChatMember().getNewChatMember().getStatus()) &&
//                (update.getChatMember().getChat().isGroupChat() || update.getChatMember().getChat().isSuperGroupChat())) {
//
//            newMemberIntoGroup.handleMessage(sender, update);
//
//            return;
//        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            Chat chat = update.getMessage().getChat();
            if (chat != null) {
                if (chat.isGroupChat() || chat.isSuperGroupChat()) {
                    log.info("收到群组消息了:{},群组id:{}",update.getMessage().getText(),update.getMessage().getChatId());
                    // 收到的是群组内的消息
                    // 在这里处理群组内的消息
                } else if (chat.isChannelChat()){
                    //频道的消息
                    log.info("收到频道消息了:{}",update.getMessage().getText());
                }else {
                    log.info("收到用户消息了:{}",update.getMessage().getText());
                    // 收到的是私发给机器人的消息
                    // 在这里处理私发给机器人的消息
                    userChatHandle.doHandle(sender, update);
                }
            }
        }else{
            log.info("收到消息了1:{}",update.getEditedMessage().getText());
        }

    }
}
