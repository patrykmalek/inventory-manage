package kruszywo.sa.computers.manage.view.device;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.dao.TypeDAO;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.PMJComboBox;
import kruszywo.sa.computers.manage.view.util.PMJTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import java.awt.Choice;
import javax.swing.JComboBox;

public class DeviceDetailsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private PMJTextField deviceNameField;
	private PMJTextField deviceUniqueNumberField;
	private PMJTextField deviceInventoryNumberField;
	private PMJTextField deviceTypeNameField;
	private PMJTextField deviceAssignedDepartmentField;
	private PMJTextField deviceAssignedEmployeeField;
	private PMJTextField deviceInvoiceNumberField;
	private JDateChooser devicePurchaseDateField;
	private JDateChooser deviceLastInstallationDateField;
	private JTextPane deviceNotes;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public DeviceDetailsFrame(Controller controller) {
		this.controller = controller;
		this.controller.setDeviceDetailsFrame(this);
//		createVisuals();
//		createEventListeners();
	}
	
	public void showWindow() {
		createVisuals();
		createEventListeners();
	}

	private void createVisuals() {
		this.setTitle(getCorrectTitle());
		this.setIconImage(new ImageIcon(getClass().getResource("/edit-solid-dark-blue-15.png")).getImage());
		this.setSize(800, 650);
		this.headerPanel = createHeaderPanel();
		this.detailsPanel = createDetailsPanel();
		this.footerPanel = createFooterPanel();
		this.getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.detailsPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
		
		JLabel additionalTitleHeaderLabel = new JLabel("Komputer, Patryk Małek, Dział IT");
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
		
		deviceTypeNameField = new PMJTextField(true, 13);
		deviceTypeNameField.setEditable(isEditable());
		deviceTypeNameField.setColumns(10);
		detailsPanel.add(deviceTypeNameField, "cell 2 4 4 1,grow");
		
		JLabel deviceAssignedDepartmentLabel = new JLabel("Miejsce użycia:");
		deviceAssignedDepartmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedDepartmentLabel, "cell 1 5,alignx left");
		
		deviceAssignedDepartmentField = new PMJTextField(true, 13);
		deviceAssignedDepartmentField.setEditable(isEditable());
		deviceAssignedDepartmentField.setColumns(10);
		detailsPanel.add(deviceAssignedDepartmentField, "cell 2 5 4 1,grow");
		
		JLabel deviceAssignedEmployeeLabel = new JLabel("Przypisany pracownik:");
		deviceAssignedEmployeeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedEmployeeLabel, "cell 1 6,alignx left");
		
		deviceAssignedEmployeeField = new PMJTextField(true, 13);
		deviceAssignedEmployeeField.setEditable(isEditable());
		deviceAssignedEmployeeField.setColumns(10);
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
		
		devicePurchaseDateField = new JDateChooser();
		devicePurchaseDateField.setEnabled(isEditable());
		devicePurchaseDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		devicePurchaseDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		devicePurchaseDateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(devicePurchaseDateField, "cell 2 8,grow");
		
//		PMJComboBox comboBox = new PMJComboBox<Device>(true);
//		detailsPanel.add(comboBox, "cell 4 8,growx");
		
		JLabel deviceLastInstallationDateLabel = new JLabel("Data ostatniej instalacji:");
		deviceLastInstallationDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceLastInstallationDateLabel, "cell 1 9,alignx left");
		
		deviceLastInstallationDateField = new JDateChooser();
		deviceLastInstallationDateField.setEnabled(isEditable());
		deviceLastInstallationDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		deviceLastInstallationDateField.setDate(getController().getCalendarWithTodayDate().getTime());
		deviceLastInstallationDateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
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
		
	}
	
	public void addDeviceDataToView(Device device) {
		
		if(device == null) return;

		deviceNameField.setText(device.getDeviceName());                
		deviceUniqueNumberField.setText(device.getDeviceUniqueNumber());
		deviceInventoryNumberField.setText(device.getDeviceInventoryNumber());
		deviceTypeNameField.setText(device.getDeviceType().getDeviceTypeName());
		deviceAssignedDepartmentField.setText(device.getAssignedDepartment().getDepartmentName());
		deviceAssignedEmployeeField.setText(device.getAssignedEmployee().getFirstName() + " " + device.getAssignedEmployee().getLastName());
		deviceInvoiceNumberField.setText(device.getInvoiceNumber());
		devicePurchaseDateField.setDate(getController().getCalendarWithCustomDate(device.getPurchaseDate(), getController().getDefaultDateFormat()).getTime());
		deviceLastInstallationDateField.setDate(getController().getCalendarWithCustomDate(device.getLastInstallationDate(), getController().getDefaultDateFormat()).getTime());
		
	}
	
	public void saveDeviceData() {
		
		Device device = new Device();
		
		device.setDeviceName(deviceNameField.getText());
		device.setDeviceUniqueNumber(deviceUniqueNumberField.getText());
		device.setDeviceInventoryNumber(deviceInventoryNumberField.getText());
//		device.setDeviceTypeID(Integer.parseInt(deviceTypeNameField.getText()));
//		device.setAssignedDepartmentID(Integer.parseInt(deviceAssignedDepartmentField.getText()));
//		device.setAssignedEmployeeID(Integer.parseInt(deviceAssignedEmployeeField.getText()));
		device.setInvoiceNumber(deviceInvoiceNumberField.getText());
		device.setPurchaseDate(devicePurchaseDateField.getDateFormatString());
		device.setLastInstallationDate(deviceLastInstallationDateField.getDateFormatString());
		device.setNotes(deviceNotes.getText());
		
		
		getController().saveDeviceData(device, getOperationType());
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
		this.operationType = operationType;
	}

	
}
