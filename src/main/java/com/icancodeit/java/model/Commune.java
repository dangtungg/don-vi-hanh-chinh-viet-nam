/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.model;

import lombok.*;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-20
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Commune {
	private String id;
	private String name;
	private String slug;
	private String fullName;
	private String level;
	private String parentId;
}
