package housekeeping;

import java.io.File;

public class UtilityHandler {
// contains all kind of utility functions
	
	// returns file extension without "." in lower case
	public static String getExtension(File file) {
		if (file != null && file.isFile()) { // must be not null and mustn't be a directory
			String name = file.getName();
			int index = name.lastIndexOf(".");
			if (index >= 0) {
				return name.substring(index + 1).toLowerCase(); // returns extension without "."
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	// returns the absolute file path without file extension
	public static String getAbsolutePathWithoutExtension(File file) {
		if (file != null && file.isFile()) { // must be not null and mustn't be a directory
			String name = file.getName();
			int index = name.lastIndexOf(".");
			if (index >= 0) {
				return name.substring(0, index); // returns extension without "."
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

}
