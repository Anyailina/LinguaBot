package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserConvertor userConvertor;

    public UserDto getUserIdByChatId(Long chatId) {
        User user = userRepository.getUserIdByChatId(chatId);
        return userConvertor.convert(user);
    }

    public void addUser(Long chatId) {
        User user = new User(chatId);
        userRepository.save(user);
    }
}
