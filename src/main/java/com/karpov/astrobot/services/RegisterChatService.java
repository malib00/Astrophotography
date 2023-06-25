package com.karpov.astrobot.services;

import com.karpov.astrobot.models.BotState;
import com.karpov.astrobot.models.Chat;
import com.karpov.astrobot.repo.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class RegisterChatService {

	private final ChatRepository chatRepository;

	public RegisterChatService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	public void registerChat(Update update) {
		long chatId = update.getMessage().getChatId();
		Optional<Chat> chatOptional = chatRepository.findById(chatId);
		if (chatOptional.isEmpty()) {
			chatRepository.save(new Chat(chatId, false, Instant.now(), BotState.MAIN_MENU));
			log.info("Chat registration: chatId={}", chatId);
		} else {
			chatRepository.updateBlockedByUserById(chatId, false);
			log.info("Chat re-registered(unblocked): chatId={}", chatId);
		}
	}
}
