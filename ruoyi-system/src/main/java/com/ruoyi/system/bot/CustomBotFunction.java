package com.ruoyi.system.bot;

import com.ruoyi.system.bot.handleService.NewMemberIntoGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomBotFunction {

    @Autowired
    private NewMemberIntoGroup newMemberIntoGroup;


    /**
     * 对消息进行处理
     *
     * @param sender
     * @param update
     */
    public void mainFunc(AbsSender sender, Update update) throws TelegramApiException, IOException, NoSuchAlgorithmException, InvalidKeyException {


        if (update.getChatMember() != null && "left".equals(update.getChatMember().getOldChatMember().getStatus())
                && "member".equals(update.getChatMember().getNewChatMember().getStatus()) &&
                (update.getChatMember().getChat().isGroupChat() || update.getChatMember().getChat().isSuperGroupChat())) {

            newMemberIntoGroup.handleMessage(sender, update);

            return;
        }

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
