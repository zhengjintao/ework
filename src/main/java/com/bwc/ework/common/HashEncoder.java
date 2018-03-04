package com.bwc.ework.common;

public class HashEncoder {

	public static String getResult(String inputStr) {
		return inputStr == null ? null : String.valueOf(inputStr.hashCode());
	}
}
