package com.wolfden.java.util;

import java.io.File;

import com.wolfden.java.AppTest;

import junit.framework.TestCase;

public class FileUtilsTest extends TestCase {
	private File testDirectory;

	public void setUp() {
		testDirectory = new File(AppTest.TEST_DIRECTORY);
	}

	public void testGetFilesFromDirDoesNotReturnNull() {
		assertNotNull(FileUtils.getFilesFromDir(testDirectory));
	}

	public void testGetFilesFromDirReturnsCorrectNumberOfFiles() {
		int correctNumberOfFilesInTestDirectory = 3;
		int numberOfFilesWithTestExtension = 1;
		int numberOfFilesWithTextExtension = 2;

		// Without file extension filter
		File[] files = FileUtils.getFilesFromDir(testDirectory);
		assertEquals(correctNumberOfFilesInTestDirectory, files.length);

		// With file extension filter
		File[] files2 = FileUtils.getFilesFromDir(testDirectory, ".test");
		assertEquals(numberOfFilesWithTestExtension, files2.length);

		// With file extension filter
		File[] files3 = FileUtils.getFilesFromDir(testDirectory, ".txt");
		assertEquals(numberOfFilesWithTextExtension, files3.length);

		// With variable length file extension filters
		File[] files4 = FileUtils.getFilesFromDir(testDirectory, ".test", ".txt");
		assertEquals(correctNumberOfFilesInTestDirectory, files4.length);
	}

	public void testGetFilesFromDirThrowsException() {
		try{
			FileUtils.getFilesFromDir(testDirectory, "");
			fail("IllegalArgumentException was not thrown");
		} catch(IllegalArgumentException iae){
			
		}
	}

	public void testIsValidFileExtensionAcceptsCorrectFileExtensions() {
		boolean result = FileUtils.isValidFileExtension(".mp3");
		assertEquals(true, result);

		result = FileUtils.isValidFileExtension(".txt");
		assertEquals(true, result);

		result = FileUtils.isValidFileExtension("txt");
		assertEquals(false, result);

		result = FileUtils.isValidFileExtension("");
		assertEquals(false, result);
	}
}
