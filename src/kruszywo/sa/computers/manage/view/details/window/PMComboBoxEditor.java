package kruszywo.sa.computers.manage.view.details.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
 
public class PMComboBoxEditor extends BasicComboBoxEditor {
    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    private Object selectedItem;
    
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
     
    public PMComboBoxEditor() {
         
        label.setOpaque(false);
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
         
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(label);
        
        panel.setBackground(getCustomTextFieldColor());
        panel.setBorder(getCustomBorderForTextField(getCustomBorderColor()));
	}
	
	private Border getCustomBorderForTextField(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private Border getCustomBorderForButton(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
     
    public Component getEditorComponent() {
        return this.panel;
    }
    
    @Override
    protected JTextField createEditorComponent() {
        JTextField editor = new JTextField();
        editor.setBorder(null);
        return editor;
    }
     
    public Object getItem() {
        return this.selectedItem.toString();
    }
     
    public void setItem(Object item) {
        this.selectedItem = item;
        if(item != null) {
        	label.setText(item.toString());
        }
        
    }

	public Color getCustomTextFieldColor() {
		return customTextFieldColor;
	}

	public Color getCustomTextFieldColorActive() {
		return customTextFieldColorActive;
	}

	public Color getCustomTextFieldColorHover() {
		return customTextFieldColorHover;
	}

	public Color getCustomTextFieldColorEmpty() {
		return customTextFieldColorEmpty;
	}

	public Color getCustomButtonColor() {
		return customButtonColor;
	}

	public Color getCustomButtonColorActive() {
		return customButtonColorActive;
	}

	public Color getCustomButtonColorHover() {
		return customButtonColorHover;
	}

	public Color getCustomButtonColorEmpty() {
		return customButtonColorEmpty;
	}

	public Color getCustomBorderColor() {
		return customBorderColor;
	}

	public Color getCustomBorderColorActive() {
		return customBorderColorActive;
	}

	public Color getCustomBorderColorHover() {
		return customBorderColorHover;
	}

	public Color getCustomBorderColorEmpty() {
		return customBorderColorEmpty;
	}
     
}