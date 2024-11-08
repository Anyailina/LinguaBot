package org.annill.linguabot.model.cache;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.model.dto.WordSuggestionDto;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SessionCache {
    private ResponseEnum response;
    private Long currentFolderId;
    private String word;
    private List<WordSuggestionDto> wordSuggestions;
    private int currentPage;

    public SessionCache(ResponseEnum response, Long currentFolderId) {
        this.response = response;
        this.currentFolderId = currentFolderId;
    }

    public SessionCache(ResponseEnum response, Long currentFolderId, String word) {
        this.response = response;
        this.currentFolderId = currentFolderId;
        this.word = word;
    }

    public SessionCache(ResponseEnum response, Long currentFolderId, String word, List<WordSuggestionDto> wordSuggestions) {
        this.response = response;
        this.currentFolderId = currentFolderId;
        this.word = word;
        this.wordSuggestions = wordSuggestions;
    }
}