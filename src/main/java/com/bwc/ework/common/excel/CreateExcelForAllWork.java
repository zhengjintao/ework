package com.bwc.ework.common.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.bwc.ework.common.JdbcUtil;

public class CreateExcelForAllWork {
	public static String readXLSFile(String companyCd, String companyname, String date)
			throws IOException, ParseException {
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String path = Thread.currentThread().getContextClassLoader().getResource("work.xls").getPath();
		InputStream ExcelFileToRead = new FileInputStream(path);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		SetModel(companyname, date, wb);
		List<String[]> dataList = getWorkData(companyCd, year, month);
		if (dataList.size() > 0) {
			int sheetIndex = 1;
			String username = dataList.get(0)[0];
			HSSFSheet sheet = wb.cloneSheet(0);
			HSSFRow rowname = sheet.getRow(4);
			rowname.getCell(2).setCellValue(username);
			wb.setSheetName(sheetIndex, username);
			
			for (String[] data : dataList) {
				int day = Integer.parseInt(data[1].split("-")[2]);
				if (!data[0].equals(username)) {
					username = data[0];
					sheet = wb.cloneSheet(0);
					rowname = sheet.getRow(4);
					rowname.getCell(2).setCellValue(username);
					sheetIndex = sheetIndex+1;
					wb.setSheetName(sheetIndex, username);
				}

				HSSFRow row = sheet.getRow(6 + day);
				// 作业内容
				if(data[7].equals("1")){
					row.getCell(14).setCellValue(data[6]);
				}else{
					// 开始时间
					row.getCell(2).setCellValue(data[2].substring(0, 8));
					// 结束时间
					row.getCell(4).setCellValue(data[3].substring(0, 8));
					// 休息时间
					row.getCell(6).setCellValue(data[4]);
					// 时间
					row.getCell(7).setCellValue(new Double(data[5]).doubleValue());
					row.getCell(8).setCellValue(data[6]);
				}
				
			}
		}
		
		wb.setSheetHidden(0, 1);
		wb.setActiveSheet(1);
		String outpath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "全员出勤统计.xls";
		FileOutputStream fileOut = new FileOutputStream(outpath);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return outpath;
	}

	private static List<String[]> getWorkData(String companyCd, String year, String month) {
		List<String[]> dataList = new ArrayList<String[]>();
		String sql2 = "select * from (select mu.username,cw.date,cw.begintime,cw.endtime,cw.diyresttime,cw.worktime,cw.comment, '0' as flg from cdata_worktime cw join cdata_companyuser cc "
				+ "on cw.userid = cc.userid join mstr_user mu on cc.userid = mu.userid where cc.companyid =? and cw.year = ? and "
				+ "cw.month = ? and cc.delflg = '0' union select mu.username,cl.leavedate as date,'' as begintime,'' as endtime,'' as diyresttime,'' as worktime,cl.content as comment,'1' as flg "
                + "from cdata_leave cl join cdata_companyuser cc on cl.userid = cc.userid join mstr_user mu on cc.userid = mu.userid "
                + "where cc.companyid =? and cl.year =? and cl.month =? and cc.delflg = '0' ) tbl order by username,date";

		Object[] params2 = new Object[6];
		params2[0] = companyCd;
		params2[1] = year;
		params2[2] = month;
		params2[3] = companyCd;
		params2[4] = year;
		params2[5] = month;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql2, params2);
		for (Object result : list1) {
			Map<String, Object> row = (Map<String, Object>) result;
			String[] data = new String[8];
			// 用户名
			data[0] = row.get("username").toString();
			// 日期
			data[1] = row.get("date").toString();
			// 开始时间
			data[2] = row.get("begintime").toString();
			// 结束时间
			data[3] = row.get("endtime").toString();
			// 休息时间
			data[4] = row.get("diyresttime") == null ? "1":row.get("diyresttime").toString();
			// 工作时间
			data[5] = row.get("worktime").toString();
			// 备注
			data[6] = row.get("comment").toString();
			data[7] = row.get("flg").toString();
			dataList.add(data);
		}
		return dataList;
	}

	private static void SetModel(String companyname, String date, HSSFWorkbook wb) throws ParseException {
		HSSFSheet sheet = wb.getSheetAt(0);
		sheet.getRow(2).getCell(2).setCellValue(date);
		sheet.getRow(3).getCell(2).setCellValue(companyname);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date + "-01"));
		int dayinweek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	    HSSFCellStyle style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.CORAL.getIndex());  
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("ＭＳ Ｐゴシック");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        
		for (int i = 1; i < days; i++) {
			sheet.getRow(6 + i).getCell(1).setCellValue(date + "-" + i);
			dayinweek  = dayinweek+1;
			if(dayinweek == 1 || dayinweek == 7){
				sheet.getRow(6 + i).getCell(1).setCellStyle(style);
			}
			if(dayinweek == 7){
				dayinweek = 0;
			}
		}
	}
}
