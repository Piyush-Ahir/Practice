package com.comcast.crm.generic.fileutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	public String getDataFromExcel(String sheetName, int rowNum, int cellNum) throws Throwable {
		FileInputStream fis1 = new FileInputStream("./testdata/contactTestScriptdata.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		
		return wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).toString();
	}

	public int getRowCount(String sheetName) throws Throwable {
		FileInputStream fis1 = new FileInputStream("./testdata/contactTestScriptdata.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);

		return wb.getSheet(sheetName).getLastRowNum();
	}

	public void setDataIntoExcel(String sheetName, int rowNum, int cellNum, String data) throws Throwable {
		FileInputStream fis1 = new FileInputStream("./testdata/contactTestScriptdata.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);

		Cell cell = wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum);

		cell.setCellValue(data);
		

		FileOutputStream fos = new FileOutputStream("./testdata/contactTestScriptdata.xlsx");
		wb.write(fos);
		wb.close();
		System.out.println("-----Data Written Successfully-----");
	}
}
