package com.karpov.astrobot.handlers;

import com.karpov.astrobot.services.EchoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainUpdateHandler {

	final private InlineQueryHandler inlineQueryHandler;
	private final MessageHandler messageHandler;

	public MainUpdateHandler(EchoService echoService, CommandHandler commandHandler, InlineQueryHandler inlineQueryHandler, MessageHandler messageHandler) {
		this.inlineQueryHandler = inlineQueryHandler;
		this.messageHandler = messageHandler;
	}

	public BotApiMethod<?> handleUpdate(Update update) {
		if (update.hasCallbackQuery()) {
			return inlineQueryHandler.handleInlineQuery(update);
		} else if (update.hasMessage()) {
			return messageHandler.handleMessage(update);
		} else {
			return new SendMessage(update.getMessage().getChatId().toString(), "not recognized");
		}
	}
}

