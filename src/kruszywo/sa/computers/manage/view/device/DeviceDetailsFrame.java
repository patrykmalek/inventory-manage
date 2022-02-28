package kruszywo.sa.computers.manage.view.device;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;

import kruszywo.sa.computers.manage.model.Device;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;

public class DeviceDetailsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField deviceNameField;
	private JTextField deviceUniqueNumberField;
	private JTextField deviceInventoryNumberField;
	private JTextField deviceTypeNameField;
	private JTextField deviceAssignedDepartmentField;
	private JTextField deviceAssignedEmployeeField;
	private JTextField deviceInvoiceNumberField;
	private JDateChooser devicePurchaseDateField;
	private JDateChooser deviceLastInstallationDateField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	
	
	public DeviceDetailsFrame() {
		createVisuals();
	}

	private void createVisuals() {
		this.setTitle("Szczegóły urządzenia");
		this.setSize(800, 600);
		this.headerPanel = createHeaderPanel();
		this.detailsPanel = createDetailsPanel();
		this.getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.detailsPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout(25, 25));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 25));
		
		JLabel iconLabel = new JLabel(new ImageIcon("C:\\Users\\Patryk\\Documents\\EclipseWorkspace\\Java\\ComputersManage\\resources\\icon\\desktop-solid-header.png"));
//		JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/desktop-solid-header.png")));
		headerPanel.add(iconLabel, BorderLayout.LINE_START);
		
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new MigLayout("", "[grow][grow]", "[][][][]"));
		
		headerPanel.add(titlePanel, BorderLayout.CENTER);
		
		JLabel titleHeaderLabel = new JLabel("Urządzenie");
		titleHeaderLabel.setForeground(SystemColor.textInactiveText);
		titleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlePanel.add(titleHeaderLabel, "cell 0 1,alignx left,growy");
		
		JLabel additionalTitleHeaderLabel = new JLabel("Komputer, Patryk Małek, Dział IT");
		additionalTitleHeaderLabel.setForeground(SystemColor.textInactiveText);
		additionalTitleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titlePanel.add(additionalTitleHeaderLabel, "cell 0 2,grow");
		
		return headerPanel;
	}
	
	private JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][150px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px]"));
		
		JLabel deviceNameLabel = new JLabel("Nazwa:");
		deviceNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceNameLabel, "cell 1 1,alignx left");
		
		deviceNameField = new JTextField();
		deviceNameField.setColumns(10);
		deviceNameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceNameField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceNameField, "cell 2 1 3 1,grow");
		
		JLabel deviceUniqueNumberLabel = new JLabel("Numer seryjny:");
		deviceUniqueNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceUniqueNumberLabel, "cell 1 2,alignx left");
		
		deviceUniqueNumberField = new JTextField();
		deviceUniqueNumberField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceUniqueNumberField.setColumns(10);
		deviceUniqueNumberField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceUniqueNumberField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceUniqueNumberField, "cell 2 2 3 1,grow");
		
		JLabel deviceInventoryNumberLabel = new JLabel("Numer inwentarzowy:");
		deviceInventoryNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInventoryNumberLabel, "cell 1 3,alignx left");
		
		deviceInventoryNumberField = new JTextField();
		deviceInventoryNumberField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceInventoryNumberField.setColumns(10);
		deviceInventoryNumberField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceInventoryNumberField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceInventoryNumberField, "cell 2 3 3 1,grow");
		
		JLabel deviceTypeNameLabel = new JLabel("Typ urządzenia:");
		deviceTypeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceTypeNameLabel, "cell 1 4,alignx left");
		
		deviceTypeNameField = new JTextField();
		deviceTypeNameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceTypeNameField.setColumns(10);
		deviceTypeNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceTypeNameField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceTypeNameField, "cell 2 4 3 1,grow");
		
		JLabel deviceAssignedDepartmentLabel = new JLabel("Miejsce użycia:");
		deviceAssignedDepartmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedDepartmentLabel, "cell 1 5,alignx left");
		
		deviceAssignedDepartmentField = new JTextField();
		deviceAssignedDepartmentField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceAssignedDepartmentField.setColumns(10);
		deviceAssignedDepartmentField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceAssignedDepartmentField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceAssignedDepartmentField, "cell 2 5 3 1,grow");
		
		JLabel deviceAssignedEmployeeLabel = new JLabel("Przypisany pracownik:");
		deviceAssignedEmployeeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceAssignedEmployeeLabel, "cell 1 6,alignx left");
		
		deviceAssignedEmployeeField = new JTextField();
		deviceAssignedEmployeeField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceAssignedEmployeeField.setColumns(10);
		deviceAssignedEmployeeField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceAssignedEmployeeField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceAssignedEmployeeField, "cell 2 6 3 1,grow");
		
		JLabel deviceInvoiceNumberLabel = new JLabel("Powiązana faktura:");
		deviceInvoiceNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceInvoiceNumberLabel, "cell 1 7,alignx left");
		
		deviceInvoiceNumberField = new JTextField();
		deviceInvoiceNumberField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deviceInvoiceNumberField.setColumns(10);
		deviceInvoiceNumberField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (Color) UIManager.getColor("Button.shadow")));
		deviceInvoiceNumberField.setBackground(SystemColor.menu);
		detailsPanel.add(deviceInvoiceNumberField, "cell 2 7 3 1,grow");
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		JLabel devicePurchaseDateLabel = new JLabel("Data zakupu:");
		devicePurchaseDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(devicePurchaseDateLabel, "cell 1 8,alignx left");
		
		devicePurchaseDateField = new JDateChooser();
		devicePurchaseDateField.setDateFormatString(new SimpleDateFormat("yyyy-MM-dd").toPattern());
		devicePurchaseDateField.setDate(calendar.getTime());
		devicePurchaseDateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(devicePurchaseDateField, "cell 2 8,grow");
		
		JLabel deviceLastInstallationDateLabel = new JLabel("Data ostatniej instalacji:");
		deviceLastInstallationDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceLastInstallationDateLabel, "cell 1 9,alignx left");
		
		deviceLastInstallationDateField = new JDateChooser();
		deviceLastInstallationDateField.setDateFormatString(new SimpleDateFormat("yyyy-MM-dd").toPattern());
		deviceLastInstallationDateField.setDate(calendar.getTime());
		deviceLastInstallationDateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceLastInstallationDateField, "cell 2 9,grow");
		
		return detailsPanel;
	}
	
	public void updateView(Device device) {
		
	}
	

}
