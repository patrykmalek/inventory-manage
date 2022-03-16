package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;





public class PMJComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 2547238204138712271L;

	private final Color customTextFieldColor = SystemColor.white;
	private final Color customTextFieldColorActive = SystemColor.inactiveCaption;
	private final Color customTextFieldColorHover = SystemColor.inactiveCaptionBorder;
	private final Color customTextFieldColorEmpty = new Color(255, 214, 214);
	
	private final Color customButtonColor = SystemColor.white;
	private final Color customButtonColorActive = SystemColor.inactiveCaption;
	private final Color customButtonColorHover = SystemColor.inactiveCaptionBorder;
	private final Color customButtonColorEmpty = new Color(255, 214, 214);
	
	private final Color customBorderColor = UIManager.getColor("Button.shadow");
	private final Color customBorderColorActive = new Color(0, 10, 110);
	private final Color customBorderColorHover = new Color(0, 60, 110);
	private final Color customBorderColorEmpty = new Color(204, 51, 0);
	
	private boolean mouseOver;
	private boolean comboFocus;
	
	private JTextField textField;
	public PMJComboBox() {
		setEditable(true);

     
        
        setUI(new ComboUI(this));
        setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = -5472112639600939859L;

			@Override
            public Component getListCellRendererComponent(JList<?> jlist, Object o, int i, boolean bln, boolean bln1) {
                Component com = super.getListCellRendererComponent(jlist, o, i, bln, bln1);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                if (bln) {
                    com.setBackground(customTextFieldColorActive);
                    com.setForeground(Color.black);
                }
                return com;
            }
        });
        
		ComboBoxEditor comboBoxEditor = getEditor();
		Component editor = comboBoxEditor.getEditorComponent();
		if( editor instanceof JTextField ) {
			textField = (JTextField) editor;
			textField.setOpaque(false);
		}
        
		textField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!comboFocus) {
					mouseOver = false;
					setComboColorDefault();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!comboFocus) {
					mouseOver = true;
					setComboColorHover();
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		
		textField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				comboFocus = false;
				setComboColorDefault();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				comboFocus = true;
				setComboColorActive();
			}
		});

	}
    
	private void setComboColorDefault() {
		setBackground(customTextFieldColor);
		setBorder(getCustomBorder(customBorderColor));
	}
	
	private void setComboColorHover() {
		setBackground(customTextFieldColorHover);
		setBorder(getCustomBorder(customBorderColorHover));
	}
	
	
	private void setComboColorActive() {
		setBackground(customTextFieldColorActive);
		setBorder(getCustomBorder(customBorderColorActive));
	}
	
	private void setComboColorEmpty() {
		setBackground(customTextFieldColorEmpty);
		setBorder(getCustomBorder(customBorderColorEmpty));
	}
	
	public boolean isEmpty() {
		if(getSelectedItem() == null) {
			setComboColorEmpty();
			return true;
		} else if (getSelectedItem().toString().replaceAll(" ", "").equals("")) {
			setComboColorEmpty();
			return true;
		} else {
			setComboColorDefault();
			return false;
		}
	}

	private Border getCustomBorder(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}

    private class ComboUI extends BasicComboBoxUI {

        @SuppressWarnings({ "rawtypes", "unused" })
		private PMJComboBox combo;
        @SuppressWarnings("rawtypes")
		public ComboUI(PMJComboBox combo) {
            this.combo = combo;
            setComboColorDefault();
        }

        
        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup pop = new BasicComboPopup(comboBox) {

			private static final long serialVersionUID = 2901669845311909690L;};
            pop.setBorder(new LineBorder(customBorderColor, 1));
            return pop;
        }
        
        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }
        
        private class ArrowButton extends JButton {

			private static final long serialVersionUID = -6071890879634476217L;

			public ArrowButton() {
                setContentAreaFilled(false);
                setBackground(customBorderColor);
                setOpaque(false);
            }
			@Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2 + 1;
                int px[] = {x, x + size, x + size / 2};
                int py[] = {y, y, y + size};
                g2.setColor(getBackground());
                g2.fillPolygon(px, py, px.length);
                g2.dispose();
            }
        }
    }
	
}
