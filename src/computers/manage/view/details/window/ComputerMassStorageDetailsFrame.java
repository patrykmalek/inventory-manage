package computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import computers.manage.controller.Controller;
import computers.manage.model.ComputerMassStorage;
import computers.manage.model.OperationType;
import computers.manage.view.util.ButtonPanel;
import computers.manage.view.util.PMJComboBox;
import computers.manage.view.util.PMJScrollPane;
import computers.manage.view.util.PMJTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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


public class ComputerMassStorageDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private int computerMassStorageID;
	
	private JLabel additionalTitleHeaderLabel;
	private PMJTextField computerMassStorageNameField;
	private PMJTextField computerMassStorageSerialNumberField;
	private PMJComboBox<String> computerMassStorageTypeField;
	private PMJTextField computerMassStorageCapacityField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public ComputerMassStorageDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setComputerMassStorageDetailsFrame(this);
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
		setSize(800, 400);
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
		
		JLabel titleHeaderLabel = new JLabel("Pamięć masowa");
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
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][25px][grow][10px]"));
		
		JLabel computerMassStorageNameLabel = new JLabel("Nazwa:");
		computerMassStorageNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerMassStorageNameLabel, "cell 1 1,alignx left");
		
		computerMassStorageNameField = new PMJTextField(true, 13, isEditable());
		computerMassStorageNameField.setColumns(10);
		detailsPanel.add(computerMassStorageNameField, "cell 2 1 4 1,grow");
		
		JLabel computerMassStorageSerialNumberLabel = new JLabel("Numer seryjny:");
		computerMassStorageSerialNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerMassStorageSerialNumberLabel, "cell 1 2,alignx left");
		
		computerMassStorageSerialNumberField = new PMJTextField(true, 13, isEditable());
		computerMassStorageSerialNumberField.setColumns(10);
		detailsPanel.add(computerMassStorageSerialNumberField, "cell 2 2 4 1,grow");
		
		JLabel computerMassStorageTypeLabel = new JLabel("Typ pamięci masowej:");
		computerMassStorageTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerMassStorageTypeLabel, "cell 1 3,alignx left");
		
		computerMassStorageTypeField = new PMJComboBox<String>();
		computerMassStorageTypeField.setEditable(isEditable());
		computerMassStorageTypeField.setEnabled(isEditable());
		computerMassStorageTypeField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		DefaultComboBoxModel<String> computerMassStorageTypeModel = new DefaultComboBoxModel<>();
		
		
		computerMassStorageTypeModel.addAll(getController().getManagerDAO().getComputerMassStorageDAO().getMassStorageTypes());
		computerMassStorageTypeField.setModel(computerMassStorageTypeModel);
		detailsPanel.add(computerMassStorageTypeField, "cell 2 3 4 1,grow");
		
		JLabel computerMassStorageCapacityLabel = new JLabel("Pojemność pamięci (MB):");
		computerMassStorageCapacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerMassStorageCapacityLabel, "cell 1 4,alignx left");
		
		computerMassStorageCapacityField = new PMJTextField(true, 13, isEditable());
		computerMassStorageCapacityField.setColumns(10);
		detailsPanel.add(computerMassStorageCapacityField, "cell 2 4 4 1,grow");
		
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
					saveComputerMassStorageData();
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
		
	}
	
	public boolean addComputerMassStorageDataToView(ComputerMassStorage computerMassStorage) {
		
		if(computerMassStorage == null) return false;
		setComputerMassStorageID(computerMassStorage.getMassStorageID());
		
		additionalTitleHeaderLabel.setText("Szczegóły");
		
		computerMassStorageNameField.setText(computerMassStorage.getMassStorageName());
		computerMassStorageSerialNumberField.setText(computerMassStorage.getMassStorageSerialNumber());
		computerMassStorageTypeField.setSelectedItem(computerMassStorage.getMassStorageType());
		computerMassStorageCapacityField.setText(String.valueOf(computerMassStorage.getMassStorageCapacityMB()));
		
		return true;
	}
	
	public void saveComputerMassStorageData() {
		
		ComputerMassStorage computerMassStorage = new ComputerMassStorage();

			computerMassStorage.setMassStorageID(getComputerMassStorageID());
			computerMassStorage.setMassStorageName(computerMassStorageNameField.getText());
		 	computerMassStorage.setMassStorageType((String) computerMassStorageTypeField.getSelectedItem());
		 	computerMassStorage.setMassStorageSerialNumber(computerMassStorageSerialNumberField.getText());
		 	computerMassStorage.setMassStorageCapacityMB(computerMassStorageCapacityField.getInt());

		getController().getManagerDAO().getComputerMassStorageServiceDAO().saveData(computerMassStorage, getOperationType());
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(computerMassStorageNameField.isEmpty());
		errors.add(computerMassStorageTypeField.isEmpty());
		errors.add(!computerMassStorageCapacityField.isInteger());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja szczegółów pamięci masowej" : "Szczegóły pamięci masowej";
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

	public int getComputerMassStorageID() {
		return computerMassStorageID;
	}

	public void setComputerMassStorageID(int computerMassStorageID) {
		this.computerMassStorageID = computerMassStorageID;
	}

	
}
