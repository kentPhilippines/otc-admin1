package com.ruoyi.system.bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SendContent {

    public SendMessage messageText(Update update, String text){
        String chatId;
        if (update.getMessage() == null) {
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        } else {
            chatId = update.getMessage().getChatId().toString();
        }
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    public SendMessage messageText(String chatId, String text, String parseMode){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode(parseMode);
        return message;
    }
}
