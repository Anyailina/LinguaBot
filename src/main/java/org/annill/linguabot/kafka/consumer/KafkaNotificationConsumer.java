package org.annill.linguabot.kafka.consumer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.controller.TelegramController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@AllArgsConstructor
public class KafkaNotificationConsumer {
    private TelegramController telegramController;

    @SneakyThrows
    @KafkaListener(topics = "${spring.kafka.topics.notification}")
    public void listen(SendMessage sendMessage) {
        telegramController.sendMessage(sendMessage);
    }
}
