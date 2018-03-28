package com.bwc.ework.form;

import java.util.List;
import java.util.Date;

public class BillDetail {
	private String name;
	private Date begindate;
	private Date enddate;
	private String createdate;
	private List<BillInfo> bilist;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBegindate() {
		return begindate;
	}
	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
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
