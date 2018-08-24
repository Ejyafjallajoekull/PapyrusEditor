package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import housekeeping.LoggingHandler;


public class MainMenu extends JMenuBar implements ActionListener{
	// menu bar and all its items
	
	// serialization
	private static final long serialVersionUID = 1L;

	// constants
	private final String DATA = "Datei";
	private final String OPEN_FILE = "Öffnen";
	private final String SAVE_FILE = "Speichern";
	private final String SAVE_ALL = "Alle Speichern";
	private final String SAVE_AS = "Speichern unter...";
	private final String CLOSE_FILE = "Schließen";
	private final String CLOSE_ALL = "Alle Schließen";
	private final String TOOLS = "Werkzeuge";
	private final String LOG_DISPLAY = "Loganzeige";
	private final String VARIOUS = "Sonstiges";
	private final String MUSIC = "Musik";
	private final String SETTINGS = "Einstellungen";
	private final String INFO = "Information";
		
	// variables
	private JMenuItem openDataItem = new JMenuItem(this.OPEN_FILE);
	private JMenuItem saveDataItem = new JMenuItem(this.SAVE_FILE);
	private JMenuItem saveAllDataItem = new JMenuItem(this.SAVE_ALL);
	private JMenuItem saveAsDataItem = new JMenuItem(this.SAVE_AS);
	private JMenuItem closeDataItem = new JMenuItem(this.CLOSE_FILE);
	private JMenuItem closeAllDataItem = new JMenuItem(this.CLOSE_ALL);
	private JMenuItem settingsDataItem = new JMenuItem(this.SETTINGS);
	private JMenuItem logDisplayToolsItem = new JMenuItem(this.LOG_DISPLAY);
	private JMenuItem infoVariousItem = new JMenuItem(this.INFO);
	private JCheckBoxMenuItem musicVariousItem = new JCheckBoxMenuItem(this.MUSIC);
	private Clip clip; // audio clip
	private InfoWindow infoWindow = new InfoWindow(); // the info window
	private LogViewerWindow logDisplayWindow = new LogViewerWindow(); // the log viewer window
	private SettingsWindow settingsWindow = new SettingsWindow(); // the settings window
		

		
	// constructor
	public MainMenu() {
		// menus
		JMenu dataMenu = new JMenu(this.DATA);
		JMenu toolsMenu = new JMenu(this.TOOLS);
		JMenu variousMenu = new JMenu(this.VARIOUS);
		dataMenu.add(openDataItem);
		dataMenu.addSeparator();
		dataMenu.add(saveDataItem);
		dataMenu.addSeparator();
		dataMenu.add(saveAllDataItem);
		dataMenu.addSeparator();
		dataMenu.add(saveAsDataItem);
		dataMenu.addSeparator();
		dataMenu.add(settingsDataItem);
		dataMenu.addSeparator();
		dataMenu.add(closeDataItem);
		dataMenu.addSeparator();
		dataMenu.add(closeAllDataItem);
		toolsMenu.add(logDisplayToolsItem);
		variousMenu.add(musicVariousItem);
		variousMenu.addSeparator();
		variousMenu.add(infoVariousItem);
		this.add(dataMenu);
		this.add(toolsMenu);
		this.add(variousMenu);
		// action listener
		openDataItem.addActionListener(ae -> StartingClass.getEditorWindow().openFile()); // open files
		saveDataItem.addActionListener(this);
		saveAllDataItem.addActionListener(this);
		saveAsDataItem.addActionListener(this);
		settingsDataItem.addActionListener(ae -> openWindow(settingsWindow)); // show settings
		closeDataItem.addActionListener(ae -> StartingClass.getEditorWindow().closeActiveTab()); // close the currently selected tab
		closeAllDataItem.addActionListener(ae -> StartingClass.getEditorWindow().closeAllTabs()); // close all tabs
		logDisplayToolsItem.addActionListener(ae -> openWindow(logDisplayWindow)); // show log viewer
		musicVariousItem.addActionListener(this);
		infoVariousItem.addActionListener(ae -> openWindow(infoWindow)); // show info
	}
		
	// action listener
	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == this.saveDataItem) { // save list

		} else if (ae.getSource() == this.musicVariousItem) { // play music
			if (musicVariousItem.isSelected()) { // checkbox
				try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/data/Keygen Music.wav"))) {
			        clip = AudioSystem.getClip();
			        clip.open(audioInputStream);
			        clip.loop(Clip.LOOP_CONTINUOUSLY); // loop indefinitely
			    } catch(Exception ex) {
			    	LoggingHandler.getLog().log(Level.WARNING, "Error with sound playback", ex);
			        ex.printStackTrace();
			    }
			} else { // checkbox deselected
				clip.stop(); // stop music
			}
		}
	}
	
	// makes a window visible and requests focus
	private static void openWindow(Component window) {
		if (window != null) {
			if (!window.isVisible()) {
				window.setVisible(true);
			}
			window.requestFocus();
		} else {
			LoggingHandler.getLog().warning("Null was passed, but Component was expected");
		}
	}

}
