package com.bwc.ework.form;

import java.util.List;

public class BillDetail {
	private String name;
	private String begindate;
	private String enddate;
	private String createdate;
	private List<BillInfo> bilist;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public List<BillInfo> getBilist() {
		return bilist;
	}
	public void setBilist(List<BillInfo> bilist) {
		this.bilist = bilist;
	}
	
}
