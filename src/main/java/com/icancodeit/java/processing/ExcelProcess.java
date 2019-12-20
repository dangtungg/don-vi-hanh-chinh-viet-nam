/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.processing;

import com.icancodeit.java.model.Province;
import com.icancodeit.java.model.RowMapper;
import com.icancodeit.java.worker.ExtractDataByProvinceThread;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-20
 */
public class ExcelProcess {
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
	
	private static final String EXCEL_XLSX_EXT = "xlsx";
	private static final String EXCEL_XLS_EXT = "xls";
	
	private String currentProvinceName = StringUtils.EMPTY;
	private String currentProvinceId = StringUtils.EMPTY;
	private Map<String, Province> provinceMap = new LinkedHashMap<>();
	
	public void readExcelFile(String excelFilePath) throws IOException {
		FileInputStream fis = new FileInputStream(new File(excelFilePath));
		Workbook workBook = getWorkbook(fis, excelFilePath);
		Sheet firstSheet = workBook.getSheetAt(0);
		
		int lastRowNum = firstSheet.getLastRowNum();
		
		List<RowMapper> rows = new ArrayList<>();
		for (Row row : firstSheet) {
			if (row.getRowNum() == 0) {  // File Header
				continue;
			}
			
			RowMapper mapper = new RowMapper();
			for (Cell cell : row) {
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
					case 0:
						mapper.setProvinceName((String) getCellValue(cell));
						break;
					case 1:
						mapper.setProvinceId((String) getCellValue(cell));
						break;
					case 2:
						mapper.setDistrictName((String) getCellValue(cell));
						break;
					case 3:
						mapper.setDistrictId((String) getCellValue(cell));
						break;
					case 4:
						mapper.setCommuneName((String) getCellValue(cell));
						break;
					case 5:
						mapper.setCommuneId((String) getCellValue(cell));
						break;
					default:
						break;
				}
			}
			
			// Process data for only Province
			String provinceName = mapper.getProvinceName();
			String provinceId = mapper.getProvinceId();
			if (!provinceMap.containsKey(provinceId)) {
				Province province = new Province();
				province.setId(provinceId);
				province.setParentId(StringUtils.EMPTY);
				ExtractDataUnit.extractProvince(provinceName, province);
				provinceMap.put(provinceId, province);
			}
			
			if (!currentProvinceId.equals(provinceId)) {
				if (currentProvinceId.equals(StringUtils.EMPTY)) {
					currentProvinceName = provinceName;
					currentProvinceId = provinceId;
				} else {
					extractDataByProvince(currentProvinceName, currentProvinceId, rows);
					currentProvinceName = provinceName;
					currentProvinceId = provinceId;
					rows = new ArrayList<>();
				}
			}
			
			rows.add(mapper);
			
			if (row.getRowNum() == lastRowNum) {
				extractDataByProvince(currentProvinceName, currentProvinceId, rows);
				WriteDataUnit.writeProvinceToFile(provinceMap);
			}
		}
		
		fis.close();
		workBook.close();
	}
	
	private Workbook getWorkbook(FileInputStream fis, String excelFilePath) throws IOException {
		if (excelFilePath.endsWith(EXCEL_XLSX_EXT)) {
			return new XSSFWorkbook(fis);
		}
		if (excelFilePath.endsWith(EXCEL_XLS_EXT)) {
			return new HSSFWorkbook(fis);
		}
		throw new IllegalArgumentException("The specified file is not Excel file");
	}
	
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getRichStringCellValue().getString();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue() + "";
				}
				return (int) cell.getNumericCellValue() + "";
			case BOOLEAN:
				return cell.getBooleanCellValue() + "";
			case FORMULA:
				return cell.getCellFormula() + "";
			default:
				return "";
		}
	}
	
	private void extractDataByProvince(String provinceName, String provinceId, List<RowMapper> mappers) {
		System.out.println("===>>> Found " + mappers.size() + " records of " + provinceName + " from Excel file, starting extraction...");
		Runnable worker = new ExtractDataByProvinceThread(provinceName, provinceId, mappers);
		EXECUTOR_SERVICE.execute(worker);
	}
	
}
