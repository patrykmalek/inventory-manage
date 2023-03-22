package computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import computers.manage.controller.Controller;
import computers.manage.model.Department;
import computers.manage.model.Device;
import computers.manage.model.DeviceType;
import computers.manage.model.Employee;
import computers.manage.model.OperationType;
import computers.manage.view.dictionary.table.panel.ComputerLicenseAssignedTablePanel;
import computers.manage.view.dictionary.table.panel.DeviceConnectedDocumentTablePanel;
import computers.manage.view.util.ButtonPanel;
import computers.manage.view.util.PMCustomTextFieldWithDictionary;
import computers.manage.view.util.PMJDateChooser;
import computers.manage.view.util.PMJScrollPane;
import computers.manage.view.util.PMJTextField;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import java.awt.Color;

public class DeviceDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JLabel additionalTitleHeaderLabel;
	private JLabel deviceNameLabel;
	private JLabel deviceUniqueNumberLabel;
	private JLabel deviceInventoryNumberLabel;
	private JLabel deviceTypeNameLabel;
	private JLabel deviceAssignedDepartmentLabel;
	private JLabel deviceAssignedEmployeeLabel;
	private JLabel deviceInvoiceNumberLabel;
	private JLabel devicePurchaseDateLabel;
	private JLabel deviceLastInstallationDateLabel;

	private int deviceID;
	private PMJTextField deviceNameField;
	private PMJTextField deviceUniqueNumberField;
	private PMJTextField deviceInventoryNumberField;
	private PMCustomTextFieldWithDictionary<DeviceType> deviceTypeField;
	private PMCustomTextFieldWithDictionary<Department> deviceAssignedDepartmentField;
	private PMCustomTextFieldWithDictionary<Employee> deviceAssignedEmployeeField;
	private PMJTextField deviceInvoiceNumberField;
	private PMJDateChooser devicePurchaseDateField;
	private PMJDateChooser deviceLastInstallationDateField;
	private JTextPane deviceNotesField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private JSplitPane splitPane;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	private boolean isComputer;
	
	private OperationType operationType;
	
	private Controller controller;
	
	private ComputerLicenseAssignedTablePanel computerLicenseAssignedTablePanel;
	private DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel;
	
	private ComputerComponentDetailsFrame computerComponentDetailsFrame;
	private JPanel computerComponentDetailsPanel;
	
	private String[] namesToShowComputerNameField = {"laptop", "laptopy", "laptops", "komputer", "komputery", "computer", "computers", "notebook", "notebooks"};
	private JCheckBox deviceUsedField;
	
	private JTabbedPane tabPanel;
	
	private boolean computerComponentsShow;
	
	public DeviceDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setDeviceDetailsFrame(this);
	}
	
	public void createWindow() {
		createVisuals();
		createEventListeners();
	}
	
	public void showWindow() {
		setVisible(true);
	}

	private void createVisuals() {
		setTitle(getCorrectTitle());
		setIconImage(new ImageIcon(getClass().getResource("/edit-solid-dark-blue-15.png")).getImage());
		setSize(1300, 750);
		headerPanel = createHeaderPanel();
		detailsPanel = createDetailsPanel();
		tabPanel = createTabbedPanel();
		footerPanel = createFooterPanel();		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new PMJScrollPane(this.detailsPanel), null);
		splitPane.setDividerLocation(600);
		splitPane.setBorder(null);
		tabPanel.add("Dane podstawowe", splitPane);
		if(getDeviceConnectedDocumentTablePanel() != null) tabPanel.add("Dokumenty", getDeviceConnectedDocumentTablePanel());
		getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		getContentPane().add(this.tabPanel, BorderLayout.CENTER);
		getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout(25, 25));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 25));
		
		JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/desktop-solid-75.png")));
		headerPanel.add(iconLabel, BorderLayout.LINE_START);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new MigLayout("", "[grow][grow]", "[][][]"));
		
		headerPanel.add(titlePanel, BorderLayout.CENTER);
		
		JLabel titleHeaderLabel = new JLabel("Urządzenie");
		titleHeaderLabel.setForeground(SystemColor.textInactiveText);
		titleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlePanel.add(titleHeaderLabel, "cell 0 0,alignx left,growy");
		
		additionalTitleHeaderLabel = new JLabel("");
		additionalTitleHeaderLabel.setForeground(SystemColor.textInactiveText);
		additionalTitleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titlePanel.add(additionalTitleHeaderLabel, "cell 0 1,grow");
		
		return headerPanel;
	}
	
	private JTabbedPane createTabbedPanel() {
		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		CardLayout panelLayout = new CardLayout();
		tabPanel.setLayout(panelLayout);
		tabPanel.setBorder(BorderFactory.createEmptyBorder());
		
		tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPanel.setFocusable(false);
		

		
		return tabPanel;
	}
	
	private JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][grow][10px]"));
		
		deviceNameLabel = new JLabel("Nazwa:");
		deviceNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceNameLabel, "cell 1 1,alignx left");
		
		deviceNameField = new PMJTextField(true, 13, isEditable());
		deviceNameField.setColumns(10);
		detailsPanel.add(deviceNameField, "cell 2 1 4 1,grow");
		
		deviceUniqueNumberLabel = new JLabel("Numer seryjny:");
		deviceUniqueNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceUniqueNumberLabel, "cell 1 2,alignx left");
		
		deviceUniqueNumberField = new PMJTextField(true, 13, isEditable());
		deviceUniqueNumberField.setEditable(isEditable());
		deviceUniqueNumberField.setColumns(10);
		detailsPanel.add(deviceUniqueNumberField, "cell 2 2 4 1,grow");
		
		deviceInventoryNumberLabel = new JLabel("Numer inwentarzowy:");
		deviceInventoryNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInventoryNumberLabel, "cell 1 3,alignx left");
		
		deviceInventoryNumberField = new PMJTextField(true, 13, isEditable());
		deviceInventoryNumberField.setColumns(10);
		detailsPanel.add(deviceInventoryNumberField, "cell 2 3 4 1,grow");
		
		deviceTypeNameLabel = new JLabel("Typ urządzenia:");
		deviceTypeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceTypeNameLabel, "cell 1 4,alignx left");
		
		deviceTypeField = new PMCustomTextFieldWithDictionary<DeviceType>(isEditable());
		detailsPanel.add(deviceTypeField, "cell 2 4 4 1, grow");
		
		deviceAssignedDepartmentLabel = new JLabel("Miejsce użycia:");
		deviceAssignedDepartmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedDepartmentLabel, "cell 1 5,alignx left");
		
		deviceAssignedDepartmentField = new PMCustomTextFieldWithDictionary<Department>(isEditable());
		detailsPanel.add(deviceAssignedDepartmentField, "cell 2 5 4 1,grow");
		
		deviceAssignedEmployeeLabel = new JLabel("Przypisany pracownik:");
		deviceAssignedEmployeeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedEmployeeLabel, "cell 1 6,alignx left");
		
		deviceAssignedEmployeeField = new PMCustomTextFieldWithDictionary<Employee>(isEditable());
		detailsPanel.add(deviceAssignedEmployeeField, "cell 2 6 4 1,grow");
		
		deviceInvoiceNumberLabel = new JLabel("Powiązana faktura:");
		deviceInvoiceNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInvoiceNumberLabel, "cell 1 7,alignx left");
		
		deviceInvoiceNumberField = new PMJTextField(true, 13, isEditable());
		deviceInvoiceNumberField.setColumns(10);
		detailsPanel.add(deviceInvoiceNumberField, "cell 2 7 4 1,grow");
		
		devicePurchaseDateLabel = new JLabel("Data zakupu:");
		devicePurchaseDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(devicePurchaseDateLabel, "cell 1 8,alignx left");
		
		devicePurchaseDateField = new PMJDateChooser();
		devicePurchaseDateField.setEnabled(isEditable());
		devicePurchaseDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		devicePurchaseDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		detailsPanel.add(devicePurchaseDateField, "cell 2 8,grow");
		
		deviceUsedField = new JCheckBox("Użyty");
		deviceUsedField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceUsedField.setBackground(Color.WHITE);
		deviceUsedField.setFocusable(false);
		deviceUsedField.setEnabled(isEditable());
		detailsPanel.add(deviceUsedField, "cell 3 8,alignx center");
		
		deviceLastInstallationDateLabel = new JLabel("Data ostatniej instalacji:");
		deviceLastInstallationDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceLastInstallationDateLabel, "cell 1 9,alignx left");
		
		deviceLastInstallationDateField = new PMJDateChooser();
		deviceLastInstallationDateField.setEnabled(isEditable());
		deviceLastInstallationDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		deviceLastInstallationDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		detailsPanel.add(deviceLastInstallationDateField, "cell 2 9,grow");
		
		deviceNotesField = new JTextPane();
		deviceNotesField.setEnabled(isEditable());
		deviceNotesField.setBorder(BorderFactory.createTitledBorder("Dodatkowe informacje"));
		deviceNotesField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane deviceNotesContainer = new JScrollPane(deviceNotesField);
		deviceNotesContainer.setBorder(null);
		detailsPanel.add(deviceNotesContainer, "cell 1 11 6 1,grow");
		
		return detailsPanel;
	}
	
	
	private JPanel createFooterPanel() {
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BorderLayout(10, 10));
		footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		buttonFooterPanel = new ButtonPanel(FlowLayout.TRAILING);
		
		JButton saveButton = new JButton();
		JButton cancelButton = new JButton();
		
		saveButton.setEnabled(isEditable());
		cancelButton.setEnabled(isEditable());
		
		buttonFooterPanel.addSaveButton(saveButton);
		buttonFooterPanel.addCancelButton(cancelButton);
		
		footerPanel.add(buttonFooterPanel, BorderLayout.CENTER);

		return footerPanel;
	}
	
	private void createEventListeners() {
		if (buttonFooterPanel.getButtons().size() <= 0) return;
		
		JButton saveButton = buttonFooterPanel.getButtons().get(0);
		saveButton.removeActionListener(saveButton.getActionListeners()[0]);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(validateFields()) {
					saveDeviceData();
					dispose();
				}
			}
		});
		
		JButton cancelButton = buttonFooterPanel.getButtons().get(1);
		cancelButton.removeActionListener(cancelButton.getActionListeners()[0]);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		deviceTypeField.getDictionaryButton().removeActionListener(deviceTypeField.getDictionaryButton().getActionListeners()[0]);
		deviceTypeField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceTypeDictionaryWindowAndAddItem();
			}
		});
		
		deviceTypeField.getCustomTextField().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					getController().getManagerDAO().getDeviceServiceDAO().findDeviceTypeByTextAndAddItemToDeviceDetailsPanel();
				}
			}
		});
		
		deviceAssignedDepartmentField.getDictionaryButton().removeActionListener(deviceAssignedDepartmentField.getDictionaryButton().getActionListeners()[0]);
		deviceAssignedDepartmentField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceServiceDAO().openDepartmentDictionaryWindowAndAddItem();
			}
		});
		
		deviceAssignedDepartmentField.getCustomTextField().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					getController().getManagerDAO().getDeviceServiceDAO().findDepartmentByTextAndAddItemToDeviceDetailsPanel();
				}
			}
		});
		
		deviceAssignedEmployeeField.getDictionaryButton().removeActionListener(deviceAssignedEmployeeField.getDictionaryButton().getActionListeners()[0]);
		deviceAssignedEmployeeField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceServiceDAO().openEmployeeDictionaryWindowAndAddItem();
			}
		});
		
		deviceAssignedEmployeeField.getCustomTextField().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					getController().getManagerDAO().getDeviceServiceDAO().findEmployeeByTextAndAddItemToDeviceDetailsPanel();
				}
			}
		});
		
	}
	
	public boolean addDeviceDataToView(Device device) {
		
		if(device == null) return false;
			setDeviceID(device.getDeviceID());
			
		if(device.getDeviceType().getDeviceTypeName() != null)
			setComputer(Stream.of(getNamesToShowComputerNameField()).anyMatch(x -> x.equals(device.getDeviceType().getDeviceTypeName().toLowerCase())));
		
		if(isComputer())
			showComputerComponents();
		
		additionalTitleHeaderLabel.setText(device.getDeviceName());
		
		deviceNameField.setText(device.getDeviceName());                
		deviceUniqueNumberField.setText(device.getDeviceUniqueNumber());
		deviceInventoryNumberField.setText(device.getDeviceInventoryNumber());
		
		deviceTypeField.addItem(device.getDeviceType());
		deviceAssignedDepartmentField.addItem(device.getAssignedDepartment());
		deviceAssignedEmployeeField.addItem(device.getAssignedEmployee());
		
		deviceInvoiceNumberField.setText(device.getInvoiceNumber());
		devicePurchaseDateField.setCalendar(getController().getCalendarWithCustomDate(device.getPurchaseDate(), getController().getDefaultDateFormat(), false));
		deviceLastInstallationDateField.setCalendar(getController().getCalendarWithCustomDate(device.getLastInstallationDate(), getController().getDefaultDateFormat(), false));
		deviceNotesField.setText(device.getNotes());
		deviceUsedField.setSelected(device.isUsed());
		
		return true;
	}
	
	public void saveDeviceData() {
		
		Device device = new Device();
		
		device.setDeviceID(getDeviceID());
		device.setDeviceName(deviceNameField.getText());
		device.setDeviceUniqueNumber(deviceUniqueNumberField.getText());
		device.setDeviceInventoryNumber(deviceInventoryNumberField.getText());
		device.setDeviceType(deviceTypeField.getItem());
		device.setAssignedDepartment(deviceAssignedDepartmentField.getItem());
		device.setAssignedEmployee(deviceAssignedEmployeeField.getItem());
		device.setInvoiceNumber(deviceInvoiceNumberField.getText());
		device.setPurchaseDate(devicePurchaseDateField.getCustomDate());
		device.setLastInstallationDate(deviceLastInstallationDateField.getCustomDate());
		device.setNotes(deviceNotesField.getText());
		device.setUsed(deviceUsedField.isSelected());
	
		getController().getManagerDAO().getDeviceServiceDAO().saveData(device, getOperationType());
		
		if(isComputer()) {
			getComputerComponentDetailsFrame().saveComputerComponentData(device);
		}
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(deviceNameField.isEmpty());
		errors.add(deviceUniqueNumberField.isEmpty());
//		errors.add(deviceInventoryNumberField.isEmpty());
		
		return !errors.contains(true);
	}
	
	public void showComputerComponents() {
		if(!isComputerComponentsShow()) {
			setComputerComponentsShow(true);
			splitPane.add(getComputerComponentDetailsPanel());
			splitPane.setDividerLocation(600);
			tabPanel.add("Licencje", getComputerLicenseAssignedTablePanel());
			repaint();
		}
	}
	
	public void removeComputerComponents() {
		if(splitPane.getComponentCount() > 2 && isComputerComponentsShow()) {
			splitPane.remove(2);
			tabPanel.remove(getComputerLicenseAssignedTablePanel());
			setComputerComponentsShow(false);
			repaint();
		}
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja urządzenia" : "Szczegóły urządzenia";
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Controller getController() {
		return controller;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		setEditable(operationType.isWindowEditable());
		this.operationType = operationType;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public PMCustomTextFieldWithDictionary<DeviceType> getDeviceTypeField() {
		return deviceTypeField;
	}

	public PMCustomTextFieldWithDictionary<Department> getDeviceAssignedDepartmentField() {
		return deviceAssignedDepartmentField;
	}

	public PMCustomTextFieldWithDictionary<Employee> getDeviceAssignedEmployeeField() {
		return deviceAssignedEmployeeField;
	}

	public void setComputer(boolean isComputer) {
		this.isComputer = isComputer;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public String[] getNamesToShowComputerNameField() {
		return namesToShowComputerNameField;
	}

	public void setIdsToShowComputerNameField(String[] namesToShowComputerNameField) {
		this.namesToShowComputerNameField = namesToShowComputerNameField;
	}

	public ComputerComponentDetailsFrame getComputerComponentDetailsFrame() {
		return computerComponentDetailsFrame;
	}

	public void setComputerComponentDetailsFrame(ComputerComponentDetailsFrame computerComponentDetailsFrame) {
		this.computerComponentDetailsFrame = computerComponentDetailsFrame;
	}

	public JPanel getComputerComponentDetailsPanel() {
		return computerComponentDetailsPanel;
	}

	public void setComputerComponentDetailsPanel(JPanel computerComponentDetailsPanel) {
		this.computerComponentDetailsPanel = computerComponentDetailsPanel;
	}

	public ComputerLicenseAssignedTablePanel getComputerLicenseAssignedTablePanel() {
		return computerLicenseAssignedTablePanel;
	}

	public void setComputerLicenseAssignedTablePanel(ComputerLicenseAssignedTablePanel computerLicenseAssignedTablePanel) {
		this.computerLicenseAssignedTablePanel = computerLicenseAssignedTablePanel;
	}

	public boolean isComputerComponentsShow() {
		return computerComponentsShow;
	}

	public void setComputerComponentsShow(boolean computerComponentsShow) {
		this.computerComponentsShow = computerComponentsShow;
	}


	public DeviceConnectedDocumentTablePanel getDeviceConnectedDocumentTablePanel() {
		return deviceConnectedDocumentTablePanel;
	}

	public void setDeviceConnectedDocumentTablePanel(DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel) {
		this.deviceConnectedDocumentTablePanel = deviceConnectedDocumentTablePanel;
	}
	
}
