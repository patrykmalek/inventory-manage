package computers.manage.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import computers.manage.controller.Controller;


public class MenuBarPanel extends JMenuBar {

	private static final long serialVersionUID = 5845864327274589304L;
	
	private JMenu dictionaryMenu;
	private JMenu dictionaryComputersComponents;
	
	private JMenu settingsMenu;
	private JMenu helpMenu;
	
	private JMenuItem deviceDictionaryItem;
	private JMenuItem deviceTypeDictionaryItem;
	private JMenuItem departmentDictionaryItem;
	private JMenuItem employeeDictionaryItem;
	private JMenuItem licensesDictionaryItem;
	private JMenuItem softwareDictionaryItem;
	private JMenuItem documentsDictionaryItem;
	
	private JMenuItem allComponentsDictionaryItem;
	private JMenuItem computerCpuDictionaryItem;
	private JMenuItem computerRamDictionaryItem;
	private JMenuItem computerMassStorageDictionaryItem;
	
	private JMenuItem databaseSettingsItem;
	
	private JMenuItem aboutAppItem;
	
	private Controller controller;


	public MenuBarPanel(Controller controller) {
		this.controller = controller;
		createVisuals();
		createEventListeners();
	}

	private void createVisuals() {
		this.setBackground(Color.BLUE);
		
		dictionaryMenu = new JMenu("Słowniki");
		deviceDictionaryItem = createMenuItem(TabbedPanel.DEVICES_PANEL, "laptop-solid.png");
		deviceTypeDictionaryItem = createMenuItem(TabbedPanel.DEVICES_TYPE_DICTIONARY_PANEL, "keyboard-solid.png");
		departmentDictionaryItem = createMenuItem(TabbedPanel.DEPARTMENT_DICTIONARY_PANEL, "laptop-house-solid.png");
		employeeDictionaryItem = createMenuItem(TabbedPanel.EMPLOYEE_DICTIONARY_PANEL, "employees.png");
		licensesDictionaryItem = createMenuItem(TabbedPanel.LICENSES_DICTIONARY_PANEL, "certificate-solid.png");
		softwareDictionaryItem = createMenuItem(TabbedPanel.SOFTWARE_DICTIONARY_PANEL, "software-brands.png");
		documentsDictionaryItem = createMenuItem(TabbedPanel.DOCUMENTS_DICTIONARY_PANEL, "file-alt-solid.png");
				
		dictionaryComputersComponents = new JMenu("Podzespoły komputerowe");
		allComponentsDictionaryItem = createMenuItem(TabbedPanel.COMPUTERS_COMPONENTS_DICTIONARY_PANEL, "laptop-house-solid.png");
		computerCpuDictionaryItem = createMenuItem(TabbedPanel.CPU_DICTIONARY_PANEL, "microchip-solid.png");
		computerRamDictionaryItem = createMenuItem(TabbedPanel.RAM_DICTIONARY_PANEL, "memory-solid.png");
		computerMassStorageDictionaryItem = createMenuItem(TabbedPanel.MASS_STORAGE_DICTIONARY_PANEL, "sd-card-solid.png");
		
		dictionaryComputersComponents.add(allComponentsDictionaryItem);
		dictionaryComputersComponents.add(computerCpuDictionaryItem);
		dictionaryComputersComponents.add(computerRamDictionaryItem);
		dictionaryComputersComponents.add(computerMassStorageDictionaryItem);
		
		dictionaryMenu.add(deviceDictionaryItem);
		dictionaryMenu.add(deviceTypeDictionaryItem);
		dictionaryMenu.add(departmentDictionaryItem);
		dictionaryMenu.add(employeeDictionaryItem);
		dictionaryMenu.add(licensesDictionaryItem);
		dictionaryMenu.add(softwareDictionaryItem);
		dictionaryMenu.add(documentsDictionaryItem);
		dictionaryMenu.add(dictionaryComputersComponents);
		
		
		
		settingsMenu = new JMenu("Ustawienia");
		
		databaseSettingsItem = createMenuItem("Baza danych", "database-solid.png");
		settingsMenu.add(databaseSettingsItem);
		
		helpMenu = new JMenu("Pomoc");
		aboutAppItem = createMenuItem("O programie", "bars-solid.png");
		
		helpMenu.add(aboutAppItem);
		
		this.add(dictionaryMenu);
		this.add(settingsMenu);
		this.add(helpMenu);
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
		employeeDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addEmployeeDictionaryPanel();
			}
		});
		licensesDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addLicensesDictionaryPanel();
			}
		});
		softwareDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addSoftwareDictionaryPanel();
			}
		});
		documentsDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addDocumentsDictionaryPanel();
			}
		});
		computerCpuDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addComputerCpuDictionaryPanel();
			}
		});
		computerRamDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addComputerRamDictionaryPanel();
			}
		});
		computerMassStorageDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addComputerMassStorageDictionaryPanel();
			}
		});
		allComponentsDictionaryItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addComputerComponentsDictionaryPanel();
			}
		});
		
		databaseSettingsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showConfigWindow();
			}
		});
		
		aboutAppItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showAboutWindow();
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
