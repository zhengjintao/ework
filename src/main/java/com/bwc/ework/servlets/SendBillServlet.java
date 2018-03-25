package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.excel.ReadWriteExcelFile;
import com.bwc.ework.form.BillDetail;
import com.bwc.ework.form.BillInfo;

/**
 * Servlet implementation class SendBillServlet
 */
public class SendBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendBillServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BillDetail bd = new BillDetail();
		bd.setName("郑");
		bd.setBegindate("2018/1/3");
		bd.setEnddate("2018/1/31");
		List<BillInfo> bilist = new ArrayList<BillInfo>();
		BillInfo bi = new BillInfo();
		bi.setDate("2018/1/11");
		bi.setFrom("わらび");
		bi.setTo("五反田");
		bi.setMoney("111");
		bi.setComment("定期券");
		bilist.add(bi);
		bd.setBilist(bilist);
		ReadWriteExcelFile.readXLSFile(bd);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
