package com.karpov.astrobot.models.weather.parts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
//there is more fields available through WeatherAPI
public class Current {

	private String last_updated;
	private Integer last_updated_epoch;
	private BigDecimal temp_c;
	private BigDecimal temp_f;
	private Integer is_day;
	private BigDecimal wind_kph;
	private Integer wind_degree;
	private String wind_dir;
	private Integer precip_mm;
	private Integer humidity;
	private Integer cloud;
	private Integer feelslike_c;
	private BigDecimal gust_kph;

}
