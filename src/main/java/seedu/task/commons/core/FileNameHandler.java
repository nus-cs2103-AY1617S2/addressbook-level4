package seedu.task.commons.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//@@author A0163845X
public class FileNameHandler {
	private static String filePath = "fileName.txt";
	public static final String DEFAULT_FILEPATH = "data/TaskManagerData.xml";

	public static String getFileName() {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] data = new byte[256];
			bis.read(data);
			String retString = new String(data).trim();
			if (isValid(retString)) {
				System.out.println("isValid");
				return retString;
			} else {
				return DEFAULT_FILEPATH;
			}
		} catch (IOException ioe) {
			System.out.println("IOException");
			return DEFAULT_FILEPATH;
		}

	}

	public static void setFileName(String fileName) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath, false);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(fileName.getBytes());
			bos.close();
		} catch (IOException ioe) {
			System.out.println("IOException");
		}
	}

	public static boolean isValid(String str) {
		if (str.length() <= 4) {
			return false;
		}
		if (str.substring(str.length() - 4, str.length()).equals(".xml")) {
			return true;
		}
		return false;
	}
}
