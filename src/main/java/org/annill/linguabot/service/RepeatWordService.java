package org.annill.linguabot.service;

import lombok.Setter;
import org.annill.linguabot.model.entity.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "repeat")
@Service
@Setter
public class RepeatWordService {
    @Value("${quantity-repeat}")
    private Integer quantityRepeat;
    private List<Integer> times;


    public Word updateRepeatWord(Word word, Integer wordDtoQuantityRepeat) {
        int maxAllowedQuantityRepeat = quantityRepeat;
        if (wordDtoQuantityRepeat != null && maxAllowedQuantityRepeat <= wordDtoQuantityRepeat) {
            wordDtoQuantityRepeat = maxAllowedQuantityRepeat;
            word.setIsLearned(true);
        }
        word.setQuantityRepeat(wordDtoQuantityRepeat);
        return word;
    }

    public List<Word> checkIfNeedRepeat(List<Word> words) {
        List<Word> wordsRepeat = new ArrayList<>();
        for (Word word : words) {
            LocalDateTime lastUpdate = word.getUpdateAt();
            switch (word.getQuantityRepeat()) {
                case 1:
                    if (Duration.between(lastUpdate, LocalDateTime.now()).toMinutes() >= times.get(0)) {
                        wordsRepeat.add(word);
                    }
                    break;

                case 2:
                    if (Duration.between(lastUpdate, LocalDateTime.now()).toHours() >= times.get(1)) {
                        wordsRepeat.add(word);
                    }
                    break;

                case 3:
                    if (Duration.between(lastUpdate, LocalDateTime.now()).toDays() > times.get(2)) {
                        wordsRepeat.add(word);
                    }
                    break;

                case 4:
                    if (Duration.between(lastUpdate, LocalDateTime.now()).toDays() > times.get(3)) {
                        wordsRepeat.add(word);
                    }
                    break;

                case 5:
                    if (Duration.between(lastUpdate, LocalDateTime.now()).toDays() > times.get(4)) {
                        wordsRepeat.add(word);
                    }
                    break;

                default:
                    break;
            }
        }
        return wordsRepeat;
    }
}
