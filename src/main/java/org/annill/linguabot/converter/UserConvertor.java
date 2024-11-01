package org.annill.linguabot.converter;

import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserConvertor {
    public UserDto convert(User user) {
        List<Long> folderIds = Optional.ofNullable(user.getFolderList())
                .map(folders -> folders.stream().map(Folder::getId).toList())
                .orElse(List.of());

        return new UserDto(user.getId(), user.getChatId(), user.getFirstName(), user.getUserName(), folderIds);
    }

    public User convert(UserDto userDto) {
        return new User(userDto.getId(), userDto.getChatId(), userDto.getFirstName(), userDto.getUserName());
    }
}
