package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "words_suggestion")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WordSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phrase;
    private String translation;

    public WordSuggestion(String phrase, String translation) {
        this.phrase = phrase;
        this.translation = translation;
    }
}
