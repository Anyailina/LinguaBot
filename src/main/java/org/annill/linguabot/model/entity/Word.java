package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
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
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Word(String name, String translation, Folder folder) {
        this.name = name;
        this.translation = translation;
        this.folder = folder;
    }
}
