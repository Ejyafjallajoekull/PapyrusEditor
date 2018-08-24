package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import housekeeping.ConfigurationHandler;
import housekeeping.LoggingHandler;
import logparsing.LogParser;

public class LogViewerWindow extends JDialog implements ItemListener{

	// serialization
	private static final long serialVersionUID = 1L;
	
	// constants
	private static final String TITLE = "Loganzeige"; // title of the window
	
	// fields
	JComboBox<File> logSelector = new JComboBox<File>(LoggingHandler.getLogFiles());
	JTextPane logText = new JTextPane();
	
	
	// constructor
	public LogViewerWindow() {
		this.setTitle(TITLE);
		this.logText.setEditable(false);
		this.logSelector.setSelectedIndex(-1);
		JScrollPane logView = new JScrollPane(this.logText);
		this.setLayout(new BorderLayout());
		
		// selector panel
		JPanel selectorPanel = new JPanel();
		selectorPanel.setLayout(new GridBagLayout());
		GridBagConstraints selectorConstraints = new GridBagConstraints();
		selectorConstraints.insets = new Insets(10, 10, 5, 10);
		selectorPanel.add(this.logSelector, selectorConstraints);
		this.logSelector.addItemListener(this);
		
		// viewer panel
		JPanel viewerPanel = new JPanel();
		viewerPanel.setLayout(new GridBagLayout());
		GridBagConstraints viewerConstraints = new GridBagConstraints();
		viewerConstraints.insets = new Insets(5, 10, 10, 10);
		viewerConstraints.fill = GridBagConstraints.BOTH;
		viewerConstraints.weightx = 1.0;
		viewerConstraints.weighty = 1.0;
		viewerPanel.add(logView, viewerConstraints);
		
		this.add(selectorPanel, BorderLayout.NORTH);
		this.add(viewerPanel, BorderLayout.CENTER);
//		this.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH); // space
//		this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST); // space
//		this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST); // space
		this.setSize(ConfigurationHandler.getLogWindowWidth(), ConfigurationHandler.getLogWindowHeigth());
		this.setLocationRelativeTo(null);
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == this.logSelector && arg0.getStateChange() == ItemEvent.SELECTED) {
			File file = (File) logSelector.getSelectedItem(); // get selected file
	//		this.logText.setText(LogParser.fuseStrings(LogParser.parseLog(file)));
			StyledDocument doc = this.logText.getStyledDocument();
			this.addStyles(doc);
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e1) {
				LoggingHandler.getLog().log(Level.WARNING, "The log window could not be cleared after selection of file " + file, e1);
				e1.printStackTrace();
			}
			String[][] logContent = LogParser.parseLog(file);
			for (int i = 0; i < logContent.length; i++) {
				try {
					doc.insertString(doc.getLength(), logContent[i][0], doc.getStyle(logContent[i][1]));
				} catch (BadLocationException e) {
					LoggingHandler.getLog().log(Level.WARNING, "String \"" + logContent[i][0] + "\" of file " + file + " could not be inserted into log window", e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addStyles(StyledDocument doc) {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style reg = doc.addStyle("regular", def);
		Style exc = doc.addStyle(LogParser.EXCEPTION, reg);
		StyleConstants.setForeground(exc, ConfigurationHandler.getLogExceptionColour());
		Style sev = doc.addStyle(Level.SEVERE.toString(), reg);
		StyleConstants.setForeground(sev, ConfigurationHandler.getLogSevereColour());
		Style war = doc.addStyle(Level.WARNING.toString(), reg);
		StyleConstants.setForeground(war, ConfigurationHandler.getLogWarningColour());
		Style log = doc.addStyle(LogParser.LOG, reg);
		StyleConstants.setBold(log, true);
	}

}
