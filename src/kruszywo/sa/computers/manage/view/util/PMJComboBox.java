package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

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
	
	public PMJComboBox() {

		setEditable(true);

		
		ComboBoxEditor comboBoxEditor = getEditor();
		Component editor = comboBoxEditor.getEditorComponent();
		if( editor instanceof JTextField ) {
			JTextField textField = (JTextField) editor;
			textField.setBorder(getCustomBorderForTextField(getCustomBorderColor()));
			textField.setBackground(getCustomTextFieldColor());
		}
		
		for (int i = 0; i < getComponentCount(); i++) 
		{
		    if (getComponent(i) instanceof AbstractButton) {
		        ((AbstractButton) getComponent(i)).setBorderPainted(true);
		    	((AbstractButton) getComponent(i)).setBorder(getCustomBorderForButton(getCustomBorderColor()));
		    	((AbstractButton) getComponent(i)).setBackground(getCustomButtonColor());
		    }
		}
		
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
}
