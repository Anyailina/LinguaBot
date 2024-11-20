package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Getter
@Setter
@Table(name = "words")
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String translation;
    @Column(name = "is_learned")
    private Boolean isLearned;
    @Column(name = "quantity_repeat")
    private Integer quantityRepeat;
    @Column(name = "create_at")
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    //TODO: @UpdateTimestamp
    private LocalDateTime updateAt;
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Word(String name, String translation, Boolean isLearned, Integer quantityRepeat, Folder folder) {
        this.name = name;
        this.translation = translation;
        this.isLearned = isLearned;
        this.quantityRepeat = quantityRepeat;
        this.folder = folder;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public Word(Long id, String name, String translation, Boolean isLearned, Integer quantityRepeat, Folder folder) {
        this.id = id;
        this.name = name;
        this.translation = translation;
        this.isLearned = isLearned;
        this.quantityRepeat = quantityRepeat;
        this.folder = folder;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
