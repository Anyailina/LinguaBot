package org.annill.linguabot.kafka;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {
    @Value("${topic.name}")
    private String topicName;
    private KafkaTemplate<String, WordTranslation> wordKafkaTemplate;

    @SneakyThrows
    public void sendMessage(String word, String translation) {
        WordTranslation wordSuggestion = new WordTranslation(word, translation);
        wordKafkaTemplate.send(topicName, wordSuggestion);
    }
}
