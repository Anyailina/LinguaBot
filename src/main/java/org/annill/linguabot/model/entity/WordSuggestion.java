package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

@Entity
@Table(name = "words_suggestion")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class WordSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phrase;
    private String translation;
    @Column(name = "create_at")
    private Date createdAt;
    @Column(name = "update_at")
    private Date updateAt;

    public WordSuggestion(String phrase, String translation) {
        this.phrase = phrase;
        this.translation = translation;
    }

    public WordSuggestion(Long id, String phrase, String translation) {
        this.id = id;
        this.phrase = phrase;
        this.translation = translation;
    }
}
