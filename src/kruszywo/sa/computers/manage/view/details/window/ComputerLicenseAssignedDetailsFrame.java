package kruszywo.sa.computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;


import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.License;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.PMCustomTextFieldWithDictionary;

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
import java.util.ArrayList;
import java.util.List;


public class ComputerLicenseAssignedDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel additionalTitleHeaderLabel;
	private JLabel computerComponentDeviceLabel;
	
	private PMCustomTextFieldWithDictionary<Device> deviceField;
	private PMCustomTextFieldWithDictionary<License> licenseField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public ComputerLicenseAssignedDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setComputerLicenseAssignedDetailsFrame(this);
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
		setSize(800, 400);
		createPanels();
		getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		getContentPane().add(this.detailsPanel, BorderLayout.CENTER);
		getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void createPanels() {
		headerPanel = createHeaderPanel();
		detailsPanel = createDetailsPanel();
		footerPanel = createFooterPanel();
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
		
		JLabel titleHeaderLabel = new JLabel("Powiązanie licencji");
		titleHeaderLabel.setForeground(SystemColor.textInactiveText);
		titleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlePanel.add(titleHeaderLabel, "cell 0 0,alignx left,growy");
		
		additionalTitleHeaderLabel = new JLabel("Szczegóły");
		additionalTitleHeaderLabel.setForeground(SystemColor.textInactiveText);
		additionalTitleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titlePanel.add(additionalTitleHeaderLabel, "cell 0 1,grow");
		
		return headerPanel;
	}
	
	public JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][grow][10px]"));
		
		computerComponentDeviceLabel = new JLabel("Komputer:");
		computerComponentDeviceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentDeviceLabel, "cell 1 1,alignx left");
		
		deviceField = new PMCustomTextFieldWithDictionary<Device>();
		deviceField.setEditable(false);
		detailsPanel.add(deviceField, "cell 2 1 4 1,grow");
		
		JLabel computerComponentCPULabel= new JLabel("Procesor:");
		computerComponentCPULabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentCPULabel, "cell 1 2,alignx left");
		
		licenseField = new PMCustomTextFieldWithDictionary<License>();
		licenseField.setEditable(isEditable());
		detailsPanel.add(licenseField, "cell 2 2 4 1,grow");

		
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
	
	public void createEventListeners() {
		if (buttonFooterPanel.getButtons().size() <= 0) return;
		
		JButton saveButton = buttonFooterPanel.getButtons().get(0);
		saveButton.removeActionListener(saveButton.getActionListeners()[0]);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(validateFields()) {
					assignedDeviceToLicense();
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
		
		deviceField.getDictionaryButton().removeActionListener(deviceField.getDictionaryButton().getActionListeners()[0]);
		deviceField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().openDeviceDictionaryWindowAndAddItem();
			}
		});
		
		licenseField.getDictionaryButton().removeActionListener(licenseField.getDictionaryButton().getActionListeners()[0]);
		licenseField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().openLicenseDictionaryWindowAndAddItem();
			}
		});
	}
	
	public boolean addComputerComponentDataToView(Device device) {
		
		deviceField.addItem(device);
		
		return true;
	}
	
	public void assignedDeviceToLicense() {
		
		License license = licenseField.getItem();
		license.setDevice(deviceField.getItem());
		
		getController().getManagerDAO().getLicenseServiceDAO().assignedDeviceToLicense(license);
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(deviceField.isEmpty());
		errors.add(licenseField.isEmpty());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Przypisanie licencji" : "Przypisanie licencji";
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

	public PMCustomTextFieldWithDictionary<Device> getDeviceField() {
		return deviceField;
	}
	public PMCustomTextFieldWithDictionary<License> getLicenseField() {
		return licenseField;
	}
	
}
