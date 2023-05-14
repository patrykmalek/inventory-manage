package inventory.manage.view.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;


public class PMJTextField extends JTextField{
	

	private static final long serialVersionUID = -7525085440768397157L;
	
	private ImageIcon clearFieldButtonIcon;
	private ImageIcon copyFieldButtonIcon;
	private JPopupMenu popupMenu;
	private boolean warningAfterCheck;
	private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard(); 
	
	public PMJTextField (String text) {
		super(text);
		this.createDefaultEventListeners();
	}

	public PMJTextField (boolean custom, int fontSize, boolean editable) {
		super();
		if(custom) {
			this.setEditable(editable);
			this.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			this.setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			this.setBackground(SystemColor.white);
			this.createCustomEventListeners();
			this.add(createPopupMenuForCustomTextField());
		}
	}
	
	private Border getCustomFieldBorder(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private Border getCustomFieldBorderWhenEmpty() {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 51, 0));
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private void createCustomEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!hasFocus() && !isWarningAfterCheck()) {
					setBackground(SystemColor.inactiveCaptionBorder);
					setBorder(getCustomFieldBorder(new Color(0, 60, 110)));
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!hasFocus() && !isWarningAfterCheck()) {
					setBackground(SystemColor.white);
					setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
				}
			}
			
		});
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				deleteWarning();
				setBackground(SystemColor.inactiveCaption);
				setBorder(getCustomFieldBorder(new Color(0, 10, 110)));
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(SystemColor.white);
				setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			}

		});
		
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					selectAll();
				}
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
	
	private ImageIcon createClearFieldButtonIcon() {
		return new ImageIcon(getClass().getResource("/times-circle-regular.png"));
	}
	
	private ImageIcon createCopyFieldButtonIcon() {
		return new ImageIcon(getClass().getResource("/copy-regular.png"));
	}
	
	private JPopupMenu createPopupMenuForCustomTextField() {
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem clearFieldButton = new JMenuItem("Wyczyść");
		setClearFieldButtonIcon(createClearFieldButtonIcon());
		clearFieldButton.setIcon(getClearFieldButtonIcon());
		clearFieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setText("");
			}
		});
		if(isEditable()) popupMenu.add(clearFieldButton);
		
		
		JMenuItem copyFieldButton = new JMenuItem("Kopiuj");
		setCopyFieldButtonIcon(createCopyFieldButtonIcon());
		copyFieldButton.setIcon(getCopyFieldButtonIcon());
		copyFieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copyToClipboard();
			}
		});
		popupMenu.add(copyFieldButton);
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
		return popupMenu;
	}
	
	public boolean isEmpty() {
		if(getText().isEmpty()) {
			setWarning();
			return true;
		} else {
			deleteWarning();
			return false;
		}
	}
	
	public boolean isInteger() {
		try {
			if(getText().isEmpty()) return true;
			Integer.parseInt(getText());
			deleteWarning();
			return true;
		} catch (NumberFormatException e) {
			setWarning();
			return false;
		}
	}

	private void setWarning() {
		setBorder(getCustomFieldBorderWhenEmpty());
		setBackground(new Color(255, 214, 214));
		setWarningAfterCheck(true);
	}
	
	private void deleteWarning() {
		setBackground(SystemColor.white);
		setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
		setWarningAfterCheck(false);
	}
	
	
	private void copyToClipboard() {
		String text = getText();
		StringSelection sel  = new StringSelection(text.toString()); 
        CLIPBOARD.setContents(sel, sel); 
	}

	private boolean isWarningAfterCheck() {
		return warningAfterCheck;
	}

	private void setWarningAfterCheck(boolean warningAfterCheck) {
		this.warningAfterCheck = warningAfterCheck;
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public ImageIcon getClearFieldButtonIcon() {
		return clearFieldButtonIcon;
	}

	public void setClearFieldButtonIcon(ImageIcon clearFieldButtonIcon) {
		this.clearFieldButtonIcon = clearFieldButtonIcon;
	}
	
	public void setCopyFieldButtonIcon(ImageIcon copyFieldButtonIcon) {
		this.copyFieldButtonIcon = copyFieldButtonIcon;
	}
	
	public int getInt() {
		return (getText().isEmpty()) ? 0 : Integer.parseInt(getText());
	}

	public ImageIcon getCopyFieldButtonIcon() {
		return copyFieldButtonIcon;
	}

}
