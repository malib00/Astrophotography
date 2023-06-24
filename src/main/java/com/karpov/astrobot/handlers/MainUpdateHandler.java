package com.karpov.astrobot.handlers;

import com.karpov.astrobot.repo.ChatRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MainUpdateHandler {

	private final InlineQueryHandler inlineQueryHandler;
	private final MessageHandler messageHandler;
	private final ChatRepository chatRepository;

	public MainUpdateHandler(InlineQueryHandler inlineQueryHandler, MessageHandler messageHandler, ChatRepository chatRepository) {
		this.inlineQueryHandler = inlineQueryHandler;
		this.messageHandler = messageHandler;
		this.chatRepository = chatRepository;
	}

	public BotApiMethod<?> handleUpdate(Update update) throws TelegramApiException {
		if (update.hasCallbackQuery()) {
			return inlineQueryHandler.handleInlineQuery(update);
		} else if (update.hasMessage()) {
			return messageHandler.handleMessage(update);
		} else if (update.hasMyChatMember() && update.getMyChatMember().getNewChatMember().getStatus().equals("kicked")) {
			chatRepository.updateBlockedByUserById (update.getMyChatMember().getChat().getId(), true);
			return new SendMessage();
		} else {
			return new SendMessage();
		}
	}
}
