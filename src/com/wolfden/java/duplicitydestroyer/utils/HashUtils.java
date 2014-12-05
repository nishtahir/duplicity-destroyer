package com.wolfden.java.duplicitydestroyer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

	private static final String MD5 = "MD5";
	private static MessageDigest digest;

	public static String generateMD5Checksum(File file) {
		byte[] byteBuffer = new byte[1024];
		int bytesRead = -1;

		try (FileInputStream inputStream = new FileInputStream(file)) {
			digest = MessageDigest.getInstance(MD5);
			
			while ((bytesRead = inputStream.read(byteBuffer)) != -1)
				digest.update(byteBuffer, 0, bytesRead);

		} catch (IOException | NoSuchAlgorithmException e) {
			// TODO - alert User of problem
			e.printStackTrace();
		}

		return convertByteArrayToHexString(digest.digest());
	}

	private static String convertByteArrayToHexString(byte[] byteArray) {
		StringBuffer mStringBuffer = new StringBuffer();

		for (byte element : byteArray) {
			mStringBuffer.append(Integer.toString((element & 0xff) + 0x100, 16)
					.substring(1));
		}
		return mStringBuffer.toString();
	}
}
