package gui;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import housekeeping.LoggingHandler;
import housekeeping.UtilityHandler;

public class EditorWindow extends JTabbedPane {

	// serialization
	private static final long serialVersionUID = 1L;
	
	
	// fields
	private JFileChooser fileWindow = new JFileChooser(new File(".").getAbsoluteFile());

	public EditorWindow() {
		super();
		this.initialiseEditor();
	}

	public EditorWindow(int arg0) {
		super(arg0);
		this.initialiseEditor();
	}

	// fields

	// constructor
	public EditorWindow(int arg0, int arg1) {
		super(arg0, arg1); // call the super constructor
		this.initialiseEditor();
	}
	
	public void initialiseEditor() {
		// file selector
		fileWindow.setMultiSelectionEnabled(true);
		fileWindow.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileWindow.addChoosableFileFilter(new FileNameExtensionFilter("Papyrus Script Files", "psc", "pex")); // filter for both script formates
		fileWindow.addChoosableFileFilter(new FileNameExtensionFilter(".psc", "psc")); // filter for uncompiled scripts
		fileWindow.addChoosableFileFilter(new FileNameExtensionFilter(".pex", "pex")); // filter for compiled scripts
		fileWindow.removeChoosableFileFilter(fileWindow.getChoosableFileFilters()[0]); // remove standard filter "all files"
	}
	
	public void openFile() {
		if (fileWindow.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = fileWindow.getSelectedFiles();
			for (File selectedFile : selectedFiles) {
				if (selectedFile != null && selectedFile.exists()) {
					if (!this.isOpened(selectedFile)) {
						if (selectedFile.isFile()) {
							this.addTabFromFile(selectedFile);
						}
						LoggingHandler.getLog().info("Opened " + selectedFile);
					} else {
						LoggingHandler.getLog().info(selectedFile + " has already been opened");
					}
				} else {
					LoggingHandler.getLog().warning("Tried to opened " + selectedFile + " , but it does not exist");
				}
			}
		} else {
			LoggingHandler.getLog().info("Opening file aborted");
		}
	}
	
	public void closeActiveTab() {
		int i = this.getSelectedIndex();
		if (i > -1) {
			Component tab = this.getComponentAt(i);
			if (tab != null && tab instanceof EditorTab) {
				LoggingHandler.getLog().info(((EditorTab) tab).getScript().getAbsolutePath() + " has been closed");
				((EditorTab) tab).closeTab();
			}
		} else {
			LoggingHandler.getLog().info("No file is selected");
		}
	}
	
	public void closeAllTabs() {
		this.removeAll();
		LoggingHandler.getLog().info("All files have been closed");
	}
	
	// helper function for tab creation
	private void addTabFromFile(File file) {
		if (file != null) {
			EditorTab tab = new EditorTab(this, file);
			this.addTab(file.getName(), tab); // title irrelevant // gets replaced by updateTitle() of the EditorTab
			tab.initTitle(); // has to be called after adding the tab to the JTabbedPane in order to work correctly 
		} else {
			LoggingHandler.getLog().warning("Null cannot be added");
		}
	}
	
	// checks if a specific file is currently opened by this EditorWindow
	private boolean isOpened(File file) {
		if (file != null) {
			String name = UtilityHandler.getAbsolutePathWithoutExtension(file);
			for (Component tab : this.getComponents()) {
				if (tab instanceof EditorTab && UtilityHandler.getAbsolutePathWithoutExtension(((EditorTab) tab).getScript()).equals(name)) {
					return true;
				}
			}
			return false;
		} else { // null cannot be opened
			return false;
		}
	}

}
