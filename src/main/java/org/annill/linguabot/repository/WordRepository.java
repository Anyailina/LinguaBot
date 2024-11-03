package org.annill.linguabot.repository;


import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByNameAndFolder(String name, Folder folder);

    Optional<Word> findFirstByNameAndFolderAndTranslation(String name, Folder folder, String translation);
}
