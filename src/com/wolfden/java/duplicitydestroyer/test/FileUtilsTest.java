package com.wolfden.java.duplicitydestroyer.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.wolfden.java.duplicitydestroyer.utils.FileUtils;

/**
 * @author Nish
 * 
 * Unit tests for the {@link FileUtils} class
 */
public class FileUtilsTest {

	private File testDirectory;

	@Before
	public void setUp() {
		testDirectory = new File("TestDirectory");
	}

	@Test
	public void getFilesFromDirDoesNotReturnNull() {
		assertNotNull(FileUtils.getFilesFromDir(testDirectory));
	}

	@Test
	public void getFilesFromDirReturnsCorrectNumberOfFiles() {
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
		File[] files4 = FileUtils.getFilesFromDir(testDirectory, ".test",
				".txt");
		assertEquals(correctNumberOfFilesInTestDirectory, files4.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFilesFromDirThrowsException() {
		FileUtils.getFilesFromDir(testDirectory, "");
	}

	@Test
	public void isValidFileExtensionAcceptsCorrectFileExtensions() {
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
