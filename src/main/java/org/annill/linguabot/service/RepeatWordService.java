package org.annill.linguabot.service;

import lombok.Setter;
import org.annill.linguabot.model.entity.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ConfigurationProperties(prefix = "repeat")
@Service
@Setter
public class RepeatWordService {
    @Value("${quantity-repeat}")
    private int quantityRepeat;
    private List<Integer> times;


    public Word updateRepeatWord(Word word, Integer wordDtoQuantityRepeat) {
        if (wordDtoQuantityRepeat != null && quantityRepeat <= wordDtoQuantityRepeat) {
            wordDtoQuantityRepeat = quantityRepeat;
            word.setIsLearned(true);
        }
        word.setQuantityRepeat(wordDtoQuantityRepeat);
        return word;
    }

    public List<Word> checkIfNeedRepeat(List<Word> words) {
        LocalDateTime now = LocalDateTime.now();
        return words.stream()
                .filter(word -> word.getQuantityRepeat() != 0)
                .filter(word -> Duration.between(word.getUpdateAt(), now).toMinutes() >= times.get(word.getQuantityRepeat() - 1))
                .toList();
    }
}
