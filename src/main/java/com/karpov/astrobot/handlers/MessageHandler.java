package com.karpov.astrobot.handlers;

import com.karpov.astrobot.services.EchoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandler {

	private final EchoService echoService;
	final private CommandHandler commandHandler;

	public MessageHandler(EchoService echoService, CommandHandler commandHandler) {
		this.echoService = echoService;
		this.commandHandler = commandHandler;
	}

	public SendMessage handleMessage(Update update) {
		if (update.getMessage().hasEntities()) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setChatId(update.getMessage().getChatId().toString());
			sendMessage.setText("Got entities, but can't handle it ");
			for (MessageEntity entity : update.getMessage().getEntities()
			) {
				if (entity.getType().equals("bot_command")) {
					sendMessage = commandHandler.handleCommand(update, entity.getText());
				}
			}
			return sendMessage;
		} else {
			return echoService.echoText(update);
		}
	}
}
