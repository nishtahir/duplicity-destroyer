package com.wolfden.java.duplicitydestroyer;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

	public static final String[] SUPPORTED_FORMATS = { "All Files", "Music", "Images", "Videos", "Text" };
	//TODO - Add more stuff
	public static final String[] SUPPORTED_EXTENSIONS = { ".mp3", ".aac", ".jpeg", ".png", ".txt" };

	/**
	 * @param directory
	 * @return returns all files in the given directory. Does not return the
	 *         contents of sub folders
	 */
	public static File[] getFilesFromDir(File directory) {
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File path) {
				return path.isFile();
			}
		};
		return directory.listFiles(filter);
	}

	/**
	 * @param directory
	 * @param extension
	 *            file extensions to filter for
	 * @return returns all files in the given directory. Does not return the
	 *         contents of sub folders
	 */
	public static File[] getFilesFromDir(File directory,
			final String... extensions) {
		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {

				for (String extension : extensions) {
					if (isValidFileExtension(extension)) {
						if (fileName.endsWith(extension)) {
							return true;
						}
					} else {
						throw new IllegalArgumentException(
								"File extensions must be in the *.[param] format.");
					}

				}
				return false;
			}
		});

		return files;
	}

	/**
	 * @param extension
	 *            any file extension in the *.[param] format
	 * @return true if valid
	 */
	public static boolean isValidFileExtension(String extension) {
		String re1 = "(\\.)";
		String re2 = "((?:[a-z0-9_]*))";

		Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher m = p.matcher(extension);
		return (m.find()) ? true : false;
	}
}
