package com.karpov.astrobot.handlers;

import com.karpov.astrobot.services.EchoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainUpdateHandler {

	final private EchoService echoService;
	final private CommandHandler commandHandler;

	public MainUpdateHandler(EchoService echoService, CommandHandler commandHandler) {
		this.echoService = echoService;
		this.commandHandler = commandHandler;
	}

	public BotApiMethod<?> handleUpdate(Update update) {
		if (update.getMessage().hasEntities()) {
			for (MessageEntity entity: update.getMessage().getEntities()
			     ) {
				if (entity.getType().equals("bot_command")) {
					return commandHandler.handleCommand(update, entity.getText());
				}
			}
		}

		if (update.getMessage().hasText()) {
			return echoService.echoText(update);

		} else {
			return new SendMessage(update.getMessage().getChatId().toString(), "not recognized");
		}
	}
}
