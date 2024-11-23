package org.annill.linguabot.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@AllArgsConstructor
public class KafkaNotificationProducer {
    @Value("${topic.notification}")
    private String topicName;
    private KafkaTemplate<String, SendMessage> notificationKafkaTemplate;

    @SneakyThrows
    public void sendMessageNotification(SendMessage notificationMessage) {
        notificationKafkaTemplate.send(topicName, notificationMessage);
    }
}
