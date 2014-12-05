package com.wolfden.java.duplicitydestroyer.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import com.wolfden.java.duplicitydestroyer.utils.HashUtils;

public class HashUtilsTest {
	File testFile;
	File testFile2;
	@Before
	public void setUp() {
		testFile = new File("TestDirectory/TestFile.txt");
		testFile2 = new File("TestDirectory/TestFile2.txt");
	}
	
	@Test
	public void hashFileDoesNotReturnNull() throws NoSuchAlgorithmException, IOException {
		String hash = HashUtils.generateMD5Checksum(testFile);
		assertNotNull(hash);
	}
	
	@Test
	public void hashFileDoesNotReturnDuplicateHashes() throws NoSuchAlgorithmException, IOException {
		String hash = HashUtils.generateMD5Checksum(testFile);
		String hash2 = HashUtils.generateMD5Checksum(testFile2);
		
		assertNotEquals(hash, hash2);
	}

}
