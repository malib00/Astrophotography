package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.InlineKeyboards.MainMenuKeyboard;
import com.karpov.astrobot.keyboards.InlineKeyboards.SettingsKeyboard;
import com.karpov.astrobot.models.Location;
import com.karpov.astrobot.services.LocationService;
import com.karpov.astrobot.services.RegisterChatService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Arrays;

@Component
public class CommandHandler {

	private final RegisterChatService registerChatService;
	private final MainMenuKeyboard mainMenuKeyboard;
	private final SettingsKeyboard settingsKeyboard;
	private final LocationService locationService;

	public CommandHandler(RegisterChatService registerChatService, MainMenuKeyboard mainMenuKeyboard, SettingsKeyboard settingsKeyboard, LocationService locationService) {
		this.registerChatService = registerChatService;
		this.mainMenuKeyboard = mainMenuKeyboard;
		this.settingsKeyboard = settingsKeyboard;
		this.locationService = locationService;
	}

	public SendMessage handleCommand(Update update, String command) {
		SendMessage sendMessage = new SendMessage();
		Long chatId = update.getMessage().getChatId();
		sendMessage.setChatId(chatId);
		MessageEntity messageEntity = new MessageEntity("code", 0, 23);
		sendMessage.setEntities(Arrays.asList(messageEntity));

		switch (command) {
			case ("/start"):
				registerChatService.registerChat(update);
				sendMessage.setText("Astrophotography Helper\n\nMain Menu");
				sendMessage.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return sendMessage;
			case ("/menu"):
				sendMessage.setText("Astrophotography Helper\n\nMain Menu");
				sendMessage.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return sendMessage;
			case ("/help"):
				sendMessage.setText("Astrophotography Helper\n\nUse /menu to open Main Menu");
				return sendMessage;
			case ("/settings"):
				sendMessage.setText("Astrophotography Helper\n\nSettings");
				sendMessage.setReplyMarkup(settingsKeyboard.getSettingsInlineKeyboard());
				return sendMessage;
			case ("/location"):
				if (locationService.isParsebleCoordinates(update.getMessage().getText())) {
					Location location = locationService.parseAndSetCoordinates(chatId, update.getMessage().getText());
					Double latitude = location.getLatitude();
					Double longitude = location.getLongitude();
					sendMessage.setText("Astrophotography Helper\n\nLocation is successfully set. Latitude: " + latitude + ", Longitude: " + longitude + ".");
					sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
				} else {
					sendMessage.setText("Astrophotography Helper\n\nLocation is not recognized.");
				}
				return sendMessage;
			default:
				return new SendMessage(update.getMessage().getChatId().toString(), command + " - command not recognized");
		}
	}
}
