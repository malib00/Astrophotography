package com.karpov.astrobot.repo;

import com.karpov.astrobot.models.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
}
