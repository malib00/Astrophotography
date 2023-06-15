package com.karpov.astrotgbot.handlers;

import com.karpov.astrotgbot.services.EchoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainUpdateHandler {

	final private EchoService echoService;

	public MainUpdateHandler(EchoService echoService) {
		this.echoService = echoService;
	}

	public BotApiMethod<?> handleUpdate(Update update) {
		if (update.getMessage().hasText()) {
			return echoService.echoText(update);
		} else {
			return new SendMessage(update.getMessage().getChatId().toString(), "not recognized");
		}
	}
}
