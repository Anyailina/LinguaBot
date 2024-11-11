package org.annill.linguabot.repository;

import org.annill.linguabot.model.entity.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    Optional<Folder> findByNameAndUserChatId(String name, Long user_chatId);

    Optional<Folder> findByIdAndUserChatId(Long id, Long user_chatId);

    Page<Folder> findAllByUserChatId(Long user_chatId, Pageable pageable);

    List<Folder> findAllByUserId(Long user_id);
}
