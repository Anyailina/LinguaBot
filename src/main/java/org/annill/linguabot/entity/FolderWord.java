package org.annill.linguabot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "folder_word")
public class FolderWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;
    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;
}
