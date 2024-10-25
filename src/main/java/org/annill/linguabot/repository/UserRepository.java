package org.annill.linguabot.repository;


import org.annill.linguabot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u  WHERE u.chatId = :chatId")
    User getUserIdByChatId(Long chatId);
}
