package com.wolfden.java.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wolfden.java.TaggedFile;

public class FileUtils {

	private static Set<String> hashSet = new HashSet<String>();

	public static final String[] SUPPORTED_FORMATS = { "All Files", "Music",
			"Images", "Videos", "Text" };
	// TODO - Add more stuff
	public static final String[] ALL_FORMATS = { "All formats" };
	public static final String[] SUPPORTED_MUSIC_EXTENSIONS = { ".mp3", ".aac",
			".flac" };
	public static final String[] SUPPORTED_IMAGES_EXTENSIONS = { ".jpeg",
			".jpg", ".png", ".gif" };
	public static final String[] SUPPORTED_VIDEO_EXTENSIONS = { ".mp4", ".flv",
			".mkv" };
	public static final String[] SUPPORTED_TEXT_EXTENSIONS = { ".txt", ".java",
			".c" };

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

	public static ArrayList<TaggedFile> createTaggedFileList(File[] files) {
		ArrayList<TaggedFile> taggedFiles = new ArrayList<TaggedFile>();

		for (File file : files) {
			taggedFiles.add(new TaggedFile(file));
		}

		return taggedFiles;
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

	public static void tagDuplicates(ArrayList<TaggedFile> taggedFiles) {
		for (TaggedFile taggedFile : taggedFiles) {
			if (isDuplicateFile(taggedFile)) {
				taggedFile.setDuplicate(true);
			}
		}
	}

	private static boolean isDuplicateFile(TaggedFile taggedFile) {
		String hash = HashUtils.generateMD5Checksum(taggedFile.getFile());

		if (hashSet.contains(hash)) {
			return true;
		} else {
			hashSet.add(hash);
			return false;
		}
	}
}
