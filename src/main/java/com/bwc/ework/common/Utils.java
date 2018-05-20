package com.bwc.ework.common;

public class Utils {
	public static boolean isDefaultCompany(String companyid){
		return companyid == null || companyid.length() == 0 || Consts.DefaultCompanyId.equals(companyid);
	}
	
	public static String getStoreCompanyid(String companyid){
		if(isDefaultCompany(companyid)){
			return Consts.DefaultCompanyId;
		}else{
			return companyid;
		}
	}
	
	public static String createRedirectUrl(String companyid, String openid, String toUrl){
		return "http://www.freertokyo.com/ework/redirectautologin.do?companyid=" + companyid 
				+"&openid=" + openid + "&tourl=" + toUrl;
	}

}
