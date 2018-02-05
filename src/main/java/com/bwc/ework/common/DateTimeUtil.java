package com.bwc.ework.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	public static String formatTime(Time time) {
		int hour = time.getHours();
		int minute = time.getMinutes();
		String hourString;
		String minuteString;

		if (hour < 10) {
			hourString = "0" + hour;
		} else {
			hourString = Integer.toString(hour);
		}
		if (minute < 10) {
			minuteString = "0" + minute;
		} else {
			minuteString = Integer.toString(minute);
		}
		return (hourString + ":" + minuteString);
	}

	public static Date stringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static Time stringToTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return new Time(sdf.parse(str).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
