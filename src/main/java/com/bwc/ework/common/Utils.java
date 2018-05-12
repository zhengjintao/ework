package com.bwc.ework.common;

public class Utils {
	public static boolean isDefaultCompany(String companyid){
		return companyid == null || companyid.length() == 0 || Consts.DefaultCompanyId.equals(companyid);
	}

}
