package org.annill.linguabot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "folders")
@NoArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "folder")
    private List<Word> words;

    public Folder(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }

}
