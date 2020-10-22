package siemens.energy.org.crm.selenium.common;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * This class is containing testng data provider method user for getting data from spreadsheet          
 * 
 */
public class DataManager
{
	public static XSSFWorkbook workbook;
	public static XSSFSheet worksheet;
	public static DataFormatter formatter = new DataFormatter();

	// Getting data from spreadsheet by passing file path and sheet name
	public static Object[][] getData(String filePath, String sheetName) throws IOException
	{
		// Excel sheet file location get mentioned here
		FileInputStream fileInputStream = new FileInputStream(filePath);

		// Get workbook
		workbook = new XSSFWorkbook(fileInputStream);

		// Get sheet from workbook
		worksheet = workbook.getSheet(sheetName);

		// Get row which start from 0
		XSSFRow Row = worksheet.getRow(0);

		// Count number of rows
		int RowNum = worksheet.getPhysicalNumberOfRows();
		
		// Get last cell number in ColNum
		int ColNum = Row.getLastCellNum();

		// Pass count data in array
		Object Data[][] = new Object[RowNum - 1][ColNum];
		
		// Loop work for Rows
		for (int i = 0; i < RowNum - 1; i++)
		{
			XSSFRow row = worksheet.getRow(i + 1);

			// Loop work for colNum
			for (int j = 0; j < ColNum; j++)
			{
				if (row == null)
					Data[i][j] = "";
				else
				{
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						
						// if it get Null value it pass no data
						Data[i][j] = "";
					else
					{
						// This formatter get all values as string i.e integer, float all type data
						String value = formatter.formatCellValue(cell);
						
						// value
						Data[i][j] = value;
					}
				}
			}
		}
		return Data;
	}
}
