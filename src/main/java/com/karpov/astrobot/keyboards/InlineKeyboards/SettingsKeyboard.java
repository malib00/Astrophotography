package com.karpov.astrobot.keyboards.InlineKeyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingsKeyboard {

	public InlineKeyboardMarkup getSettingsInlineKeyboard() {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

		List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButtonSetManualLocation = new InlineKeyboardButton("Set location");
		inlineKeyboardButtonSetManualLocation.setCallbackData("SetLocationButton");
		rowInLine1.add(inlineKeyboardButtonSetManualLocation);

		List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButtonSettings = new InlineKeyboardButton("Back");
		inlineKeyboardButtonSettings.setCallbackData("BackToMainMenuButton");
		rowInLine2.add(inlineKeyboardButtonSettings);
		InlineKeyboardButton inlineKeyboardButtonExitMenu = new InlineKeyboardButton("Exit");
		inlineKeyboardButtonExitMenu.setCallbackData("ExitButton");
		rowInLine2.add(inlineKeyboardButtonExitMenu);

		rowsInLine.add(rowInLine1);
		rowsInLine.add(rowInLine2);
		inlineKeyboardMarkup.setKeyboard(rowsInLine);
		return inlineKeyboardMarkup;
	}
}