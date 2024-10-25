package org.annill.linguabot.fabricOfAction;

import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActionHandlerHelper {
    private final Map<ActionEnum, ActionHandler> actionHandlersMap;
    @Value("${message.not-correct-command}")
    private String incorrectCommand;

    public ActionHandlerHelper(List<ActionHandler> listAction) {
        actionHandlersMap = new HashMap<>();
        listAction.forEach(actionHandler -> actionHandlersMap.put(actionHandler.getType(), actionHandler));
    }

    public SendMessage process(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            sendMessage.setText(actionHandlersMap.get(ActionEnum.fromText(text)).process(update));
            return sendMessage;
        }
        sendMessage.setText(incorrectCommand);
        return sendMessage;
    }
}
