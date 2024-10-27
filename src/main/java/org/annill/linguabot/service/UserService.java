package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

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
    public void addUser(Long chatId) {
        if (userRepository.findByChatId(chatId).isEmpty()) {
            User user = new User(chatId);
            userRepository.save(user);
        }
    }
}
