package housekeeping;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;

public class ConfigurationHandler {
	// handles all configuration
	
	// constants
	public static final String VERSION = "0.1.0"; // the version number
	private static final String INI_NAME = "PapyrusEditor";
	
	// fields
	private static int mainWindowWidth = 1000; // width of the main window
	private static int mainWindowHeigth = 700; // heigth of the main window
	private static int logWindowWidth = 600; // width of the main window
	private static int logWindowHeigth = 800; // heigth of the main window
	private static int numberLogFiles = 5; // the number of log files to be kept
	private static String logFileName = "Log_"; // name/prefix of logging files
	private static String logFolder = "Logs\\"; // name of the log folder
	private static Color logSevereColour = Color.RED; // display colour for severe logs
	private static Color logWarningColour = Color.ORANGE; // display colour for warning logs
	private static Color logExceptionColour = Color.RED; // display colour for exceptions in log warnings
	private static String[] logFields = {"numberLogFiles", "logFileName", "logFolder"};
	private static String[] logColourFields = {"logSevereColour", "logWarningColour", "logExceptionColour"};
	
	// writes all configuration variables to external .ini file
	public static void writeINI() {
		// try-with resources for autoclose when try-block ends
		try (BufferedWriter iniWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(INI_NAME + ".ini"), Charset.forName("UTF-8").newEncoder()))) {
			// config file creation in working directory (relative)
			// file header
			iniWriter.write(INI_NAME + System.getProperty("line.separator") + System.getProperty("line.separator")); // title
			for (Field field : getFieldGroup(logFields)) {
				try {
					iniWriter.write(field.getName() + "=" + field.get(field) + System.getProperty("line.separator"));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Field field : getFieldGroup(logColourFields)) {
				try {
					iniWriter.write(field.getName() + "=" + ((Color) field.get(field)).getRGB() + System.getProperty("line.separator"));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			LoggingHandler.getLog().log(Level.SEVERE, INI_NAME + ".ini could not be written");
			e.printStackTrace();
		}	
	}
	
	// imports all configuration variables from external .ini file
	public static void readINI() {
		ArrayList<String> configLines = new ArrayList<String>(); // list of all lines in config file as strings without line separators
		File configFile = new File(INI_NAME + ".ini");
		if (configFile.exists()) { // check if there is a config file
			try {
				// UTF-8 standard for addLines
				configLines.addAll(Files.readAllLines(configFile.toPath())); // easier than FileReader(), but substitutes line separators // casted to ArrayList to save a line of code (import list)
				for (String configString : configLines) { // search every line for variable definitions
					// music folder path
					if (configString.contains("MusicFolderPath=")) {
		//				musicPath = configString.replaceFirst("MusicFolderPath=", "");
			//			System.out.println("MusicFolderPath=" + musicPath);
					// 
					}
				}
			} catch (IOException e) {
				LoggingHandler.getLog().log(Level.SEVERE, INI_NAME + ".ini could not be accessed");
				e.printStackTrace();
			}
		} else {
			LoggingHandler.getLog().info(INI_NAME + ".ini does not exist and will now be created");
			writeINI();
		}
	}
	
	private static Field[] getFieldGroup(String[] fields) {
		if (fields != null) {
			ArrayList<Field> fieldList = new ArrayList<Field>();
			for (String item : fields) {
				try {
					fieldList.add(ConfigurationHandler.class.getDeclaredField(item));
				} catch (NoSuchFieldException | SecurityException e) {
					LoggingHandler.getLog().log(Level.WARNING, "Configuration field " + item + " could not be accessed or did not exist", e);
					e.printStackTrace();
				}
			}
			return fieldList.toArray(new Field[fieldList.size()]);
		} else {
			return new Field[0];
		}
	}
	
	// getters
	public static int getMainWindowWidth() {
		return mainWindowWidth;
	}
	public static int getMainWindowHeigth() {
		return mainWindowHeigth;
	}
	public static int getNumberLogFiles() {
		return numberLogFiles;
	}
	public static String getLogFileName() {
		return logFileName;
	}
	public static String getLogFolder() {
		return logFolder;
	}
	public static int getLogWindowHeigth() {
		return logWindowHeigth;
	}
	public static int getLogWindowWidth() {
		return logWindowWidth;
	}
	public static Color getLogSevereColour() {
		return logSevereColour;
	}
	public static Color getLogWarningColour() {
		return logWarningColour;
	}
	public static Color getLogExceptionColour() {
		return logExceptionColour;
	}
	
	// setters
	public static void setMainWindowWidth(int mainWindowWidth) {
		ConfigurationHandler.mainWindowWidth = mainWindowWidth;
	}
	public static void setMainWindowHeigth(int mainWindowHeigth) {
		ConfigurationHandler.mainWindowHeigth = mainWindowHeigth;
	}
	public static void setNumberLogFiles(int numberLogFiles) {
		ConfigurationHandler.numberLogFiles = numberLogFiles;
	}
	public static void setLogFileName(String logFileName) {
		ConfigurationHandler.logFileName = logFileName;
	}
	public static void setLogFolder(String logFolder) {
		ConfigurationHandler.logFolder = logFolder;
	}
	public static void setLogWindowWidth(int logWindowWidth) {
		ConfigurationHandler.logWindowWidth = logWindowWidth;
	}
	public static void setLogWindowHeigth(int logWindowHeigth) {
		ConfigurationHandler.logWindowHeigth = logWindowHeigth;
	}
	public static void setLogSevereColour(Color logSevereColour) {
		ConfigurationHandler.logSevereColour = logSevereColour;
	}
	public static void setLogWarningColour(Color logWarningColour) {
		ConfigurationHandler.logWarningColour = logWarningColour;
	}
	public static void setLogExceptionColour(Color logExceptionColour) {
		ConfigurationHandler.logExceptionColour = logExceptionColour;
	}

}
