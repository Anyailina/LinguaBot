package org.annill.linguabot.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(chatId, user.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(chatId);
    }
}
