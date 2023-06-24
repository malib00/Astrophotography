package com.karpov.astrobot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.karpov.astrobot.config.BotConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.Set;

@Service
public class CustomMessageService {

	private final RestTemplate restTemplate;
	private final BotConfig botConfig;

	public CustomMessageService(RestTemplate restTemplate, BotConfig botConfig) {
		this.restTemplate = restTemplate;
		this.botConfig = botConfig;
	}

	public void deleteMessage(Long chatId, Integer messageId) {
		try {
			ResponseEntity<Message> responseEntity = restTemplate.getForEntity("https://api.telegram.org/bot{botToken}/deleteMessage?chat_id={chatId}&message_id={messageId}",
					Message.class,
					botConfig.getBotToken(),
					chatId,
					messageId);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
	}

	public HttpStatusCode sendPhotoFromHTTP(String chatId, String http) {
		try {
			ResponseEntity<Message> responseEntity = restTemplate.getForEntity("https://api.telegram.org/bot{botToken}/sendPhoto?chat_id={chatId}&photo={photoLink}",
					Message.class,
					botConfig.getBotToken(),
					chatId,
					http);
			return responseEntity.getStatusCode();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return e.getStatusCode();
		}

	}

	// set should be from 2 to 10
	public HttpStatusCode sendPhotoGroupFromHTTP(String chatId, Set<InputMediaPhoto> set) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json = mapper.writeValueAsString(set);

			ResponseEntity<Message> responseEntity = restTemplate.getForEntity("https://api.telegram.org/bot{botToken}/sendMediaGroup?chat_id={chatId}&media={json}",
					Message.class,
					botConfig.getBotToken(),
					chatId,
					json);
			return responseEntity.getStatusCode();
		} catch (HttpClientErrorException | JsonProcessingException e) {
			e.printStackTrace();
			return HttpStatusCode.valueOf(404);
		}
	}
}
