package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicButtonUI;

import housekeeping.LoggingHandler;
import housekeeping.UtilityHandler;

public class EditorTab extends JPanel {
// tab for script editing
	
	// serialisation
	private static final long serialVersionUID = 1L;

	// fields
	private File script = null; // the script represented by this tab
	private JTabbedPane pane = null; // the tabbed pane this tab is added to
	private JLabel label = new JLabel(); // the label holding the tab title

	// constructor
	public EditorTab(JTabbedPane pane, File file) {
		super();
		if (file != null && pane != null) {
			this.script = file;
			this.pane = pane;
			initTab();
		} else { // error if null is passed
			LoggingHandler.getLog().severe("Tab was initialised with a null script or null JTabbedPane");
		}
	}
	
	
	private void initTab() {
		if (script != null) {
			if (UtilityHandler.getExtension(script).equals("psc")) {
				try {
					String content = new String(Files.readAllBytes(script.toPath()), StandardCharsets.ISO_8859_1);
					this.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.weightx = 1;
					c.weighty = 1;
					JTextPane textArea = new JTextPane() {
					    // serialisation
						private static final long serialVersionUID = 1L;

						public boolean getScrollableTracksViewportWidth() // overwrite this function to disable line wrapping
					    {
					        return getUI().getPreferredSize(this).width <= getParent().getSize().width;
					    }
					};
					textArea.setText(content);
					textArea.setCaretPosition(0); // scroll to the top
					JScrollPane scrollPane = new JScrollPane(textArea);
					this.add(scrollPane, c);
				} catch (IOException e) {
					LoggingHandler.getLog().log(Level.SEVERE, "Could not read " + script, e);
					e.printStackTrace();
				}
			}
		} else {
			LoggingHandler.getLog().warning("Null cannot be added");
		}
		
	}
	
	// init the title // needs to be called after being added to the JTabbedPane or will throw NullPointerException
	public boolean initTitle() {
		int i = this.pane.indexOfComponent(EditorTab.this);
        if (i > -1) {
        	this.pane.setTabComponentAt(this.pane.indexOfComponent(this), new TabLabel());
        	return true;
        } else {
        	return false;
        }
	}
	
	// updates the title after change of the script file
	private boolean updateTitle() {
		this.label.setText(script.getName());
		int i = this.pane.indexOfComponent(EditorTab.this);
        if (i > -1) {
        	this.pane.setToolTipTextAt(i, script.getAbsolutePath());
        	return true;
        } else {
        	return false;
        }
	}
	
	// close the tab and return true if successful
    public boolean closeTab() {
    	int i = this.pane.indexOfComponent(EditorTab.this);
        if (i > -1) {
            EditorTab.this.pane.remove(i);
            return true;
        } else {
        	return false;
        }
    }

	// getters
	public File getScript() {
		return this.script;
	}
	
	// setters
	public void setScript(File script) {
		this.script = script;
		this.updateTitle();
	}



	private class TabLabel extends JPanel {
		// serialisation
		private static final long serialVersionUID = 1L;
		
		public TabLabel() {
			EditorTab.this.updateTitle(); // first of all update the label
			this.add(EditorTab.this.label); // add the text label
			this.add(new TabButton()); // add the closing button
			this.setOpaque(false); // colour the tab
		}

	};
	
	// copied from java doc
	private class TabButton extends JButton {
        // serialisation
		private static final long serialVersionUID = 1L;

		public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(ae -> EditorTab.this.closeTab());
        }
  
        //we don't want to update UI for this button
        public void updateUI() {
        }
 
        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
 
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
	

}
