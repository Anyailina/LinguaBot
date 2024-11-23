package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private UserConvertor userConvertor;

    public Optional<UserDto> getUserIdByChatId(Long chatId) {
        return userRepository.findByChatId(chatId)
                .map(userConvertor::convert);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConvertor::convert)
                .toList();
    }

    @Modifying
    public UserDto addUser(org.telegram.telegrambots.meta.api.objects.User userFromTelegram) {
        long idUserFromTelegram = userFromTelegram.getId();

        if (userRepository.findByChatId(idUserFromTelegram).isEmpty()) {
            User user = new User(idUserFromTelegram, userFromTelegram.getFirstName(), userFromTelegram.getUserName());
            return userConvertor.convert(userRepository.save(user));
        }
        return null;
    }
}
