package gui;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import housekeeping.ConfigurationHandler;
import housekeeping.LoggingHandler;

public class StartingClass {
		
	// fields
	private static JFrame mainWindow = null; // the main window
	private static final String MAIN_TITLE = "Papyrus Editor"; // titel of the main window
	private static MainMenu mainMenu = new MainMenu(); // the main menu
	private static EditorWindow editorWindow = new EditorWindow(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT); // the editor window
	
	// main
	public static void main(String[] args) {
		if (!LoggingHandler.startLogWriting()) { // start logging and print error message if it fails
			// print error message
		} else {
			LoggingHandler.getLog().info("Logging initialised.");
		}
		startGUI();
	}
	
	// initiates the GUI
	public static void startGUI() {
		if (mainWindow == null) {
			mainWindow = new JFrame(MAIN_TITLE);
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit process on close
			mainWindow.setSize(ConfigurationHandler.getMainWindowWidth(), ConfigurationHandler.getMainWindowHeigth());
			mainWindow.setLocationRelativeTo(null);
			mainWindow.add(mainMenu); // add the main menu bar
			mainWindow.setJMenuBar(mainMenu); // set to menu bar
			mainWindow.add(editorWindow); // add text editor window
			mainWindow.setVisible(true);
			LoggingHandler.getLog().info("GUI started.");
			ConfigurationHandler.readINI();
		} else {
			LoggingHandler.getLog().warning("Only one instance of PapyrusEditor can be started at the same time.");
		}
	}
	
	// closes the GUI
	public static void closeGUI() {
		if (mainWindow != null) {
			mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING)); // close the main window
			mainWindow = null;
			LoggingHandler.getLog().info("GUI closed.");
		}
	}

	// getters
	public static JFrame getMainWindow() {
		return mainWindow;
	}

	public static MainMenu getMainMenu() {
		return mainMenu;
	}

	public static EditorWindow getEditorWindow() {
		return editorWindow;
	}

}
