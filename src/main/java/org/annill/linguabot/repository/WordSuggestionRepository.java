package org.annill.linguabot.repository;

import org.annill.linguabot.model.entity.WordSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordSuggestionRepository extends JpaRepository<WordSuggestion, Long> {

    Optional<List<WordSuggestion>> findByPhrase(String phrase);

    Optional<WordSuggestion> findFirstByPhrase(String phrase);
}
