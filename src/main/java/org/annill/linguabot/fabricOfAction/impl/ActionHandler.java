package org.annill.linguabot.fabricOfAction.impl;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.impl.IAdd;

public abstract class ActionHandler {
    // Поле, если нужно
    protected ActionEnum actionType;

    // Конструктор
    public ActionHandler(ActionEnum actionType) {
        this.actionType = actionType;
    }

    // Абстрактный метод, который должен быть реализован в подклассах
    public abstract String process(String text, long chatId, IAdd iAdd);

    // Метод для получения типа действия
    public ActionEnum getType() {
        return actionType;
    }
}