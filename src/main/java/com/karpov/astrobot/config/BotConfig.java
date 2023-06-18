package com.karpov.astrobot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {

	@Value("${bot.username}")
	private String botUsername;

	@Value("${bot.webHookPath}")
	private String botWebHookPath;

	@Value("${bot.token}")
	private String botToken;

}
