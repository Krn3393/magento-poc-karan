package com.magento.framework.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class ExcelHelper {
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static HSSFCell cell;
	public static HSSFRow row;
	// public static final String filePath = ".//test_data//";

	/**
	 * @author ddoshi
	 * @param filepath
	 * @param sheetName to set excel file for perform read/write operations
	 */
	public static void setExcelFile(String fileName, String sheetName) {
		File file = new File(fileName);
		try {
			FileInputStream ExcelFile = new FileInputStream(file.getAbsolutePath());
			workbook = new HSSFWorkbook(ExcelFile);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean createAnExcel(String fileName, String sheetName) {
		workbook = new HSSFWorkbook();
		OutputStream fileOut = null;
		File f = new File(fileName);
		if (!f.exists()) {
			try {
				fileOut = new FileOutputStream(f.getAbsolutePath());
				sheet = workbook.createSheet(sheetName);
				workbook.write(fileOut);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author ddoshi
	 * @param RowNum
	 * @param ColNum
	 * @return excel cell data
	 */
	@SuppressWarnings("deprecation")
	public static String getCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		String cellData;
		// DataFormatter dataFormatter = new DataFormatter();
		try {
			setExcelFile(fileName, sheetName);
			cell = sheet.getRow(RowNum).getCell(ColNum);
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				cellData = String.valueOf((int) cell.getNumericCellValue());
			else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
				cellData = String.valueOf(cell.getBooleanCellValue());
			// else if (HSSFDateUtil.isCellDateFormatted(cell))
			// cellData = dataFormatter.formatCellValue(cell);
			else
				cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author ddoshi
	 * @param RowNum
	 * @param ColNum
	 * @return date type excel cell data
	 */
	public static String getDateCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		String CellData = " ";
		try {
			setExcelFile(fileName, sheetName);
			DataFormatter dataFormatter = new DataFormatter();
			cell = sheet.getRow(RowNum).getCell(ColNum);
			if (HSSFDateUtil.isCellDateFormatted(cell))
				CellData = dataFormatter.formatCellValue(cell);
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author ddoshi
	 * @param RowNum
	 * @param ColNum
	 * @return numeric type excel cell data
	 */
	public static String getNumCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		try {
			setExcelFile(fileName, sheetName);
			cell = sheet.getRow(RowNum).getCell(ColNum);
			int CellData = (int) cell.getNumericCellValue();
			String cellValue = String.valueOf(CellData);
			return cellValue;
		} catch (Exception e) {
			return " ";
		}
	}

	/**
	 * @author ddoshi
	 * @param fileName
	 * @param sheetName
	 * @return no of rows in excel
	 */
	public static int getRowCount(String fileName, String sheetName) {
		try {
			setExcelFile(fileName, sheetName);
			int RowCount = sheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @author ddoshi
	 * @param fileName
	 * @param sheetName
	 * @return no of columns in excel
	 */
	public static int getColCount(String fileName, String sheetName) {
		try {
			setExcelFile(fileName, sheetName);
			int ColCount = sheet.getRow(0).getPhysicalNumberOfCells();
			return ColCount;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param fileName
	 * @param sheetName
	 * @param RowNum
	 * @param ColNum
	 * @param Results   set @param Results value into excel cell
	 */
	public static void setCellData(String fileName, String sheetName, int RowNum, int ColNum, String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
			System.out.println(
					"==============================================================================================");
			System.out.println("Row : " + RowNum + "| Column : " + ColNum + " | Updated Content : " + Results);
			System.out.println(
					"==============================================================================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCellDataWithFormate(String fileName, String sheetName, int RowNum, int ColNum,
			String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			HSSFCellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
			if (Results.length() > 50) {
				sheet.setColumnWidth(ColNum, 10000);
				style.setWrapText(true);
			}
			// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path
	 * @param RowNum
	 * @param ColNum    set formula into excel cell
	 * @param sheetName
	 * @param formula
	 */
	@SuppressWarnings("deprecation")
	public static void setCellFormula(String fileName, String sheetName, int RowNum, int ColNum, String formula) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			cell.setCellFormula(formula);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRowNumber(String fileName, String sheetName, int colNum, String content) {
		int i = 0;
		try {
			int rows = getRowCount(fileName, sheetName);
			for (i = 0; i < rows; i++)
				if (getCellData(fileName, sheetName, i, colNum).equalsIgnoreCase(content))
					return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getColNumber(String fileName, String sheetName, int rowNum, String content) {
		int i = 0;
		try {
			int rows = getColCount(fileName, sheetName);
			for (i = 0; i < rows; i++)
				if (getCellData(fileName, sheetName, rowNum, i).equalsIgnoreCase(content))
					return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int countTextInColumn(String fileName, String sheetName, int colNum, String content) {
		int i = 0, count = 0;
		try {
			int rows = getRowCount(fileName, sheetName);
			for (i = 0; i < rows; i++)
				if (getCellData(fileName, sheetName, colNum, i).equalsIgnoreCase(content))
					count++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static List<String> getSheetList(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		try {
			FileInputStream ExcelFile = new FileInputStream(file.getAbsolutePath());
			workbook = new HSSFWorkbook(ExcelFile);
			int noOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < noOfSheets; i++)
				list.add(workbook.getSheetName(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int findRow(Map<Integer, Map<String, String>> abc, String order) {
		int result = 0;
		for (Entry<Integer, Map<String, String>> m1 : abc.entrySet()) {
			Map<String, String> colMap = m1.getValue();
			for (Entry<String, String> m2 : colMap.entrySet()) {
				if (m2.getValue().equalsIgnoreCase(order)) {
					result = m1.getKey();
				}
			}
		}
		return result;
	}

	public static Map<String, Map<Integer, Map<String, String>>> readCompleteExcelFile(String fileName) {
		Map<String, Map<Integer, Map<String, String>>> completeMap = new HashedMap<String, Map<Integer, Map<String, String>>>();
		Map<Integer, Map<String, String>> sheetMap;
		Map<String, String> rowMap;
		File file = new File(fileName);
		try {
			FileInputStream ExcelFile = new FileInputStream(file.getAbsolutePath());
			workbook = new HSSFWorkbook(ExcelFile);
			int noOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < noOfSheets; i++) {
				sheet = workbook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();
				int columns = sheet.getRow(0).getPhysicalNumberOfCells();
				sheetMap = new HashedMap<Integer, Map<String, String>>();
				for (int j = 1; j < rows; j++) {
					rowMap = new HashedMap<String, String>();
					for (int k = 0; k < columns; k++) {
						rowMap.put(getCellData(fileName, sheet.getSheetName(), 0, k),
								getCellData(fileName, sheet.getSheetName(), j, k));
					}
					sheetMap.put(j, rowMap);
				}
				completeMap.put(sheet.getSheetName(), sheetMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return completeMap;
	}

	public static void setImage(String fileName, String sheetName, int RowNum, int ColNum, String imagePath)
			throws IOException {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			InputStream inputStream = new FileInputStream(imagePath);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			inputStream.close();
			CreationHelper helper = workbook.getCreationHelper();
			@SuppressWarnings("rawtypes")
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(ColNum); // Column B
			anchor.setRow1(RowNum); // Row 3
			anchor.setCol2(ColNum + 1); // Column C
			anchor.setRow2(RowNum + 1); // Row 4
			@SuppressWarnings("unused")
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
