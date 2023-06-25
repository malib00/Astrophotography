package com.karpov.astrobot.services;

import com.karpov.astrobot.models.ChatLocation;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LocationService {

	public boolean isParsebleCoordinates(String coordinates) {
		Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{2,6},[0-9]{2}\\.[0-9]{2,6}");
		Matcher matcher = pattern.matcher(coordinates);
		return matcher.find();
	}

	public ChatLocation parseCoordinates(String coordinates) {
		Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{2,6}");
		Matcher matcher = pattern.matcher(coordinates);
		matcher.find();
		Double latitude = Double.parseDouble(matcher.group());
		matcher.find();
		Double longitude = Double.parseDouble(matcher.group());
		return new ChatLocation(latitude,longitude);
	}
}
