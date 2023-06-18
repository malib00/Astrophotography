package com.karpov.astrobot.services;

import com.karpov.astrobot.bot.AstroBot;
import com.karpov.astrobot.config.BotConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendPhotoService {

	private final RestTemplate restTemplate;
	private final BotConfig botConfig;

	public SendPhotoService(RestTemplate restTemplate, AstroBot astroBot, BotConfig botConfig) {
		this.restTemplate = restTemplate;
		this.botConfig = botConfig;
	}

	public void sendPhotoFromHTTP(String chatId, String http) {
		restTemplate.exchange("https://api.telegram.org/bot{botToken}/sendPhoto?chat_id={chatId}&photo={photoLink}",
				HttpMethod.POST,
				HttpEntity.EMPTY,
				String.class,
				botConfig.getBotToken(),
				chatId,
				http
		);
	}
}
