package com.wolfden.java.duplicitydestroyer;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.wolfden.java.duplicitydestroyer.utils.HashUtils;

public class TaggedFile {
	private File file;
	private boolean isDuplicate = false;
	
	public TaggedFile(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	
	public String getHash() throws NoSuchAlgorithmException, IOException{
		return HashUtils.generateMD5Checksum(file);
	}

}
