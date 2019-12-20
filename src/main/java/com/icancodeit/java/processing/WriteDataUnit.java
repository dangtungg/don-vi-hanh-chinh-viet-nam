/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.processing;

import com.google.gson.Gson;
import com.icancodeit.java.model.Commune;
import com.icancodeit.java.model.District;
import com.icancodeit.java.model.Province;
import com.icancodeit.java.util.FileUtil;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-21
 */
public class WriteDataUnit {
	
	private static final Gson GSON = new Gson();
	
	public static void writeProvinceToFile(Map<String, Province> provinceMap) {
		try {
			System.out.println("===>>> [WRITE JSON] Write " + provinceMap.size() + " tỉnh/tp");
			FileUtil fileUtil = new FileUtil(FileUtil.JSON_DIR);
			fileUtil.writeFile("tinh_tp.json", GSON.toJson(provinceMap));
		} catch (IOException e) {
			System.err.println("Can't write provinces to file");
		}
	}
	
	public static void writeDistrictToFile(Map<String, District> districtMap, String provinceId, String provinceName) {
		try {
			System.out.println("===>>> [WRITE JSON] Write " + districtMap.size() + " quận/huyện của " + provinceName);
			FileUtil fileUtil = new FileUtil(FileUtil.JSON_DIR_QUAN_HUYEN);
			fileUtil.writeFile(provinceId + ".json", GSON.toJson(districtMap));
		} catch (IOException e) {
			System.err.println("Can't write districts of provinceId [" + provinceId + "] to file");
		}
	}
	
	public static void writeCommuneByDistrictToFile(Map<String, Map<String, Commune>> communeByDistrictMap) {
		communeByDistrictMap.forEach((k, v) -> writeCommuneToFile(v, k));
	}
	
	private static void writeCommuneToFile(Map<String, Commune> communeMap, String districtId) {
		try {
			System.out.println("===>>> [WRITE JSON] Write " + communeMap.size() + " phường/xã của quận/huyện có mã = " + districtId);
			FileUtil fileUtil = new FileUtil(FileUtil.JSON_DIR_XA_PHUONG);
			fileUtil.writeFile(districtId + ".json", GSON.toJson(communeMap));
		} catch (IOException e) {
			System.err.println("Can't write communes of districtId [" + districtId + "] to file");
		}
	}
	
}
