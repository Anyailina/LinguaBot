package org.annill.linguabot.cashe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordCash {
    private String folderName;
    private String word;

    public WordCash(String folderName) {
        this.folderName = folderName;
    }
}
