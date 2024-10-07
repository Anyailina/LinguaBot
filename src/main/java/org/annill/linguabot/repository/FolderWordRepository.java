package org.annill.linguabot.repository;

import org.annill.linguabot.entity.FolderWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FolderWordRepository extends JpaRepository<FolderWord, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM FolderWord fw WHERE fw.folder.id = :folderId")
    void deleteByFolderId(@Param("folderId") Long folderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FolderWord fw WHERE fw.word.id = :wordId AND fw.folder.id = :folderId")
    void deleteByWordId(@Param("folderId") Long folderId, @Param("wordId") Long wordId);
}
