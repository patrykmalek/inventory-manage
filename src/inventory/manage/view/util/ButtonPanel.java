package inventory.manage.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{


	private static final long serialVersionUID = 9159475743311541651L;
	private List<JButton> buttons = new ArrayList<JButton>();
	
	private Color buttonHeaderBackgroundColor = SystemColor.white;
	private Color buttonFooterBackgroundColor = new Color(245,245,245);

	private int layoutPosition;
	
	public ButtonPanel(int layoutPosition) {
		this.layoutPosition = layoutPosition;
		this.createVisuals(getLayoutPosition());
	}

	private void createVisuals(int layoutPosition) {
		
		this.setLayout(new FlowLayout(layoutPosition));
		
		if(layoutPosition == FlowLayout.LEADING) {
			this.setBackground(getButtonHeaderBackgroundColor());
		} else {
			this.setBackground(getButtonFooterBackgroundColor());
		}
	}
	
	public void addShowButton(JButton button) {
		if (button.getText().equals("")) button.setText("Wyświetl");
		Icon icon = new ImageIcon(getClass().getResource("/eye-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addInsertButton(JButton button) {
		if (button.getText().equals("")) button.setText("Dodaj");
		Icon icon = new ImageIcon(getClass().getResource("/plus-circle-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addEditButton(JButton button) {
		if (button.getText().equals("")) button.setText("Edytuj");
		Icon icon = new ImageIcon(getClass().getResource("/edit-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addDeleteButton(JButton button) {
		if (button.getText().equals("")) button.setText("Usuń");
		Icon icon = new ImageIcon(getClass().getResource("/trash-alt-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addSaveButton(JButton button) {
		if (button.getText().equals("")) button.setText("Zapisz");
		Icon icon = new ImageIcon(getClass().getResource("/check-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}

	public void addCancelButton(JButton button) {
		if (button.getText().equals("")) button.setText("Anuluj");
		Icon icon = new ImageIcon(getClass().getResource("/times-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addPrintButton(JButton button) {
		Icon icon = new ImageIcon(getClass().getResource("/printer.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addAttachmentButton(JButton button) {
		if (button.getText().equals("")) button.setText("Otwórz załącznik");
		Icon icon = new ImageIcon(getClass().getResource("/paperclip-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		button.setPreferredSize(new Dimension(130, 30));
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addAtachButton(JButton button) {
		if (button.getText().equals("")) button.setText("Powiąż");
		Icon icon = new ImageIcon(getClass().getResource("/handshake-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addDetachButton(JButton button) {
		if (button.getText().equals("")) button.setText("Odłącz");
		Icon icon = new ImageIcon(getClass().getResource("/handshake-slash-solid.png"));
		button.setIcon(icon);
		button = createDefaultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addButton(JButton button) {
		button = createDefaultButton(button);
		this.buttons.add(button);
		this.add(button);
	}
	
	public void addButton(JButton button, Icon icon) {
		button = createDefaultButton(button);
		button.setIcon(icon);
		this.buttons.add(button);
		this.add(button);
	}
	
	public void addButton(JButton button, int weight, int high) {
		button = createDefaultButton(button);
		button.setPreferredSize(new Dimension(weight, high));
		this.buttons.add(button);
		this.add(button);
	}
	
	private JButton createDefaultButton(JButton button) {
		button.setLayout(new BorderLayout(10,10));
		button.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		button.setPreferredSize(new Dimension(75, 30));
		button.setFocusable(false);
		this.defaultListener(button);
		
		return button;
	}
	
	public void removeButton(int indexButton) {
		this.remove(indexButton);
		this.buttons.remove(indexButton);
	}
	
	public void removeAllButtons() {
		
		int startIndex = buttons.size() - 1;
		for(int index = startIndex; index >= 0; index--) {
			removeButton(index);
		}
	}

	public List<JButton> getButtons() {
		return buttons;
	}
	
	public void defaultListener(JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(new JFrame(), "Przycisk jeszcze nie ma podpiętej akcji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public Color getButtonHeaderBackgroundColor() {
		return buttonHeaderBackgroundColor;
	}

	public Color getButtonFooterBackgroundColor() {
		return buttonFooterBackgroundColor;
	}

	public int getLayoutPosition() {
		return layoutPosition;
	}

	public void setLayoutPosition(int layoutPosition) {
		this.layoutPosition = layoutPosition;
	}

}
