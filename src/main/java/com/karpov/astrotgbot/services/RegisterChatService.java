package com.karpov.astrotgbot.services;

import com.karpov.astrotgbot.models.Chat;
import com.karpov.astrotgbot.repo.ChatRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Service
public class RegisterChatService {

	private final ChatRepository chatRepository;

	public RegisterChatService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	public boolean registerChat(Update update) {
		long id = update.getMessage().getChatId();
		if (!chatRepository.existsById(id)) {
			chatRepository.save(new Chat(id, Instant.now()));
			return true;
		} else {
			return false;
		}
	}
}
