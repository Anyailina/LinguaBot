package org.annill.linguabot.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;


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
    @Column(name = "create_at")
    private Date createdAt;
    @Column(name = "update_at")
    private Date updateAt;
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Word(String name, String translation, Date createdAt, Date updateAt, Folder folder) {
        this.name = name;
        this.translation = translation;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.folder = folder;
    }

    public Word(Long id, String name, String translation, Folder folder) {
        this.id = id;
        this.name = name;
        this.translation = translation;
        this.folder = folder;
    }
}
