package com.example.cinemakiosk.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime().toLocalDate();
	}

	public static Date convertToDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
