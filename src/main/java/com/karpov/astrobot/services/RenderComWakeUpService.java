package com.karpov.astrobot.services;

import com.karpov.astrobot.config.BotConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//to keep bot from "falling asleep" because of inactivity on free tier on render.com
@Service
public class RenderComWakeUpService {

	private final RestTemplate restTemplate;
	private final BotConfig botConfig;

	public RenderComWakeUpService(RestTemplate restTemplate, BotConfig botConfig) {
		this.restTemplate = restTemplate;
		this.botConfig = botConfig;
	}

	@Scheduled(fixedRate = 840000)
	public void makeScheduledGetRequest() {
		String url = botConfig.getBotWebHookPath()+"/health";
		this.restTemplate.getForObject(url, String.class);
	}
}
