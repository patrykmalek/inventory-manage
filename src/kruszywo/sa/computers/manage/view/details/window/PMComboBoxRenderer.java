package kruszywo.sa.computers.manage.view.details.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
 
public class PMComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 5202957157058797647L;

	public PMComboBoxRenderer() {
        setOpaque(true);
        setFont(new Font("Tahoma", Font.PLAIN | Font.ITALIC, 13));
       
    }
     
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }
 
}