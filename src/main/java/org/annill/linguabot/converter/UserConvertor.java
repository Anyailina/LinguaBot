package org.annill.linguabot.converter;

import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {
    public UserDto convert(User user) {
        return new UserDto(user.getId(), user.getChatId(), user.getFirstName(), user.getUserName());
    }

    public User convert(UserDto userDto) {
        return new User(userDto.getId(), userDto.getChatId(), userDto.getFirstName(), userDto.getUserName());
    }
}
