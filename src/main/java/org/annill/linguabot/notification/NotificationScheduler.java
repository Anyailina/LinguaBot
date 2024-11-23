package org.annill.linguabot.notification;

import lombok.AllArgsConstructor;
import org.annill.linguabot.kafka.producer.KafkaNotificationProducer;
import org.annill.linguabot.model.Message;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.service.UserService;
import org.annill.linguabot.service.WordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationScheduler {
    private final WordService wordService;
    private final UserService userService;
    private final FolderService folderService;
    private final KafkaNotificationProducer kafkaNotificationProducer;
    private final Message message;

    @Scheduled(cron = "0 0 10 * * *")
    public void checkForRepetition() {
        List<UserDto> userDtoList = userService.getAllUsers();
        for (UserDto userDto : userDtoList) {
            List<Long> folderIds = folderService.getFolders(userDto.getId()).stream()
                    .map(FolderDto::getId).toList();
            List<WordDto> wordDtoListForRepeat = wordService.getWordsForRepeat(folderIds, userDto.getId());
            if (!wordDtoListForRepeat.isEmpty()) {
                SendMessage sendMessage = createNotificationMessage(wordDtoListForRepeat.size(), userDto.getChatId());
                kafkaNotificationProducer.sendMessageNotification(sendMessage);
            }
        }
    }

    private SendMessage createNotificationMessage(Integer quantityWordForRepeat, Long chatId) {
        String messageText = String.format(message.getNotification(), quantityWordForRepeat);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageText);
        return sendMessage;
    }
}
