package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.AuroraKeyboard;
import com.karpov.astrobot.keyboards.MainMenuKeyboard;
import com.karpov.astrobot.services.SendPhotoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InlineQueryHandler {

	private final SendPhotoService sendPhotoService;
	private final AuroraKeyboard auroraKeyboard;
	private final MainMenuKeyboard mainMenuKeyboard;

	public InlineQueryHandler(SendPhotoService sendPhotoService, AuroraKeyboard auroraKeyboard, MainMenuKeyboard mainMenuKeyboard) {
		this.sendPhotoService = sendPhotoService;
		this.auroraKeyboard = auroraKeyboard;
		this.mainMenuKeyboard = mainMenuKeyboard;
	}

	public BotApiMethod<?> handleInlineQuery(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		String callbackQueryData = callbackQuery.getData();

		EditMessageText editMessageText = new EditMessageText();
		editMessageText.setChatId(callbackQuery.getMessage().getChatId());
		editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());

		switch (callbackQueryData) {
			case ("ExitButton"):
				return new DeleteMessage(callbackQuery.getFrom().getId().toString(), callbackQuery.getMessage().getMessageId());
			case ("AuroraButton"):
				editMessageText.setText("Aurora forecast");
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("BackToMainMenuButton"):
				editMessageText.setText("Main Menu");
				editMessageText.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return editMessageText;
			case ("3DayForecastButton"):
				editMessageText.setText("3 day forecast");
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			default:
				return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(), "Update has query, but not implemented");
		}
	}
}
