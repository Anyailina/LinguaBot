package org.annill.linguabot.converter;

import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConvertor {
    public UserDto convert(User user) {
        List<Long> foldersId = user.getFolderList()
                .stream()
                .map(Folder::getId).toList();
        return new UserDto(user.getId(), user.getChatId(), foldersId);
    }

    public User convert(UserDto userDto) {
        return new User(userDto.getId(), userDto.getChatId());
    }
}
