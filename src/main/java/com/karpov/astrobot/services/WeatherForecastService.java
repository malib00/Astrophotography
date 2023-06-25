package com.karpov.astrobot.services;

import com.karpov.astrobot.config.WeatherAPIConfig;
import com.karpov.astrobot.models.weather.CurrentWeather;
import com.karpov.astrobot.models.weather.parts.Current;
import com.karpov.astrobot.models.weather.parts.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherForecastService {

	private final WeatherAPIConfig weatherAPIConfig;
	private final RestTemplate restTemplate;

	public WeatherForecastService(WeatherAPIConfig weatherAPIConfig, RestTemplate restTemplate) {
		this.weatherAPIConfig = weatherAPIConfig;
		this.restTemplate = restTemplate;
	}

	public String getCurrentWeather(Double latitude, Double longitude) {
		String url = "https://api.weatherapi.com/v1/current.json?key={token}&q={location}&aqi={airQualityData}";
		String coordinates = latitude + "," + longitude;
		ResponseEntity<CurrentWeather> response = this.restTemplate.getForEntity(
				url,
				CurrentWeather.class,
				weatherAPIConfig.getWeatherAPIToken(),
				coordinates,
				"no");
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				CurrentWeather currentWeather = response.getBody();
				Location location = currentWeather.getLocation();
				Current current = currentWeather.getCurrent();
				String currentWeatherFormattedText = "Current Weather in " + location.getName() + ".\n\n" +
						"Cloud cover: " + current.getCloud() + "%" + ".\n" +
						"t = " + Math.round(current.getTemp_c()) + "°C, feels like = " + current.getFeelslike_c() + "°C.\n" +
						"Wind = " + (float) Math.round(current.getWind_kph() / 3.6 * 10) / 10 + "m/s. Wind gust = " +
						(float) Math.round(current.getGust_kph() / 3.6 * 10) / 10 + "m/s.\n" +
						"Wind direction = " + current.getWind_dir() + " (" +
						current.getWind_degree() + "°).\n" +
						"Humidity = " + current.getHumidity() + "%.\n";
				return currentWeatherFormattedText;
			} catch (NullPointerException e) {
				log.error("WeatherAPI: Error during parsing CurrentWeather in  received response: response={}", response, e);
				return "Service unavailable.";
			}
		} else {
			log.error("WeatherAPI: Current Weather service is unavailable, responseStatusCode={}", response.getStatusCode());
			return "Service unavailable.";
		}
	}
}
