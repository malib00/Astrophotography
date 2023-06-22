package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.MainMenuKeyboard;
import com.karpov.astrobot.keyboards.SettingsKeyboard;
import com.karpov.astrobot.services.RegisterChatService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class CommandHandler {

	private final RegisterChatService registerChatService;
	private final MainMenuKeyboard mainMenuKeyboard;
	private final SettingsKeyboard settingsKeyboard;

	public CommandHandler(RegisterChatService registerChatService, MainMenuKeyboard mainMenuKeyboard, SettingsKeyboard settingsKeyboard) {
		this.registerChatService = registerChatService;
		this.mainMenuKeyboard = mainMenuKeyboard;
		this.settingsKeyboard = settingsKeyboard;
	}

	public SendMessage handleCommand(Update update, String command) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(update.getMessage().getChatId().toString());
		MessageEntity messageEntity = new MessageEntity("code", 0, 23);
		sendMessage.setEntities(Arrays.asList(messageEntity));

		switch (command) {
			case ("/start"):
				registerChatService.registerChatOrUpdateState(update);
				sendMessage.setText("Astrophotography Helper\n\nMain Menu");
				sendMessage.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return sendMessage;
			case ("/help"):
				sendMessage.setText("Astrophotography Helper\n\nUse /start to open Main Menu");
				return sendMessage;
			case ("/settings"):
				sendMessage.setText("Astrophotography Helper\n\nSettings");
				sendMessage.setReplyMarkup(settingsKeyboard.getSettingsInlineKeyboard());
				return sendMessage;
			default:
				return new SendMessage(update.getMessage().getChatId().toString(), command + " - command not recognized");
		}
	}
}
