package org.annill.linguabot.states.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.caсhe.UserCacheData;
import org.annill.linguabot.caсhe.WordCache;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NameFolderState implements IAdd {
    private final WordState wordState;
    private final FolderService folderService;
    @Value("${message.mistake.folder-not-exists}")
    private String messageFolderNotExists;

    @Override
    public String getStatus() {
        return AddWordStateEnum.NAME_FOLDER.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {

        return folderService.getFolderByName(text, chatId) == null ?
                ResultStatusEnum.MISTAKE :
                ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        Cache cache = addContext.getCache();
        WordCache wordCache = new WordCache(text);
        UserCacheData userCacheData = new UserCacheData(wordState, addContext.getActionHandler(), wordCache);
        cache.put(chatId, userCacheData);
        addContext.setIAdd(wordState);
    }

    @Override
    public String wrongAnswer() {
        return messageFolderNotExists;
    }
}
