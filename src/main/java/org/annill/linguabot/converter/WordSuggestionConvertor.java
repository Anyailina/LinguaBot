package org.annill.linguabot.converter;


import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.model.entity.WordSuggestion;
import org.springframework.stereotype.Component;

@Component
public class WordSuggestionConvertor {

    public WordSuggestionDto convert(WordSuggestion wordSuggestion) {
        return new WordSuggestionDto(wordSuggestion.getId(), wordSuggestion.getPhrase(), wordSuggestion.getTranslation());
    }

    public WordSuggestion convert(WordSuggestionDto wordSuggestionDto) {
        return new WordSuggestion(wordSuggestionDto.getId(), wordSuggestionDto.getPhrase(), wordSuggestionDto.getTranslation());
    }
}
