package com.karpov.astrobot.models.weather.parts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//there is more fields available through WeatherAPI
public class Current {

	private String last_updated;
	private Float temp_c;
	private Integer is_day;
	private Float wind_kph;
	private Integer wind_degree;
	private String wind_dir;
	private Integer precip_mm;
	private Integer humidity;
	private Integer cloud;
	private Integer feelslike_c;
	private Float gust_kph;

}
