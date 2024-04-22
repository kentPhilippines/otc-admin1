package com.ruoyi.system.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomBotCommands {
    public void setCustomBotCommands(AbsSender sender) throws TelegramApiException {
        List<BotCommand> commands = Arrays.asList(
                new BotCommand("/start", "开始使用"),
                new BotCommand("/rank", "汇率信息")
         /*       new BotCommand("/ban", "!或/ban user 时间(可选) 原因(可选)"),
                new BotCommand("/dban","可Ban掉用户的同时，删除他的发言，格式参考Ban"),
                new BotCommand("/unban", "解封用户，!或/unban user"),
                new BotCommand("/mute", "禁言用户，格式参考Ban"),
                new BotCommand("/unmute","解除用户发言限制，!或/unmute user")*/
        );

        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commands);

        sender.execute(setMyCommands);

    }
}
