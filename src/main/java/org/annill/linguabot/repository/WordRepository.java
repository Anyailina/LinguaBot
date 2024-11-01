package org.annill.linguabot.repository;


import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByNameAndFolder(String name, Folder folder);

    List<Word> findByNameAndTranslationAndFolder(String name, String translation, Folder folder);


    Optional<Word> findFirstByNameAndFolder(String name, Folder folder);
}
