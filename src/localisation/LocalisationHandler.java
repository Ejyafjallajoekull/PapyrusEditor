package localisation;

import java.lang.reflect.Field;
import java.util.logging.Level;

import housekeeping.LoggingHandler;

public class LocalisationHandler {
	// handles all String localisation and contains all String variables 

	
	// MainMenu
	public static String MAIN_TITLE = "Papyrus Editor"; // titel of the main window
	
	
	public static void setLanguage() {
		for (Field field : LocalisationHandler.class.getDeclaredFields()) {
			System.out.println(field.getName());
			try {
				field.set(null, "dada");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				LoggingHandler.getLog().log(Level.SEVERE, "testlog", e);
				e.printStackTrace();
			}
			try {
				System.out.println(field.get(field));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
