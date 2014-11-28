package com.wolfden.java.duplicitydestroyer.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.wolfden.java.duplicitydestroyer.FileUtils;

/**
 * @author Nish
 * 
 *         Unit tests for the {@link FileUtils} class
 */
public class FileUtilsTest {

	private File testDirectory;

	@Before
	public void setUp() {
		testDirectory = new File("TestDirectory");
	}

	@Test
	public void getFilesFromDir_DoesNotReturnNull() {
		assertNotNull(FileUtils.getFilesFromDir(testDirectory));
	}

	@Test
	public void getFilesFromDir_ReturnsCorrectNumberOfFiles() {
		// Without file extension filter
		File[] files = FileUtils.getFilesFromDir(testDirectory);
		assertEquals(3, files.length);

		// With file extension filter
		File[] files2 = FileUtils.getFilesFromDir(testDirectory, ".test");
		assertEquals(1, files2.length);
		
		// With file extension filter
		File[] files3 = FileUtils.getFilesFromDir(testDirectory, ".txt");
		assertEquals(2, files3.length);

		// With variable length file extension filters
		File[] files4 = FileUtils.getFilesFromDir(testDirectory, ".test",
				".txt");
		assertEquals(3, files4.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFilesFromDir_throwsException() {
		FileUtils.getFilesFromDir(testDirectory, "");
	}

	@Test
	public void isValidFileExtension() {
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
