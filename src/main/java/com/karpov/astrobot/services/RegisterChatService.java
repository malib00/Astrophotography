package com.karpov.astrobot.services;

import com.karpov.astrobot.models.BotState;
import com.karpov.astrobot.models.Chat;
import com.karpov.astrobot.repo.ChatRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.util.Optional;

@Service
public class RegisterChatService {

	private final ChatRepository chatRepository;

	public RegisterChatService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	public void registerChat(Update update) {
		long id = update.getMessage().getChatId();
		Optional<Chat> chatOptional = chatRepository.findById(id);
		if (chatOptional.isEmpty()) {
			chatRepository.save(new Chat(id, false, Instant.now(), BotState.MENU));
		} else {
			chatRepository.updateBlockedByUserById(id, false);
		}
	}
}
