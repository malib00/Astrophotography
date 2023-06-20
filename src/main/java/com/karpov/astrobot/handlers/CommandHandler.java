package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.MainMenuKeyboard;
import com.karpov.astrobot.services.RegisterChatService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {

	private final RegisterChatService registerChatService;
	private final MainMenuKeyboard mainMenuKeyboard;

	public CommandHandler(RegisterChatService registerChatService, MainMenuKeyboard mainMenuKeyboard) {
		this.registerChatService = registerChatService;
		this.mainMenuKeyboard = mainMenuKeyboard;
	}

	public SendMessage handleCommand(Update update, String command) {
		switch (command) {
			case ("/start"):
				registerChatService.registerChatOrUpdateState(update);
				SendMessage sendMessage = new SendMessage();
				sendMessage.setChatId(update.getMessage().getChatId().toString());
				sendMessage.setText("Menu");
				sendMessage.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return sendMessage;
			case ("/help"):
				return new SendMessage(update.getMessage().getChatId().toString(), "Some help text here... gonna be soon");
			case ("/settings"):
				return new SendMessage(update.getMessage().getChatId().toString(), "Settings is not implemented yet");
			default:
				return new SendMessage(update.getMessage().getChatId().toString(), command + " - command not recognized");
		}
	}
}
