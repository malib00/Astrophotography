package com.karpov.astrobot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WeatherAPIConfig {

	@Value("${weatherAPI.token}")
	private String weatherAPIToken;
}
