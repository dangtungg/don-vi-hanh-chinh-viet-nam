/************************************************************
 *     Copyright (C) 2019 by DTT - All rights reserved.     *
 ************************************************************/
package com.icancodeit.java.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Tung Dang
 * @email dtt.dangthanhtung@gmail.com
 * @category java-microsoft-excel-to-json
 * @date 2019-12-20
 */
public class FileUtil {
	
	public static final String EXCEL_DIR = "data/excel";
	public static final String JSON_DIR = "data/json";
	public static final String JSON_DIR_QUAN_HUYEN = "data/json/quan-huyen";
	public static final String JSON_DIR_XA_PHUONG = "data/json/xa-phuong";
	private final Path rootLocation;
	
	public FileUtil(String dir) throws IOException {
		this.rootLocation = Paths.get(dir);
		init();
	}
	
	private void init() throws IOException {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new IOException("Could not initialize storage", e);
		}
	}
	
	public void writeFile(String fileName, String fileContent) throws IOException {
		FileChannel channel;
		try (RandomAccessFile stream = new RandomAccessFile(this.rootLocation.resolve(fileName).toString(), "rw")) {
			channel = stream.getChannel();
			byte[] strBytes = fileContent.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
			buffer.put(strBytes);
			buffer.flip();
			channel.write(buffer);
		}
		channel.close();
	}
}
