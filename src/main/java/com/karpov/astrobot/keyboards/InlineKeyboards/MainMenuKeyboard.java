package com.karpov.astrobot.keyboards.InlineKeyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuKeyboard {

	public InlineKeyboardMarkup getMainMenuInlineKeyboard() {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

		List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButtonWeather = new InlineKeyboardButton("Weather");
		inlineKeyboardButtonWeather.setCallbackData("WeatherButton");
		InlineKeyboardButton inlineKeyboardButtonAurora = new InlineKeyboardButton("Aurora");
		inlineKeyboardButtonAurora.setCallbackData("AuroraButton");

		List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButtonSettings = new InlineKeyboardButton("Settings");
		inlineKeyboardButtonSettings.setCallbackData("SettingsButton");
		InlineKeyboardButton inlineKeyboardButtonExitMenu = new InlineKeyboardButton("Exit");
		inlineKeyboardButtonExitMenu.setCallbackData("ExitButton");

		rowInLine1.add(inlineKeyboardButtonWeather);
		rowInLine1.add(inlineKeyboardButtonAurora);
		rowInLine2.add(inlineKeyboardButtonSettings);
		rowInLine2.add(inlineKeyboardButtonExitMenu);
		rowsInLine.add(rowInLine1);
		rowsInLine.add(rowInLine2);
		inlineKeyboardMarkup.setKeyboard(rowsInLine);
		return inlineKeyboardMarkup;
	}
}
