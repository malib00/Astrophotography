package com.karpov.astrobot.models.weather.parts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
//there is more fields available through WeatherAPI
public class Location {

	private String name;
	private String region;
	private String country;
	private BigDecimal lat;
	private BigDecimal lon;
	private String localtime;

}
