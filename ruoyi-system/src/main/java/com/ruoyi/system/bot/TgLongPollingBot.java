package com.ruoyi.system.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Component
@Slf4j
public class TgLongPollingBot extends TelegramLongPollingBot {


    @Value("${tg.bot.name}")
    private String botName;

    @Value("${tg.bot.token}")
    private String botToken;

    @Autowired
    private CustomBotFunction customBotFunction;

    @Autowired
    private CustomBotCommands customBotCommands;



    public void setCommands() throws TelegramApiException {
//        groupCommands.setGroupCommands(this);
        customBotCommands.setCustomBotCommands(this);

    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
//        SendMessage sendMessage = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("收到消息:"+update.getMessage().getChatId().toString()).build();
        try {
            customBotFunction.mainFunc(this,update);

        } catch (TelegramApiException e) {
            log.error("ex:{}",e);
            throw new RuntimeException(e);
        }
    }
}