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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@Slf4j
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
		Long chatId = callbackQuery.getMessage().getChatId();
		editMessageText.setChatId(chatId);
		Integer messageId = callbackQuery.getMessage().getMessageId();
		editMessageText.setMessageId(messageId);
		editMessageText.setParseMode("HTML");

		switch (callbackQueryData) {
			case ("ExitButton"):
				return new DeleteMessage(callbackQuery.getFrom().getId().toString(), messageId);
			case ("WeatherButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nWeather Forecast");
				editMessageText.setReplyMarkup(weatherKeyboard.getWeatherInlineKeyboard());
				return editMessageText;
			case ("CurrentWeatherButton"):
				Optional<Chat> chatOptional = chatRepository.findById(chatId);
				if (chatOptional.isPresent()) {
					Chat chat = chatOptional.get();
					Double latitude = chat.getLatitude();
					Double longitude = chat.getLongitude();
					if (latitude!= null && longitude!=null) {
						editMessageText.setText("<pre>Astrophotography Helper</pre>\n\n" + weatherForecastService.getCurrentWeather(latitude,longitude));
					} else {
						editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nPlease set location in Settings");
					}
				} else {
					editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nPlease register bot by sending /start command");
					log.warn("Access to CurrentWeatherButton bypassing initial /start command: chatId={}", chatId);
				}
				editMessageText.setReplyMarkup(weatherKeyboard.getWeatherInlineKeyboard());
				return editMessageText;
			case ("AuroraButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nAurora forecast");
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("BackToMainMenuButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nMain Menu");
				editMessageText.setReplyMarkup(mainMenuKeyboard.getMainMenuInlineKeyboard());
				return editMessageText;
			case ("3DayForecastButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\n" + auroraForecast.get3DayAuroraForecast());
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("27DayForecastButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\n" + auroraForecast.get27DayAuroraForecast());
				editMessageText.setReplyMarkup(auroraKeyboard.getAuroraInlineKeyboard());
				return editMessageText;
			case ("SettingsButton"):
				editMessageText.setText("<pre>Astrophotography Helper</pre>\n\nSettings");
				editMessageText.setReplyMarkup(settingsKeyboard.getSettingsInlineKeyboard());
				return editMessageText;
			case ("SetLocationButton"):
				String messageText = "2 options available to set location:\n\n" +
						"1. Set location automatically by pushing the keyboard button (Set location automatically) at the bottom.\n\n" +
						"2. Set location manually by typing coordinates (latitude and longitude) in <u>decimals degrees</u> after /location command.\n" +
						"<i>Examples:\n" +
						"/location 27.38,33.63\n" +
						"/location 27.380411,33.632224\n" +
						"where 27.380411 - latitude, 33.632224 - longitude.</i>";
				SendMessage sendMessage = new SendMessage(chatId.toString(), messageText);
				sendMessage.setParseMode("HTML");
				sendMessage.setReplyMarkup(locationReplyKeyboard.getLocationReplyKeyboard());
				return sendMessage;
			default:
				log.warn("Update has an unrecognized callbackQuery: chatId={}, update={}", chatId, update);
				return new SendMessage(chatId.toString(), "Message is not recognized");
		}
	}
}
