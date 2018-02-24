package com.bwc.ework.common;

public class HashEncoder {

	public static String getResult(String inputStr) {
		return String.valueOf(inputStr.hashCode());
	}
}
