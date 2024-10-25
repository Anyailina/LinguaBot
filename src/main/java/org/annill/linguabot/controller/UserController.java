package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

    public UserDto getUserIdByChatId(Long chatId) {
        return userService.getUserIdByChatId(chatId);
    }

    public void addUser(Long chatId) {
        userService.addUser(chatId);
    }
}
