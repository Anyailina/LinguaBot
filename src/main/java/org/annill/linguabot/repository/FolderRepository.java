package org.annill.linguabot.repository;

import org.annill.linguabot.model.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f LEFT JOIN FETCH f.words  WHERE f.name = :name AND f.user.chatId = :chatId")
    Optional<Folder> findByNameAndUserChatId(@Param("name") String name, @Param("chatId") Long chatId);

    Optional<Folder> findByIdAndUserChatId(Long id, Long user_chatId);
}
