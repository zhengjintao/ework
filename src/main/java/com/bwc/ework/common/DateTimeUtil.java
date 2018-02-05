package com.bwc.ework.common;

import java.sql.Time;

public class DateTimeUtil {
	public String formatTime(Time time){
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

}
