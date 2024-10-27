package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.cashe.WordCash;
import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NameFolderState implements IAdd {
    private WordState wordState;
    private FolderController folderController;

    @Override
    public String getStatus() {
        return AddWordStateEnum.NAME_FOLDER.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {

        return folderController.getFolderByName(text,chatId) == null ?
                ResultStatusEnum.MISTAKE :
                ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        Cache cache = addContext.getCache();
        WordCash wordCash = new WordCash(text);
        UserCacheData userCacheData = new UserCacheData(wordState, addContext.getActionHandler(), wordCash);
        cache.put(chatId, userCacheData);
        addContext.setIAdd(wordState);
    }

    @Override
    public String wrongAnswer() {
        return "Папки с таким названием не существует";
    }
}
