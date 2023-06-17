package com.karpov.astrotgbot.handlers;

import com.karpov.astrotgbot.services.RegisterChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {

	@Autowired
	private RegisterChatService registerChatService;

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
