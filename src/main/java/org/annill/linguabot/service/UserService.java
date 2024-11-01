package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private UserConvertor userConvertor;

    public UserDto getUserIdByChatId(Long chatId) {
        return userRepository.findByChatId(chatId)
                .map(userConvertor::convert)
                .orElse(null);
    }

    @Modifying
    public UserDto addUser(org.telegram.telegrambots.meta.api.objects.User userFromTelegram) {
        long idUserFromTelegram = userFromTelegram.getId();

        if (userRepository.findByChatId(idUserFromTelegram).isEmpty()) {
            User user = new User(idUserFromTelegram, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), userFromTelegram.getFirstName(), userFromTelegram.getUserName());
            return userConvertor.convert(userRepository.save(user));
        }
        return null;
    }
}
