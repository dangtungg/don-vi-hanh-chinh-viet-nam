/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.processing;

import com.icancodeit.java.model.Commune;
import com.icancodeit.java.model.District;
import com.icancodeit.java.model.Province;
import com.icancodeit.java.util.VietnameseConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-21
 */
public class ExtractDataUnit {
	
	public static final String LEVEL_TINH = "Tỉnh";
	public static final String LEVEL_THANH_PHO = "Thành phố";
	
	public static final String LEVEL_QUAN = "Quận";
	public static final String LEVEL_HUYEN = "Huyện";
	public static final String LEVEL_THI_XA = "Thị xã";
	
	public static final String LEVEL_XA = "Xã";
	public static final String LEVEL_PHUONG = "Phường";
	public static final String LEVEL_THI_TRAN = "Thị trấn";
	
	public static void extractProvince(String original, Province province) {
		String level;
		int index;
		
		if (original.startsWith(LEVEL_THANH_PHO)) {
			index = LEVEL_THANH_PHO.length();
			level = "Thành phố Trung ương";
		} else {
			index = original.indexOf(StringUtils.SPACE);
			level = original.substring(0, index);
		}
		
		String name = original.substring(index + 1);
		String slug = VietnameseConverter.toAlias(name).toLowerCase();
		
		province.setName(name);
		province.setSlug(slug);
		province.setFullName(original);
		province.setLevel(level);
	}
	
	public static void extractDistrict(String original, District district) {
		int index;
		if (original.startsWith(LEVEL_THANH_PHO)) {
			index = LEVEL_THANH_PHO.length();
		} else if (original.startsWith(LEVEL_THI_XA)) {
			index = LEVEL_THI_XA.length();
		} else {
			index = original.indexOf(StringUtils.SPACE);
		}
		
		String level = original.substring(0, index);
		String name = original.substring(index + 1);
		String slug = VietnameseConverter.toAlias(name).toLowerCase();
		
		district.setName(name);
		district.setSlug(slug);
		district.setFullName(original);
		district.setLevel(level);
	}
	
	public static void extractCommune(String original, Commune commune) {
		int index;
		if (original.startsWith(LEVEL_THI_TRAN)) {
			index = LEVEL_THI_TRAN.length();
		} else {
			index = original.indexOf(StringUtils.SPACE);
		}
		
		String level = original.substring(0, index);
		String name = original.substring(index + 1);
		String slug = VietnameseConverter.toAlias(name).toLowerCase();
		
		commune.setName(name);
		commune.setSlug(slug);
		commune.setFullName(original);
		commune.setLevel(level);
	}
	
}
