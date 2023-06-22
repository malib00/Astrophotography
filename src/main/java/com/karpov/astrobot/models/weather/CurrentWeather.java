package com.karpov.astrobot.models.weather;

import com.karpov.astrobot.models.weather.parts.Current;
import com.karpov.astrobot.models.weather.parts.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CurrentWeather {

	private Location location;
	private Current current;

}
