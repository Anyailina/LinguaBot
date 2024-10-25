package org.annill.linguabot.repository;

import org.annill.linguabot.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
