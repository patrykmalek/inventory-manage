package kruszywo.sa.computers.manage.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import kruszywo.sa.computers.manage.controller.Controller;


public class MenuBarPanel extends JMenuBar {

	private static final long serialVersionUID = 5845864327274589304L;
	
	private JMenu dictionaryMenu;
	private JMenuItem deviceDictionaryItem;
	private JMenuItem deviceTypeDictionaryItem;
	private JMenuItem departmentDictionaryItem;

	private Controller controller;

	public MenuBarPanel(Controller controller) {
		this.controller = controller;
		createVisuals();
		createEventListeners();
	}

	private void createVisuals() {
		this.setBackground(Color.BLUE);
		
		dictionaryMenu = new JMenu("Słowniki");
		deviceDictionaryItem = createMenuItem("Urządzenia", "laptop-solid.png");
		deviceTypeDictionaryItem = createMenuItem("Typy urządzeń", "keyboard-solid.png");
		departmentDictionaryItem = createMenuItem("Miejsce użytkowania", "laptop-house-solid.png");
		
		dictionaryMenu.add(deviceDictionaryItem);
		dictionaryMenu.add(deviceTypeDictionaryItem);
		dictionaryMenu.add(departmentDictionaryItem);
		
		this.add(dictionaryMenu);
	}
	
	private void createEventListeners() {
		deviceDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addDevicesPanel();
			}
		});
		deviceTypeDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addDevicesTypeDictionaryPanel();
			}
		});
		departmentDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addDepartmentDictionaryPanel();
			}
		});
	}

	public Controller getController() {
		return controller;
	}
	
	private JMenuItem createMenuItem(String menuItemName, String menuItemIcon) {
		return new JMenuItem(menuItemName, new ImageIcon(getClass().getResource("/" + menuItemIcon)));
	}
	
	
}
