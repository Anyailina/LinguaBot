package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private UserConvertor userConvertor;

    public UserDto getUserIdByChatId(Long chatId) {
        User user = userRepository.getUserIdByChatId(chatId);
        return userConvertor.convert(user);
    }

    public void addUser(Long chatId) {
        if (userRepository.getUserIdByChatId(chatId) == null) {
            User user = new User(chatId);
            userRepository.save(user);
        }
    }
}
