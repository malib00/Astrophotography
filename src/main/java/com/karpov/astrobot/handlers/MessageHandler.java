package com.karpov.astrobot.handlers;

import com.karpov.astrobot.repo.ChatRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
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
			sendMessage.setText("Got entities, but can't handle it ");
			for (MessageEntity entity : update.getMessage().getEntities()
			) {
				if (entity.getType().equals("bot_command")) {
					sendMessage = commandHandler.handleCommand(update, entity.getText());
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
			return sendMessage;
		} else {
			sendMessage.setText("Message is not recognized");
			return sendMessage;
		}
	}
}
