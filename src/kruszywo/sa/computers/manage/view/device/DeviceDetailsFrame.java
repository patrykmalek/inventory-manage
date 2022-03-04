package kruszywo.sa.computers.manage.view.device;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Department;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.DeviceType;
import kruszywo.sa.computers.manage.model.Employee;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.PMCustomTextFieldWithJList;
import kruszywo.sa.computers.manage.view.util.PMJDateChooser;
import kruszywo.sa.computers.manage.view.util.PMJTextField;

import java.awt.BorderLayout;
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

import javax.swing.JTextPane;

public class DeviceDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JLabel additionalTitleHeaderLabel;
	private int deviceID;
	private PMJTextField deviceNameField;
	private PMJTextField deviceUniqueNumberField;
	private PMJTextField deviceInventoryNumberField;
	private PMCustomTextFieldWithJList<DeviceType> deviceTypeField;
	private PMCustomTextFieldWithJList<Department> deviceAssignedDepartmentField;
	private PMCustomTextFieldWithJList<Employee> deviceAssignedEmployeeField;
	private PMJTextField deviceInvoiceNumberField;
	private PMJDateChooser devicePurchaseDateField;
	private PMJDateChooser deviceLastInstallationDateField;
	private JTextPane deviceNotes;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
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
		setSize(800, 650);
		headerPanel = createHeaderPanel();
		detailsPanel = createDetailsPanel();
		footerPanel = createFooterPanel();
		getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		getContentPane().add(this.detailsPanel, BorderLayout.CENTER);
		getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout(25, 25));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 25));
		
