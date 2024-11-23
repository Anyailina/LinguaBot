package org.annill.linguabot.converter;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.entity.Word;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WordConverter {
    private FolderConverter folderConverter;

    public WordDto convert(Word word) {
        return new WordDto(word.getId(), word.getName(), word.getTranslation(), word.getIsLearned(), word.getQuantityRepeat(), word.getIsSelected(), folderConverter.convert(word.getFolder()));
    }

    public Word convert(WordDto wordDto) {
        return new Word(wordDto.getId(), wordDto.getName(), wordDto.getTranslation(), wordDto.getIsLearned(), wordDto.getQuantityRepeat(), wordDto.getIsSelected(), folderConverter.convert(wordDto.getFolderDto()));
    }
}
