//package com.ruoyi.system.bot.handle;
//
//import com.ruoyi.system.bot.utils.SendContent;
//import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
//import com.ruoyi.system.service.ISysConfigService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.text.StrSubstitutor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.objects.InputFile;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.bots.AbsSender;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Slf4j
//public class NewMemberIntoGroup {
//
//    @Autowired
//    private SendContent sendContent;
//    @Autowired
//    private ISysConfigService configService;
//    @Autowired
//    private Usdt2TrxTransferHandler usdt2TrxTransferHandlerl;
//
//
//    public void handleMessage(AbsSender sender, Update update) throws TelegramApiException, IOException, NoSuchAlgorithmException, InvalidKeyException {
//        InputStream inputStream =
//                new ClassPathResource("pbottleRPA_1681804582722.png").getInputStream();
//
//        Long chatId = update.getChatMember().getChat().getId();
//        String sysUsdtGroupWelcomTopic = configService.selectConfigByKey("sys.usdt.group.welcom.topic");
//        String sysTgGroupChatId = configService.selectConfigByKey("sys.tg.group.chat.id");
//
//
//
//        if (StringUtils.isNotEmpty(sysUsdtGroupWelcomTopic) && StringUtils.isNotEmpty(sysTgGroupChatId)){
//            BigDecimal oneUsdtToTrx = usdt2TrxTransferHandlerl.getOneUsdtToTrx();
//
//            BigDecimal tenUsdtToTrx = oneUsdtToTrx.multiply(BigDecimal.TEN);
//            Map<String, Object> arguments = new HashMap<>();
//            arguments.put("tenUsdtToTrx", tenUsdtToTrx);
//            StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
//            String message = substitutor.replace(sysUsdtGroupWelcomTopic);
//            SendPhoto sendPhoto = SendPhoto.builder()
//                    .chatId(chatId.toString())
//                    .photo(new InputFile(inputStream, "aaa.png"))
//                    .caption(message).build();
//
//            sender.execute(sendPhoto);
//        }else {
//            log.info("sysUsdtGroupWelcomTopic or sysTgGroupChatId is empty");
//        }
//
//    }
//}
