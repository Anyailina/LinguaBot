package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @Column(name = "is_selected")
    private Boolean isSelected;
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Word(String name, String translation, Boolean isLearned, Integer quantityRepeat, Boolean isSelected, Folder folder) {
        this.name = name;
        this.translation = translation;
        this.isLearned = isLearned;
        this.quantityRepeat = quantityRepeat;
        this.folder = folder;
        this.isSelected = isSelected;
    }

    public Word(Long id, String name, String translation, Boolean isLearned, Integer quantityRepeat, Boolean isSelected, Folder folder) {
        this.id = id;
        this.name = name;
        this.translation = translation;
        this.isLearned = isLearned;
        this.quantityRepeat = quantityRepeat;
        this.folder = folder;
        this.isSelected = isSelected;
    }
}
