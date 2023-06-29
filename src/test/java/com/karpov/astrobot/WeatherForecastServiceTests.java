package com.karpov.astrobot;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherForecastServiceTests {

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void getCurrentWeatherJSONTest() {
		assertThat(this.restTemplate.getForObject("https://api.weatherapi.com/v1/current.json?key=" + System.getenv("WEATHERAPI_TOKEN") + "&q=40.715322,-73.999971&aqi=no",
				String.class)).contains("New York");
	}
}
