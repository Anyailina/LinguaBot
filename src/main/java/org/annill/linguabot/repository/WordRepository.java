package org.annill.linguabot.repository;

import org.annill.linguabot.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    @Modifying
    @Transactional
    @Query("SELECT w FROM Word w JOIN w.folders f WHERE f.id = :folderId")
    List<Word> findAllWord(@Param("folderId") Long folderId);
}
