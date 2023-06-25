package com.karpov.astrobot.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@Service
public class AuroraForecastService {

	public String get3DayAuroraForecast() {
		try {
			URL url = new URL("https://services.swpc.noaa.gov/text/3-day-forecast.txt");
			Scanner scanner = new Scanner(url.openStream());
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<pre>");
			for (int i = 0; i < 22; i++) {
				stringBuilder.append(scanner.nextLine()).append(System.lineSeparator());
			}
			stringBuilder.append("</pre>");
			scanner.close();
			return stringBuilder.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
			return "Service unavailable";
		}
	}

	public String get27DayAuroraForecast() {
		try {
			URL url = new URL("https://services.swpc.noaa.gov/text/27-day-outlook.txt");
			Scanner scanner = new Scanner(url.openStream());
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<pre>");
			while(scanner.hasNextLine()) {
				stringBuilder.append(scanner.nextLine()).append(System.lineSeparator());
			}
			stringBuilder.append("</pre>");
			scanner.close();
			return stringBuilder.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
			return "Service unavailable";
		}
	}
}
