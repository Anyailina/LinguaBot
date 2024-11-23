package org.annill.linguabot.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "folders")
@NoArgsConstructor
@EqualsAndHashCode
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @CreationTimestamp
    @Column(name = "create_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;
    @OneToMany(mappedBy = "folder", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
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
