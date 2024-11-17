package org.annill.linguabot.repository;


import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query("SELECT w FROM Word w WHERE  w.name = :name AND w.folder.id = :folderId AND w.folder.user.chatId = :chatId")
    List<Word> findWordsByNameFolderIdAndUserChatId(@Param("name") String name, @Param("folderId") Long folderId, @Param("chatId") Long chatId);

    List<Word> findByIsLearnedAndQuantityRepeatAndFolderIdInAndFolderUserId(
            Boolean isLearned,
            Integer quantityRepeat,
            List<Long> folderIds,
            Long userId
    );

    List<Word> findByIsLearnedAndQuantityRepeatNotAndFolderIdInAndFolderUserId(
            Boolean isLearned,
            Integer quantityRepeat,
            List<Long> folderIds,
            Long userId
    );

    List<Word> findAllByName(String name);

    Optional<Word> findFirstByNameAndTranslation(String name, String translation);

    Optional<Word> findFirstByNameAndFolderAndTranslation(String name, Folder folder, String translation);
}
