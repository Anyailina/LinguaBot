package org.annill.linguabot.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.kafka.WordTranslation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaAddedWordProducer {
    @Value("${topic.add-word}")
    private String topicName;
    private KafkaTemplate<String, WordTranslation> wordKafkaTemplate;

    @SneakyThrows
    public void sendMessageAddedWord(String word, String translation) {
        WordTranslation wordSuggestion = new WordTranslation(word, translation);
        wordKafkaTemplate.send(topicName, wordSuggestion);
    }
}
