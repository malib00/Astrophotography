package com.karpov.astrobot.handlers;

import com.karpov.astrobot.keyboards.InlineKeyboards.AuroraKeyboard;
import com.karpov.astrobot.keyboards.InlineKeyboards.MainMenuKeyboard;
import com.karpov.astrobot.keyboards.InlineKeyboards.SettingsKeyboard;
import com.karpov.astrobot.keyboards.InlineKeyboards.WeatherKeyboard;
import com.karpov.astrobot.keyboards.ReplyKeyboards.LocationReplyKeyboard;
import com.karpov.astrobot.models.Chat;
import com.karpov.astrobot.repo.ChatRepository;
import com.karpov.astrobot.services.AuroraForecastService;
import com.karpov.astrobot.services.WeatherForecastService;
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
	private final LocationReplyKeyboard locationReplyKeyboard;
	private final WeatherKeyboard weatherKeyboard;
	private final WeatherForecastService weatherForecastService;
	private final ChatRepository chatRepository;

	public InlineQueryHandler(AuroraKeyboard auroraKeyboard,
	                          MainMenuKeyboard mainMenuKeyboard,
	                          AuroraForecastService auroraForecast,
	                          SettingsKeyboard settingsKeyboard,
	                          LocationReplyKeyboard locationReplyKeyboard,
	                          WeatherKeyboard weatherKeyboard, WeatherForecastService weatherForecastService, ChatRepository chatReposotory) {
		this.auroraKeyboard = auroraKeyboard;
		this.mainMenuKeyboard = mainMenuKeyboard;
		this.auroraForecast = auroraForecast;
		this.settingsKeyboard = settingsKeyboard;
		this.locationReplyKeyboard = locationReplyKeyboard;
		this.weatherKeyboard = weatherKeyboard;
		this.weatherForecastService = weatherForecastService;
		this.chatRepository = chatReposotory;
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
			case ("WeatherButton"):
				editMessageText.setText("Astrophotography Helper\n\nWeather Forecast");
				editMessageText.setReplyMarkup(weatherKeyboard.getWeatherInlineKeyboard());
				return editMessageText;
			case ("CurrentWeatherButton"):
				Chat chat = chatRepository.findById(callbackQuery.getMessage().getChatId()).get();
				Double latitude = chat.getLatitude();
				Double longitude = chat.getLongitude();
				if (latitude!= null && longitude!=null) {
					editMessageText.setText("Astrophotography Helper\n\n" + weatherForecastService.getCurrentWeatherText(latitude,longitude));
				} else {
					editMessageText.setText("Astrophotography Helper\n\nPlease set location in Settings");
				}
				editMessageText.setReplyMarkup(weatherKeyboard.getWeatherInlineKeyboard());
				return editMessageText;
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
			case ("SetLocationButton"):
				String messageText = "2 options available to set location:\n\n" +
						"1. Set location automatically by pushing the keyboard button (Set location automatically) at the bottom.\n\n" +
						"2. Set location manually by typing coordinates (latitude and longitude) in decimals degrees after /location command.\n" +
						"   Examples:\n" +
						"   /location 27.380411,33.632224\n" +
						"   or\n" +
						"   /location 27.38,33.63\n" +
						"   where 27.380411 - latitude, 33.632224 - longitude.";
				SendMessage sendMessage = new SendMessage(callbackQuery.getMessage().getChatId().toString(), messageText);
				sendMessage.setReplyMarkup(locationReplyKeyboard.getLocationReplyKeyboard());
				return sendMessage;
			default:
				return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(), "Update has query, but not implemented");
		}
	}
}
