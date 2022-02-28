package kruszywo.sa.computers.manage.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

	public ButtonPanel() {
		this.createVisuals();
	}

	private void createVisuals() {
		this.setBackground(new Color(245,245,245));
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
	}
	
	public void addInsertButton(JButton button) {
		if (button.getText().equals("")) button.setText("Dodaj");
		Icon icon = new ImageIcon(getClass().getResource("/plus-circle-solid.png"));
		button.setIcon(icon);
		button = createDefultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addEditButton(JButton button) {
		if (button.getText().equals("")) button.setText("Edytuj");
		Icon icon = new ImageIcon(getClass().getResource("/edit-solid.png"));
		button.setIcon(icon);
		button = createDefultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addDeleteButton(JButton button) {
		if (button.getText().equals("")) button.setText("Usuń");
		Icon icon = new ImageIcon(getClass().getResource("/trash-alt-solid.png"));
		button.setIcon(icon);
		button = createDefultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addPrintButton(JButton button) {
		Icon icon = new ImageIcon(getClass().getResource("/printer.png"));
		button.setIcon(icon);
		button = createDefultButton(button);
		this.add(button);
		this.buttons.add(button);
	}
	
	public void addButton(JButton button) {
		button = createDefultButton(button);
		this.buttons.add(button);
		this.add(button);
	}
	
	public void addButton(JButton button, Icon icon) {
		button = createDefultButton(button);
		button.setIcon(icon);
		this.buttons.add(button);
		this.add(button);
	}
	
	private JButton createDefultButton(JButton button) {
		button.setLayout(new BorderLayout(10,10));
		button.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		button.setPreferredSize(new Dimension(75, 30));
		button.setFocusable(false);
		this.defaultListener(button);
		return button;
	}
	
	public void removeButton(int indexButton) {
		this.buttons.remove(indexButton);
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

}
