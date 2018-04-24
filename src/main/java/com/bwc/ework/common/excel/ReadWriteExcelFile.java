package com.bwc.ework.common.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bwc.ework.common.StringUtil;
import com.bwc.ework.form.BillDetail;
import com.bwc.ework.form.BillInfo;

public class ReadWriteExcelFile {
	public static String readXLSFile(BillDetail bd) throws IOException  
	  {  
		String path = Thread.currentThread().getContextClassLoader().getResource("billtmp.xls").getPath(); 
	    InputStream ExcelFileToRead = new FileInputStream(path);  
	    HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);  
	  
	    HSSFSheet sheet = wb.getSheetAt(0);  
	    HSSFRow row2 = sheet.getRow(2);
	    row2.getCell(0).setCellValue(bd.getBegindate().getYear()+ 1900); // 年
	    row2.getCell(3).setCellValue(bd.getBegindate().getMonth() + 1); // 月（开始）
	    row2.getCell(5).setCellValue(bd.getBegindate().getDate()); // 日（开始）
	    row2.getCell(8).setCellValue(bd.getEnddate().getMonth() + 1); // 月（末尾）
	    row2.getCell(10).setCellValue(bd.getEnddate().getDate()); // 月（末日）
	    HSSFRow row6 = sheet.getRow(6);
	    row6.getCell(1).setCellValue(bd.getName()); // 姓名
	    
	    int rowinx = 9;
	    for(BillInfo bi : bd.getBilist()){
	    	HSSFRow row = sheet.getRow(rowinx);
	    	row.getCell(0).setCellValue(bi.getDate().substring(5, 10)); // 日
	    	row.getCell(1).setCellValue(bi.getTo()); // 行き先
	    	row.getCell(5).setCellValue(bi.getFrom() + " - " + bi.getTo()); // 区間
	    	row.getCell(13).setCellValue(new Double(bi.getMoney()).doubleValue()); // 料金
	    	row.getCell(14).setCellValue(bi.getComment()); // 備考（理由）
	    	rowinx++;
	    }  
	    
	    String date = String.valueOf(bd.getBegindate().getYear()+ 1900) + StringUtil.padLeft(String.valueOf(bd.getEnddate().getMonth() + 1), 2, '0');
	    
	    String outpath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "车费报销(" + bd.getName()+ date +").xls"; 
	    FileOutputStream fileOut = new FileOutputStream(outpath);  
		  
	    // write this workbook to an Outputstream.  
	    wb.write(fileOut);  
	    fileOut.flush();  
	    fileOut.close();  
	 
	    return outpath;
	  } 
	
}
