package com.karpov.astrobot.services;

import com.karpov.astrobot.config.WeatherAPIConfig;
import com.karpov.astrobot.models.weather.CurrentWeather;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherForecastService {

	private final WeatherAPIConfig weatherAPIConfig;
	private final RestTemplate restTemplate;

	public WeatherForecastService(WeatherAPIConfig weatherAPIConfig, RestTemplate restTemplate) {
		this.weatherAPIConfig = weatherAPIConfig;
		this.restTemplate = restTemplate;
	}

	public CurrentWeather getCurrentWeather(String coordinates) {
		String url = "https://api.weatherapi.com/v1/current.json?key={token}&q={location}&aqi={airQualityData}";
		ResponseEntity<CurrentWeather> response = this.restTemplate.getForEntity(
				url,
				CurrentWeather.class,
				weatherAPIConfig.getWeatherAPIToken(),
				coordinates,
				"no");
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}
}
