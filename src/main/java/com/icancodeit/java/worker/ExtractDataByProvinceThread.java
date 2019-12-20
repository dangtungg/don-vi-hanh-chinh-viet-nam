/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.worker;

import com.icancodeit.java.model.Commune;
import com.icancodeit.java.model.District;
import com.icancodeit.java.model.RowMapper;
import com.icancodeit.java.processing.ExtractDataUnit;
import com.icancodeit.java.processing.WriteDataUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-20
 */
public class ExtractDataByProvinceThread implements Runnable {
	
	private String provinceName;
	private String provinceId;
	private List<RowMapper> mappers;
	private String currentDistrictId;
	
	private Map<String, District> districtMap = new LinkedHashMap<>();
	private Map<String, Commune> communeMap = new LinkedHashMap<>();
	private Map<String, Map<String, Commune>> communeByDistrictMap = new LinkedHashMap<>();
	
	public ExtractDataByProvinceThread(String provinceName, String provinceId, List<RowMapper> mappers) {
		this.provinceName = provinceName;
		this.provinceId = provinceId;
		this.mappers = mappers;
		this.currentDistrictId = StringUtils.EMPTY;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " processing data of " + provinceName + ", mã = " + provinceId);
		
		for (int i = 0, size = mappers.size(); i < size; i++) {
			extractRowMapper(mappers.get(i), i + 1, size);
		}
		
		WriteDataUnit.writeDistrictToFile(districtMap, provinceId, provinceName);
		WriteDataUnit.writeCommuneByDistrictToFile(communeByDistrictMap);
		
		System.out.println(Thread.currentThread().getName() + " finished.");
	}
	
	private void extractRowMapper(RowMapper row, int currentRow, int totalRow) {
		String districtId = row.getDistrictId();
		if (!districtMap.containsKey(districtId)) {
			District district = new District();
			district.setId(districtId);
			district.setParentId(row.getProvinceId());
			ExtractDataUnit.extractDistrict(row.getDistrictName(), district);
			districtMap.put(districtId, district);
		}
		
		if (!currentDistrictId.equals(districtId)) {
			if (currentDistrictId.equals(StringUtils.EMPTY)) {
				currentDistrictId = districtId;
			} else {
				communeByDistrictMap.put(currentDistrictId, communeMap);
				currentDistrictId = districtId;
				communeMap = new LinkedHashMap<>();
			}
		}
		
		if (!communeMap.containsKey(row.getCommuneId())) {
			Commune commune = new Commune();
			commune.setId(row.getCommuneId());
			commune.setParentId(districtId);
			ExtractDataUnit.extractCommune(row.getCommuneName(), commune);
			communeMap.put(row.getCommuneId(), commune);
		}
		
		if (currentRow == totalRow) {
			communeByDistrictMap.put(currentDistrictId, communeMap);
		}
	}
	
}
