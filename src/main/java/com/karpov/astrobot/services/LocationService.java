package com.karpov.astrobot.services;

import com.karpov.astrobot.models.Location;
import com.karpov.astrobot.repo.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LocationService {

	private final ChatRepository chatRepository;

	public LocationService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	public boolean isParsebleCoordinates(String coordinates) {
		Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{2,6},[0-9]{2}\\.[0-9]{2,6}");
		Matcher matcher = pattern.matcher(coordinates);
		return matcher.find();
	}

	public Location parseAndSetCoordinates(Long id, String coordinates) {
		Pattern pattern = Pattern.compile("[0-9]{2}\\.[0-9]{2,6}");
		Matcher matcher = pattern.matcher(coordinates);
		matcher.find();
		Double latitude = Double.parseDouble(matcher.group());
		matcher.find();
		Double longitude = Double.parseDouble(matcher.group());
		chatRepository.updateLongitudeAndLatitudeById(id,latitude,longitude);
		return new Location(latitude,longitude);
	}
}
