package com.karpov.astrobot.handlers;

import com.karpov.astrobot.services.RegisterChatService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {


	private final RegisterChatService registerChatService;

	public CommandHandler(RegisterChatService registerChatService) {
		this.registerChatService = registerChatService;
	}

	public SendMessage handleCommand(Update update, String command) {
		if (command.equals("/start")) {
			if (registerChatService.registerChat(update)) {
				return new SendMessage(update.getMessage().getChatId().toString(), "Welcome");
			} else {
				return new SendMessage(update.getMessage().getChatId().toString(), "Bot is already started for this chat");
			}
		} else {
			return new SendMessage(update.getMessage().getChatId().toString(), command + " - command not recognized");
		}
	}
}
