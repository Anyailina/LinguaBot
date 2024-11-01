package org.annill.linguabot.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "create_at")
    private Date createdAt;
    @Column(name = "update_at")
    private Date updateAt;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private List<Folder> folderList;


    public User(Long id, Long chatId, String firstName, String userName) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
        this.userName = userName;
    }

    public User(Long id, Date createdAt, Date updateAt, String firstName, String userName) {
        this.chatId = id;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.firstName = firstName;
        this.userName = userName;
    }
}