//		JLabel iconLabel = new JLabel(new ImageIcon("C:\\Users\\Patryk\\Documents\\EclipseWorkspace\\Java\\ComputersManage\\resources\\icon\\desktop-solid-header.png"));
//		JLabel iconLabel = new JLabel(new ImageIcon("C:\\Users\\pmalek\\eclipse\\Java\\ComputersManage\\resources\\icon\\desktop-solid-75.png"));
		JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/desktop-solid-75.png")));
		headerPanel.add(iconLabel, BorderLayout.LINE_START);
		
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new MigLayout("", "[grow][grow]", "[][][]"));
		
		headerPanel.add(titlePanel, BorderLayout.CENTER);
		
		JLabel titleHeaderLabel = new JLabel("Urządzenie");
		titleHeaderLabel.setForeground(SystemColor.textInactiveText);
		titleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlePanel.add(titleHeaderLabel, "cell 0 0,alignx left,growy");
		
		additionalTitleHeaderLabel = new JLabel("Komputer, Patryk Małek, Dział IT");
		additionalTitleHeaderLabel.setForeground(SystemColor.textInactiveText);
		additionalTitleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titlePanel.add(additionalTitleHeaderLabel, "cell 0 1,grow");
		
		return headerPanel;
	}
	
	private JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][grow][10px]"));
		
		JLabel deviceNameLabel = new JLabel("Nazwa:");
		deviceNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceNameLabel, "cell 1 1,alignx left");
		
		deviceNameField = new PMJTextField(true, 13);
		deviceNameField.setEditable(isEditable());
		deviceNameField.setColumns(10);
		detailsPanel.add(deviceNameField, "cell 2 1 4 1,grow");
		
		JLabel deviceUniqueNumberLabel = new JLabel("Numer seryjny:");
		deviceUniqueNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceUniqueNumberLabel, "cell 1 2,alignx left");
		
		deviceUniqueNumberField = new PMJTextField(true, 13);
		deviceUniqueNumberField.setEditable(isEditable());
		deviceUniqueNumberField.setColumns(10);
		detailsPanel.add(deviceUniqueNumberField, "cell 2 2 4 1,grow");
		
		JLabel deviceInventoryNumberLabel = new JLabel("Numer inwentarzowy:");
		deviceInventoryNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInventoryNumberLabel, "cell 1 3,alignx left");
		
		deviceInventoryNumberField = new PMJTextField(true, 13);
		deviceInventoryNumberField.setEditable(isEditable());
		deviceInventoryNumberField.setColumns(10);
		detailsPanel.add(deviceInventoryNumberField, "cell 2 3 4 1,grow");
		
		JLabel deviceTypeNameLabel = new JLabel("Typ urządzenia:");
		deviceTypeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceTypeNameLabel, "cell 1 4,alignx left");
		
		deviceTypeField = new PMCustomTextFieldWithJList<DeviceType>();
		deviceTypeField.setEditable(isEditable());
		detailsPanel.add(deviceTypeField, "cell 2 4 4 1, grow");
		
		JLabel deviceAssignedDepartmentLabel = new JLabel("Miejsce użycia:");
		deviceAssignedDepartmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedDepartmentLabel, "cell 1 5,alignx left");
		
		deviceAssignedDepartmentField = new PMCustomTextFieldWithJList<Department>();
		deviceAssignedDepartmentField.setEditable(isEditable());
		detailsPanel.add(deviceAssignedDepartmentField, "cell 2 5 4 1,grow");
		
		JLabel deviceAssignedEmployeeLabel = new JLabel("Przypisany pracownik:");
		deviceAssignedEmployeeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedEmployeeLabel, "cell 1 6,alignx left");
		
		deviceAssignedEmployeeField = new PMCustomTextFieldWithJList<Employee>();
		deviceAssignedEmployeeField.setEditable(isEditable());
		detailsPanel.add(deviceAssignedEmployeeField, "cell 2 6 4 1,grow");
		
		JLabel deviceInvoiceNumberLabel = new JLabel("Powiązana faktura:");
		deviceInvoiceNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInvoiceNumberLabel, "cell 1 7,alignx left");
		
		deviceInvoiceNumberField = new PMJTextField(true, 13);
		deviceInvoiceNumberField.setEditable(isEditable());
		deviceInvoiceNumberField.setColumns(10);
		detailsPanel.add(deviceInvoiceNumberField, "cell 2 7 4 1,grow");
		
		JLabel devicePurchaseDateLabel = new JLabel("Data zakupu:");
		devicePurchaseDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(devicePurchaseDateLabel, "cell 1 8,alignx left");
		
		devicePurchaseDateField = new PMJDateChooser();
		devicePurchaseDateField.setEnabled(isEditable());
		devicePurchaseDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		devicePurchaseDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		detailsPanel.add(devicePurchaseDateField, "cell 2 8,grow");
		
		JLabel deviceLastInstallationDateLabel = new JLabel("Data ostatniej instalacji:");
		deviceLastInstallationDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceLastInstallationDateLabel, "cell 1 9,alignx left");
		
		deviceLastInstallationDateField = new PMJDateChooser();
		deviceLastInstallationDateField.setEnabled(isEditable());
		deviceLastInstallationDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		deviceLastInstallationDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		detailsPanel.add(deviceLastInstallationDateField, "cell 2 9,grow");
		
		deviceNotes = new JTextPane();
		deviceNotes.setEnabled(isEditable());
		deviceNotes.setBorder(BorderFactory.createTitledBorder("Dodatkowe informacje"));
		deviceNotes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane deviceNotesContainer = new JScrollPane(deviceNotes);
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
				saveDeviceData();
				dispose();
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
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceTypeDictionaryWindow();
			}
		});
		
		deviceAssignedDepartmentField.getDictionaryButton().removeActionListener(deviceAssignedDepartmentField.getDictionaryButton().getActionListeners()[0]);
		deviceAssignedDepartmentField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceServiceDAO().openDepartmentDictionaryWindow();
			}
		});
		
		deviceAssignedEmployeeField.getDictionaryButton().removeActionListener(deviceAssignedEmployeeField.getDictionaryButton().getActionListeners()[0]);
		deviceAssignedEmployeeField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
	}
	
	public boolean addDeviceDataToView(Device device) {
		
		if(device == null) return false;
		setDeviceID(device.getDeviceID());
		
		additionalTitleHeaderLabel.setText(device.getDeviceType().getDeviceTypeName() + ", " + 
											device.getAssignedEmployee().getFirstName() + " " + 
											device.getAssignedEmployee().getLastName() +", " + 
											device.getAssignedDepartment().getDepartmentName());
		
		deviceNameField.setText(device.getDeviceName());                
		deviceUniqueNumberField.setText(device.getDeviceUniqueNumber());
		deviceInventoryNumberField.setText(device.getDeviceInventoryNumber());
		
		deviceTypeField.addItem(device.getDeviceType());
		deviceAssignedDepartmentField.addItem(device.getAssignedDepartment());
		deviceAssignedEmployeeField.addItem(device.getAssignedEmployee());
		
		deviceInvoiceNumberField.setText(device.getInvoiceNumber());
		devicePurchaseDateField.setDate(getController().getCalendarWithCustomDate(device.getPurchaseDate(), getController().getDefaultDateFormat()).getTime());
		deviceLastInstallationDateField.setDate(getController().getCalendarWithCustomDate(device.getLastInstallationDate(), getController().getDefaultDateFormat()).getTime());
		deviceNotes.setText(device.getNotes());
		
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
		device.setPurchaseDate(CommonFunctions.formatDate(devicePurchaseDateField.getDate(), devicePurchaseDateField.getDateFormatString()));
		device.setLastInstallationDate(CommonFunctions.formatDate(deviceLastInstallationDateField.getDate(), deviceLastInstallationDateField.getDateFormatString()));
		device.setNotes(deviceNotes.getText());
		
		getController().getManagerDAO().getDeviceServiceDAO().saveData(device, getOperationType());
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

	
}
