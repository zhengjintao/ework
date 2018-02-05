package com.bwc.ework.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.bwc.ework.form.Date;

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
		Date date= new Date();
		date.setYear(str.substring(0, 4));
		date.setMonth(str.substring(5, 7));
		date.setDay(str.substring(8, 10));
		return date;
	}

	public static Time stringToTime(String str) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			return new Time(sdf.parse(str).getTime());
		} catch (ParseException e) {
			
			sdf = new SimpleDateFormat("HH:mm");
			try {
				return new Time(sdf.parse(str).getTime());
			} catch (ParseException ex) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}
}
