package com.karpov.astrobot.handlers;

import com.karpov.astrobot.repo.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@Slf4j
public class MessageHandler {

	private final CommandHandler commandHandler;
	private final ChatRepository chatRepository;

	public MessageHandler(CommandHandler commandHandler, ChatRepository chatRepository) {
		this.commandHandler = commandHandler;
		this.chatRepository = chatRepository;
	}

	public SendMessage handleMessage(Update update) {
		Long chatId = update.getMessage().getChatId();
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		if (update.getMessage().hasEntities()) {
			for (MessageEntity entity : update.getMessage().getEntities()
			) {
				if (entity.getType().equals("bot_command")) {
					sendMessage = commandHandler.handleCommand(update, entity.getText());
					break;
				} else {
					sendMessage.setText("Message is not recognized");
					log.warn("Unexpected behavior, can't handle an incoming update with entities: {}", update);
				}
			}
			return sendMessage;
		} else if (update.getMessage().hasLocation()) {
			Location location = update.getMessage().getLocation();
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			chatRepository.updateLongitudeAndLatitudeById(chatId,latitude,longitude);
			sendMessage.setText("Location is successfully set. Latitude: " + latitude + ", Longitude: " + longitude + ".");
			sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
			log.info("Location for chat is set: chatId={}, latitude={}, longitude={}", chatId, latitude, longitude);
			return sendMessage;
		} else {
			sendMessage.setText("Message is not recognized");
			log.info("Unexpected behavior, can't handle an incoming update with message: chatId={}, update={}", chatId, update);
			return sendMessage;
		}
	}
}
