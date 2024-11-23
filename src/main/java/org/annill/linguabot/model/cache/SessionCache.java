package org.annill.linguabot.model.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.model.dto.WordSuggestionDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SessionCache {
    private ResponseEnum response;
    private Long currentFolderId;
    private String word;
    private List<WordSuggestionDto> wordSuggestions;
    private int currentPage;
}