package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.AuroraKeyboard;
import com.karpov.astrobot.keyboards.MainMenuKeyboard;
import com.karpov.astrobot.keyboards.SettingsKeyboard;
import com.karpov.astrobot.services.AuroraForecastService;
import com.karpov.astrobot.services.SendPhotoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class InlineQueryHandler {

	private final AuroraKeyboard auroraKeyboard;
	private final MainMenuKeyboard mainMenuKeyboard;
	private final AuroraForecastService auroraForecast;
	private final SettingsKeyboard settingsKeyboard;

	public InlineQueryHandler(AuroraKeyboard auroraKeyboard, MainMenuKeyboard mainMenuKeyboard, AuroraForecastService auroraForecast, SettingsKeyboard settingsKeyboard) {
		this.auroraKeyboard = auroraKeyboard;
		this.mainMenuKeyboard = mainMenuKeyboard;
		this.auroraForecast = auroraForecast;
		this.settingsKeyboard = settingsKeyboard;
	}

	public BotApiMethod<?> handleInlineQuery(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		String callbackQueryData = callbackQuery.getData();

		EditMessageText editMessageText = new EditMessageText();
		editMessageText.setChatId(callbackQuery.getMessage().getChatId());
		editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
		MessageEntity messageEntity = new MessageEntity("code", 0, 23);
		editMessageText.setEntities(Arrays.asList(messageEntity));

		switch (callbackQueryData) {
			case ("ExitButton"):
				return new DeleteMessage(callbackQuery.getFrom().getId().toString(), callbackQuery.getMessage().getMessageId());
			case ("AuroraButton"):
				editMessageText.setText("Astrophotography Helper\n\nAurora forecast");
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("BackToMainMenuButton"):
				editMessageText.setText("Astrophotography Helper\n\nMain Menu");
				editMessageText.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return editMessageText;
			case ("3DayForecastButton"):
				editMessageText.setText("Astrophotography Helper\n\n" + auroraForecast.get3DayAuroraForecast());
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("27DayForecastButton"):
				editMessageText.setText("Astrophotography Helper\n\n" + auroraForecast.get27DayAuroraForecast());
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("SettingsButton"):
				editMessageText.setText("Astrophotography Helper\n\nSettings");
				editMessageText.setReplyMarkup(settingsKeyboard.getSettingsInlineKeyboard());
				return editMessageText;
			default:
				return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(), "Update has query, but not implemented");
		}
	}
}
