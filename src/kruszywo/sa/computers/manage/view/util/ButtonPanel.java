package kruszywo.sa.computers.manage.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	private JButton createDefaultButton(JButton button) {
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
		
		button.addMouseListener(new MouseListener() {
			
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
				button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
