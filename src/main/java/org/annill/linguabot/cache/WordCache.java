package org.annill.linguabot.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public class WordCache implements Serializable {
    private String folderName;
    private String word;

    public WordCache(String folderName) {
        this.folderName = folderName;
    }
}
