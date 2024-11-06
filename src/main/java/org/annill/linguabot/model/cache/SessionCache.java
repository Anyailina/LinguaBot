package org.annill.linguabot.model.cache;

import lombok.Data;
import lombok.experimental.Accessors;
import org.annill.linguabot.enums.ResponseEnum;

@Data
@Accessors(chain = true)
public class SessionCache {

    private ResponseEnum response;
    private Long currentFolderId;
    private String word;
    private String translation;
}
