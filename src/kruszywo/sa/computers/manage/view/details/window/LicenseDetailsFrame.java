package kruszywo.sa.computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.License;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.model.Software;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.PMCustomTextFieldWithDictionary;
import kruszywo.sa.computers.manage.view.util.PMJDateChooser;
import kruszywo.sa.computers.manage.view.util.PMJScrollPane;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextPane;

public class LicenseDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JLabel additionalTitleHeaderLabel;
	private JLabel licenseKeyLabel;
	private JLabel licenseMainKeyLabel;
	private JLabel licenseInvoiceNumberLabel;
	private JLabel licenseAssignedEmailLabel;
	private JLabel licenseSoftwareLabel;
	private JLabel licensePurchaseDateLabel;
	private JLabel licenseLastInstallationDateLabel;
	

	private int licenseID;
	private PMJTextField licenseKeyField;
	private PMJTextField licenseMainKeyField;
	private PMJTextField licenseInvoiceNumberField;
	private PMJTextField licenseAssignedEmailField;
	private PMCustomTextFieldWithDictionary<Software> licenseSoftwareField;
	private PMJDateChooser licensePurchaseDateField;
	private PMJDateChooser licenseLastInstallationDateField;
	private JTextPane licenseNotesField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	public LicenseDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setLicenseDetailsFrame(this);
		createWindow();
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
		setSize(800, 550);
		headerPanel = createHeaderPanel();
		detailsPanel = createDetailsPanel();
		footerPanel = createFooterPanel();
		getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		getContentPane().add(new PMJScrollPane(this.detailsPanel), BorderLayout.CENTER);
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
		
		JLabel titleHeaderLabel = new JLabel("Licencja");
		titleHeaderLabel.setForeground(SystemColor.textInactiveText);
		titleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlePanel.add(titleHeaderLabel, "cell 0 0,alignx left,growy");
		
		additionalTitleHeaderLabel = new JLabel("Szczegóły");
		additionalTitleHeaderLabel.setForeground(SystemColor.textInactiveText);
		additionalTitleHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titlePanel.add(additionalTitleHeaderLabel, "cell 0 1,grow");
		
		return headerPanel;
	}
	
	private JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px][grow][10px]"));
		
		licenseSoftwareLabel = new JLabel("Typ oprogramowania:");
		licenseSoftwareLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseSoftwareLabel, "cell 1 1,alignx left");
		
		licenseSoftwareField = new PMCustomTextFieldWithDictionary<Software>();
		licenseSoftwareField.setEditable(isEditable());
		detailsPanel.add(licenseSoftwareField, "cell 2 1 4 1,grow");
		
		licenseMainKeyLabel = new JLabel("Główny klucz licencji:");
		licenseMainKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseMainKeyLabel, "cell 1 2,alignx left");
		
		licenseMainKeyField = new PMJTextField(true, 12);
		licenseMainKeyField.setEditable(isEditable());
		licenseMainKeyField.setColumns(10);
		detailsPanel.add(licenseMainKeyField, "cell 2 2 4 1,grow");
		
		licenseKeyLabel = new JLabel("Klucz licencji:");
		licenseKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseKeyLabel, "cell 1 3,alignx left");
		
		licenseKeyField = new PMJTextField(true, 13);
		licenseKeyField.setEditable(isEditable());
		licenseKeyField.setColumns(10);
		detailsPanel.add(licenseKeyField, "cell 2 3 4 1,grow");
		
		licenseAssignedEmailLabel = new JLabel("Przypisane konto email:");
		licenseAssignedEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseAssignedEmailLabel, "cell 1 4,alignx left");
		
		licenseAssignedEmailField = new PMJTextField(true, 13);
		licenseAssignedEmailField.setEditable(isEditable());
		licenseAssignedEmailField.setColumns(10);
		detailsPanel.add(licenseAssignedEmailField, "cell 2 4 4 1,grow");
		
		licenseInvoiceNumberLabel = new JLabel("Powiązana faktura:");
		licenseInvoiceNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseInvoiceNumberLabel, "cell 1 5,alignx left");
		
		licenseInvoiceNumberField = new PMJTextField(true, 13);
		licenseInvoiceNumberField.setEditable(isEditable());
		licenseInvoiceNumberField.setColumns(10);
		detailsPanel.add(licenseInvoiceNumberField, "cell 2 5 4 1,grow");
		
		licensePurchaseDateLabel = new JLabel("Data zakupu:");
		licensePurchaseDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licensePurchaseDateLabel, "cell 1 6,alignx left");
		
		licensePurchaseDateField = new PMJDateChooser();
		licensePurchaseDateField.setEnabled(isEditable());
		licensePurchaseDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		licensePurchaseDateField.setCalendar(getController().getCalendarWithTodayDate());
		detailsPanel.add(licensePurchaseDateField, "cell 2 6,grow");
		
		licenseLastInstallationDateLabel = new JLabel("Data ostatniej instalacji:");
		licenseLastInstallationDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(licenseLastInstallationDateLabel, "cell 1 7,alignx left");
		
		licenseLastInstallationDateField = new PMJDateChooser();
		licenseLastInstallationDateField.setEnabled(isEditable());
		licenseLastInstallationDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		licenseLastInstallationDateField.setCalendar(getController().getCalendarWithTodayDate());
		detailsPanel.add(licenseLastInstallationDateField, "cell 2 7,grow");
		
		licenseNotesField = new JTextPane();
		licenseNotesField.setEnabled(isEditable());
		licenseNotesField.setBorder(BorderFactory.createTitledBorder("Dodatkowe informacje"));
		licenseNotesField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane licenseNotesContainer = new JScrollPane(licenseNotesField);
		licenseNotesContainer.setBorder(null);
		detailsPanel.add(licenseNotesContainer, "cell 1 10 6 1,grow");
		
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
					saveLicenseData();
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
		
		licenseSoftwareField.getCustomTextField().addKeyListener(new KeyListener() {
			
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
					getController().getManagerDAO().getLicenseServiceDAO().findSoftwareByTextAndAddItemToLicenseDetailsPanel();
				}
			}
		});
		
		licenseSoftwareField.getDictionaryButton().removeActionListener(licenseSoftwareField.getDictionaryButton().getActionListeners()[0]);
		licenseSoftwareField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().openSoftwareDictionaryWindowAndAddItem();
			}
		});

		
	}
	
	public boolean addLicenseDataToView(License license) {
		
		if(license == null) return false;
		setLicenseID(license.getLicenseID());
		
		additionalTitleHeaderLabel.setText(license.getSoftware().getSoftwareName());
		         
		licenseSoftwareField.addItem(license.getSoftware());
		licenseMainKeyField.setText(license.getLicenseMainKey());
		licenseKeyField.setText(license.getLicenseKey());
		licenseAssignedEmailField.setText(license.getAssignedEmail());
		licenseInvoiceNumberField.setText(license.getInvoiceNumber());
		licensePurchaseDateField.setCalendar(getController().getCalendarWithCustomDate(license.getPurchaseDate(), getController().getDefaultDateFormat(), false));
		licenseLastInstallationDateField.setCalendar(getController().getCalendarWithCustomDate(license.getLastInstallationDate(), getController().getDefaultDateFormat(), false));
		licenseNotesField.setText(license.getLicenseNotes());
		return true;
	}
	
	public void saveLicenseData() {
		
		License license = new License();
		
		license.setLicenseID(getLicenseID());
		license.setSoftware(licenseSoftwareField.getItem());
		license.setLicenseKey(licenseKeyField.getText());
		license.setLicenseMainKey(licenseMainKeyField.getText());
		license.setAssignedEmail(licenseAssignedEmailField.getText());
		license.setInvoiceNumber(licenseInvoiceNumberField.getText());
		license.setPurchaseDate(licensePurchaseDateField.getCustomDate());
		license.setLastInstallationDate(licenseLastInstallationDateField.getCustomDate());
		license.setLicenseNotes(licenseNotesField.getText());
		
		getController().getManagerDAO().getLicenseServiceDAO().saveData(license, getOperationType());
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(licenseSoftwareField.isEmpty());
		errors.add(licenseMainKeyField.isEmpty());
		errors.add(licenseAssignedEmailField.isEmpty());
		errors.add(licenseInvoiceNumberField.isEmpty());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja licencji" : "Szczegóły licencji";
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

	public int getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(int licenseID) {
		this.licenseID = licenseID;
	}

	public PMCustomTextFieldWithDictionary<Software> getLicenseSoftwareField() {
		return licenseSoftwareField;
	}

	public JPanel getDetailsPanel() {
		return detailsPanel;
	}

	public void setDetailsPanel(JPanel detailsPanel) {
		this.detailsPanel = detailsPanel;
	}

	
}
