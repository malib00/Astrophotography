package com.karpov.astrobot.keyboards.ReplyKeyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationReplyKeyboard {

	public ReplyKeyboardMarkup getLocationReplyKeyboard() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> rowsInLine = new ArrayList<>();

		KeyboardRow rowInLine1 = new KeyboardRow();
		KeyboardButton keyboardButton = new KeyboardButton("Set location automatically");
		keyboardButton.setRequestLocation(true);
		rowInLine1.add(keyboardButton);

		rowsInLine.add(rowInLine1);

		replyKeyboardMarkup.setKeyboard(rowsInLine);
		replyKeyboardMarkup.setResizeKeyboard(true);
		return replyKeyboardMarkup;
	}
}
