package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.RowFilter;


public class PMJTextField extends JTextField{
	

	private static final long serialVersionUID = -7525085440768397157L;

	public PMJTextField (String text) {
		super(text);
		
	}
	
	public void setSearchField(PMJTable table) {
		
		PMJTextField searchField = this;
		searchField.setText("Wyszukaj");
		searchField.setForeground(Color.GRAY);
		
		this.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (searchField.getText().equals("Wyszukaj")) {
		        	searchField.setText("");
		        	searchField.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (searchField.getText().isEmpty()) {
		        	searchField.setForeground(Color.GRAY);
		        	searchField.setText("Wyszukaj");
		        }
		    }
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (searchField.getText().length() == 0) {
					table.getTableSorter().setRowFilter(null);
				} else {
					table.getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F && searchField.hasFocus()) {
					searchField.setText("");
					table.requestFocus();
					table.getTableSorter().setRowFilter(null);
				}
			}
			
		});
	}

}
