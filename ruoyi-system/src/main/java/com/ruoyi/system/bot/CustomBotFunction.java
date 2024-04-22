package com.ruoyi.system.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomBotFunction {



    /**
     * 对消息进行处理
     * @param sender
     * @param update
     */
    public void mainFunc(AbsSender sender, Update update) throws TelegramApiException {
        if ("/start".equals(update.getMessage().getText())) {

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Please select an option:");

            // 创建一个 ReplyKeyboardMarkup 对象，用于存储按钮
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();

            // 创建一个按钮行
            KeyboardRow row = new KeyboardRow();
            row.add("Option 1");
            row.add("Option 2");
            keyboard.add(row);

            // 将按钮行添加到 ReplyKeyboardMarkup 对象中
            keyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(keyboardMarkup);

            // 设置按钮显示在输入框底部
            keyboardMarkup.setResizeKeyboard(true);

            sender.execute(message);
        }
    }
}
