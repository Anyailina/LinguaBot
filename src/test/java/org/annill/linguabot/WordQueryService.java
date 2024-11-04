package org.annill.linguabot;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.annill.linguabot.model.entity.Word;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WordQueryService {

    private EntityManager entityManager;

    public Word getWordByNameAndTranslation(String word, String translation) {
        List<Word> words = entityManager.createQuery(
                        "select w from Word w where w.name = :name and w.translation = :translation", Word.class)
                .setParameter("name", word)
                .setParameter("translation", translation)
                .getResultList();
        return words.isEmpty() ? null : words.get(0);
    }

    public List<Word> getsWordByName(String word) {
        return entityManager.createQuery("select w from Word w where w.name = :name", Word.class)
                .setParameter("name", word)
                .getResultList();
    }
}