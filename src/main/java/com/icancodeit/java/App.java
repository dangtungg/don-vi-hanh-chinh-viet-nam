package com.icancodeit.java;

import com.icancodeit.java.processing.ExcelProcess;

public class App {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		try {
			// String excelFilePath = "...path_to...\\java-microsoft-excel-to-json\\data\\excel\\Hà Nội quận huyện, phường xã _01__21_12_2019.xls";
			String excelFilePath = "..path_to...\\java-microsoft-excel-to-json\\data\\excel\\Danh sách cấp tỉnh kèm theo quận huyện, phường xã ___21_12_2019.xls";
			new ExcelProcess().readExcelFile(excelFilePath);
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			
		} finally {
			long end = System.currentTimeMillis();
			calculateTimeDifference(end - start);
		}
	}
	
	private static void calculateTimeDifference(long diff) {
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		System.out.print("Total time: " + diffDays + " days, ");
		System.out.print(diffHours + " hours, ");
		System.out.print(diffMinutes + " minutes, ");
		System.out.print(diffSeconds + " seconds.\n");
	}
	
}
