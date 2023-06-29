package com.karpov.astrobot;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class AuroraForecastServiceTests {

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void get3DayAuroraForecastTest() {
		assertThat(this.restTemplate.getForObject("https://services.swpc.noaa.gov/text/3-day-forecast.txt",
				String.class)).contains(":Product: 3-Day Forecast");
	}

	@Test
	public void get27DayAuroraForecastTest() {
		assertThat(this.restTemplate.getForObject("https://services.swpc.noaa.gov/text/27-day-outlook.txt",
				String.class)).contains(":Product: 27-day Space Weather");
	}
}
