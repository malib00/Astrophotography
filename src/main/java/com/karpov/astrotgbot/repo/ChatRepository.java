package com.karpov.astrotgbot.repo;

import com.karpov.astrotgbot.models.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
}
