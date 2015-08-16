package com.wolfden.java.util;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.wolfden.java.AppTest;

import junit.framework.TestCase;

public class HashUtilsTest extends TestCase {
	File testFile;
	File testFile2;
	public void setUp() {
		testFile = new File(AppTest.TEST_DIRECTORY + "/TestFile1.txt");
		testFile2 = new File(AppTest.TEST_DIRECTORY + "/TestFile2.txt");
	}
	
	public void testHashFileDoesNotReturnNull() throws NoSuchAlgorithmException, IOException {
		String hash = HashUtils.generateMD5Checksum(testFile);
		assertNotNull(hash);
	}
	
	public void testHashFileDoesNotReturnDuplicateHashes() throws NoSuchAlgorithmException, IOException {
		String hash = HashUtils.generateMD5Checksum(testFile);
		String hash2 = HashUtils.generateMD5Checksum(testFile2);
		
		assertNotSame(hash, hash2);
	}
}
