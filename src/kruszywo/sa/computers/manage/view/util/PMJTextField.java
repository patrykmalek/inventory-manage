package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;


public class PMJTextField extends JTextField{
	

	private static final long serialVersionUID = -7525085440768397157L;

	
	public PMJTextField (String text) {
		super(text);
		this.createDefaultEventListeners();
	}

	public PMJTextField (boolean custom, int fontSize) {
		super();
		if(custom) {
			this.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			this.setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			this.setBackground(SystemColor.white);
			this.createCustomEventListeners();
		}
	}
	
	private Border getCustomFieldBorder(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private void createCustomEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!hasFocus()) {
					setBackground(SystemColor.inactiveCaptionBorder);
					setBorder(getCustomFieldBorder(new Color(0, 60, 110)));
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!hasFocus()) {
					setBackground(SystemColor.white);
					setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
				}
			}
			
		});
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				setBackground(SystemColor.inactiveCaption);
				setBorder(getCustomFieldBorder(new Color(0, 10, 110)));
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(SystemColor.white);
				setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			}

		});
	}
	
	public void setSearchField(PMJTable table) {
		
		setText("Wyszukaj");
		setForeground(Color.GRAY);
		
		this.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (getText().equals("Wyszukaj")) {
		        	setText("");
		        	setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (getText().isEmpty()) {
		        	setForeground(Color.GRAY);
		        	setText("Wyszukaj");
		        }
		    }
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (getText().length() == 0) {
					table.getTableSorter().setRowFilter(null);
				} else {
					table.getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + getText()));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.isControlDown()) { 
					 if (e.getKeyCode() == KeyEvent.VK_F && hasFocus()) {
							setText("");
							table.requestFocus();
							table.getTableSorter().setRowFilter(null);
					 }
				 }
			}
		});
		
		table.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown()) {
					if (e.getKeyCode() == KeyEvent.VK_F && !hasFocus()) {
						requestFocus();
					}
				}
			}
		});
	}
	
	
	private void createDefaultEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}

		});
	}


}
