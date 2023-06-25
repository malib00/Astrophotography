package com.karpov.astrobot.handlers;

import com.karpov.astrobot.repo.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class MainUpdateHandler {

	private final InlineQueryHandler inlineQueryHandler;
	private final MessageHandler messageHandler;
	private final ChatRepository chatRepository;

	public MainUpdateHandler(InlineQueryHandler inlineQueryHandler, MessageHandler messageHandler, ChatRepository chatRepository) {
		this.inlineQueryHandler = inlineQueryHandler;
		this.messageHandler = messageHandler;
		this.chatRepository = chatRepository;
	}

	public BotApiMethod<?> handleUpdate(Update update) {
		if (update.hasCallbackQuery()) {
			return inlineQueryHandler.handleInlineQuery(update);
		} else if (update.hasMessage()) {
			return messageHandler.handleMessage(update);
		} else if (update.hasMyChatMember() && update.getMyChatMember().getNewChatMember().getStatus().equals("kicked")) {
			Long chatId = update.getMyChatMember().getChat().getId();
			chatRepository.updateBlockedByUserById (chatId, true);
			log.info("User blocked chat: chatId={}", chatId);
			return null;
		} else if (update.hasMyChatMember() && update.getMyChatMember().getNewChatMember().getStatus().equals("member")) {
			Long chatId = update.getMyChatMember().getChat().getId();
			chatRepository.updateBlockedByUserById (chatId, true);
			log.info("User unblocked chat: chatId={}", chatId);
			return null;
		} else {
			log.warn("Unexpected behavior, can't handle an incoming update: {}", update);
			return null;
		}
	}
}
