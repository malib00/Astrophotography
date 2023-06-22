package com.karpov.astrobot.keyboards;

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
		InlineKeyboardButton inlineKeyboardButtonSetLocation = new InlineKeyboardButton("Set Location");
		inlineKeyboardButtonSetLocation.setCallbackData("SetLocationButton");

		List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButtonSettings = new InlineKeyboardButton("Back");
		inlineKeyboardButtonSettings.setCallbackData("BackToMainMenuButton");
		InlineKeyboardButton inlineKeyboardButtonExitMenu = new InlineKeyboardButton("Exit");
		inlineKeyboardButtonExitMenu.setCallbackData("ExitButton");

		rowInLine1.add(inlineKeyboardButtonSetLocation);
		rowInLine2.add(inlineKeyboardButtonSettings);
		rowInLine2.add(inlineKeyboardButtonExitMenu);
		rowsInLine.add(rowInLine1);
		rowsInLine.add(rowInLine2);
		inlineKeyboardMarkup.setKeyboard(rowsInLine);
		return inlineKeyboardMarkup;
	}
}