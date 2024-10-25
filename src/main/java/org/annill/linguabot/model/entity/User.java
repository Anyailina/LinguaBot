package org.annill.linguabot.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "users")
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @OneToMany(mappedBy = "user")
    private List<Folder> folderList;

    public User(Long chatId) {
        this.chatId = chatId;
    }

    public User(Long id, Long chatId) {
        this.id = id;
        this.chatId = chatId;
    }
}
