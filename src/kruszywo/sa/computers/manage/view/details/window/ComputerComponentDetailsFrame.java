package kruszywo.sa.computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;


import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerCPU;
import kruszywo.sa.computers.manage.model.ComputerComponent;
import kruszywo.sa.computers.manage.model.ComputerMassStorage;
import kruszywo.sa.computers.manage.model.ComputerRAM;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.PMCustomTextFieldWithDictionary;
import kruszywo.sa.computers.manage.view.util.PMJScrollPane;

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


public class ComputerComponentDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private int computerComponentID;
	
	private JLabel additionalTitleHeaderLabel;
	private JLabel computerComponentDeviceLabel;
	
	private PMCustomTextFieldWithDictionary<Device> computerComponentDeviceField;
	private PMCustomTextFieldWithDictionary<ComputerCPU> computerComponentCPUField;
	private PMCustomTextFieldWithDictionary<ComputerRAM> computerComponentRAMField;
	private PMCustomTextFieldWithDictionary<ComputerMassStorage> computerComponentMassStorageFirstField;
	private PMCustomTextFieldWithDictionary<ComputerMassStorage> computerComponentMassStorageSecondField;
	private PMCustomTextFieldWithDictionary<ComputerMassStorage> computerComponentMassStorageThirdField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	private boolean addedFromDevice;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public ComputerComponentDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setComputerComponentDetailsFrame(this);
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
		setSize(800, 600);
		createPanels();
		getContentPane().add(this.headerPanel, BorderLayout.NORTH);
		getContentPane().add(new PMJScrollPane(this.detailsPanel), BorderLayout.CENTER);
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
		
		JLabel titleHeaderLabel = new JLabel("Procesor");
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
		
		computerComponentDeviceField = new PMCustomTextFieldWithDictionary<Device>();
		computerComponentDeviceField.setEditable(isEditable());
		detailsPanel.add(computerComponentDeviceField, "cell 2 1 4 1,grow");
		
		JLabel computerComponentCPULabel= new JLabel("Procesor:");
		computerComponentCPULabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentCPULabel, "cell 1 2,alignx left");
		
		computerComponentCPUField = new PMCustomTextFieldWithDictionary<ComputerCPU>();
		computerComponentCPUField.setEditable(isEditable());
		detailsPanel.add(computerComponentCPUField, "cell 2 2 4 1,grow");
		
		JLabel computerComponentRAMLabel = new JLabel("Pamięć RAM:");
		computerComponentRAMLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentRAMLabel, "cell 1 3,alignx left");
		
		computerComponentRAMField = new PMCustomTextFieldWithDictionary<ComputerRAM>();
		computerComponentRAMField.setEditable(isEditable());
		detailsPanel.add(computerComponentRAMField, "cell 2 3 4 1,grow");
		
		JLabel computerComponentMassStorageFirstLabel = new JLabel("Pamięć masowa - 1:");
		computerComponentMassStorageFirstLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentMassStorageFirstLabel, "cell 1 4,alignx left");
		
		computerComponentMassStorageFirstField = new PMCustomTextFieldWithDictionary<ComputerMassStorage>();
		computerComponentMassStorageFirstField.setEditable(isEditable());
		detailsPanel.add(computerComponentMassStorageFirstField, "cell 2 4 4 1,grow");
		
		JLabel computerComponentMassStorageSecondLabel = new JLabel("Pamięć masowa - 2:");
		computerComponentMassStorageSecondLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentMassStorageSecondLabel, "cell 1 5,alignx left");
		
		computerComponentMassStorageSecondField = new PMCustomTextFieldWithDictionary<ComputerMassStorage>();
		computerComponentMassStorageSecondField.setEditable(isEditable());
		detailsPanel.add(computerComponentMassStorageSecondField, "cell 2 5 4 1,grow");
		
		JLabel computerComponentMassStorageThirdLabel = new JLabel("Pamięć masowa - 3:");
		computerComponentMassStorageThirdLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerComponentMassStorageThirdLabel, "cell 1 6,alignx left");
		
		computerComponentMassStorageThirdField = new PMCustomTextFieldWithDictionary<ComputerMassStorage>();
		computerComponentMassStorageThirdField.setEditable(isEditable());
		detailsPanel.add(computerComponentMassStorageThirdField, "cell 2 6 4 1,grow");
		
		
		
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
					saveComputerComponentData();
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
		
		computerComponentDeviceField.getDictionaryButton().removeActionListener(computerComponentDeviceField.getDictionaryButton().getActionListeners()[0]);
		computerComponentDeviceField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openDeviceDictionaryWindowAndAddItem();
			}
		});
		computerComponentCPUField.getDictionaryButton().removeActionListener(computerComponentCPUField.getDictionaryButton().getActionListeners()[0]);
		computerComponentCPUField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openCPUDictionaryWindowAndAddItem();
			}
		});
		computerComponentRAMField.getDictionaryButton().removeActionListener(computerComponentRAMField.getDictionaryButton().getActionListeners()[0]);
		computerComponentRAMField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openRAMDictionaryWindowAndAddItem();
			}
		});
		computerComponentMassStorageFirstField.getDictionaryButton().removeActionListener(computerComponentMassStorageFirstField.getDictionaryButton().getActionListeners()[0]);
		computerComponentMassStorageFirstField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openMassStorageDictionaryWindowAndAddItem(computerComponentMassStorageFirstField);
			}
		});
		computerComponentMassStorageSecondField.getDictionaryButton().removeActionListener(computerComponentMassStorageSecondField.getDictionaryButton().getActionListeners()[0]);
		computerComponentMassStorageSecondField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openMassStorageDictionaryWindowAndAddItem(computerComponentMassStorageSecondField);
			}
		});
		computerComponentMassStorageThirdField.getDictionaryButton().removeActionListener(computerComponentMassStorageThirdField.getDictionaryButton().getActionListeners()[0]);
		computerComponentMassStorageThirdField.getDictionaryButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openMassStorageDictionaryWindowAndAddItem(computerComponentMassStorageThirdField);
			}
		});
	}
	
	public boolean addComputerComponentDataToView(ComputerComponent computerComponent) {
		
		if(computerComponent == null) return false;
		setComputerComponentID(computerComponent.getComputerComponentID());
		
		computerComponentDeviceField.addItem(computerComponent.getDevice());
		computerComponentCPUField.addItem(computerComponent.getComputerCPU());
		computerComponentRAMField.addItem(computerComponent.getComputerRAM());
		computerComponentMassStorageFirstField.addItem(computerComponent.getComputerMassStorageFirst());
		computerComponentMassStorageSecondField.addItem(computerComponent.getComputerMassStorageSecond());
		computerComponentMassStorageThirdField.addItem(computerComponent.getComputerMassStorageThird());
		
		return true;
	}
	
	public void saveComputerComponentData() {
		
		ComputerComponent computerComponent = new ComputerComponent();

			computerComponent.setComputerComponentID(getComputerComponentID());
		 	computerComponent.setDevice(computerComponentDeviceField.getItem());
		 	computerComponent.setComputerCPU(computerComponentCPUField.getItem());
		 	computerComponent.setComputerRAM(computerComponentRAMField.getItem());
		 	computerComponent.setComputerMassStorageFirst(computerComponentMassStorageFirstField.getItem());
		 	computerComponent.setComputerMassStorageSecond(computerComponentMassStorageSecondField.getItem());
		 	computerComponent.setComputerMassStorageThird(computerComponentMassStorageThirdField.getItem());
		 	
		getController().getManagerDAO().getComputerComponentServiceDAO().saveData(computerComponent, getOperationType());
	}
	
	public void saveComputerComponentData(Device device) {
		
		ComputerComponent computerComponent = new ComputerComponent();

			computerComponent.setComputerComponentID(getComputerComponentID());
		 	computerComponent.setDevice(device);
		 	computerComponent.setComputerCPU(computerComponentCPUField.getItem());
		 	computerComponent.setComputerRAM(computerComponentRAMField.getItem());
		 	computerComponent.setComputerMassStorageFirst(computerComponentMassStorageFirstField.getItem());
		 	computerComponent.setComputerMassStorageSecond(computerComponentMassStorageSecondField.getItem());
		 	computerComponent.setComputerMassStorageThird(computerComponentMassStorageThirdField.getItem());
		 	
		getController().getManagerDAO().getComputerComponentServiceDAO().saveData(computerComponent, getOperationType());
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(computerComponentDeviceField.isEmpty());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja szczegółów procesora" : "Szczegóły procesora";
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

	public int getComputerComponentID() {
		return computerComponentID;
	}

	public void setComputerComponentID(int computerComponentID) {
		this.computerComponentID = computerComponentID;
	}

	public PMCustomTextFieldWithDictionary<Device> getComputerComponentDeviceField() {
		return computerComponentDeviceField;
	}

	public PMCustomTextFieldWithDictionary<ComputerCPU> getComputerComponentCPUField() {
		return computerComponentCPUField;
	}

	public PMCustomTextFieldWithDictionary<ComputerRAM> getComputerComponentRAMField() {
		return computerComponentRAMField;
	}

	public PMCustomTextFieldWithDictionary<ComputerMassStorage> getComputerComponentMassStorageFirstField() {
		return computerComponentMassStorageFirstField;
	}

	public PMCustomTextFieldWithDictionary<ComputerMassStorage> getComputerComponentMassStorageSecondField() {
		return computerComponentMassStorageSecondField;
	}

	public PMCustomTextFieldWithDictionary<ComputerMassStorage> getComputerComponentMassStorageThirdField() {
		return computerComponentMassStorageThirdField;
	}

	public JPanel getDetailsPanel() {
		return detailsPanel;
	}

	public boolean isAddedFromDevice() {
		return addedFromDevice;
	}

	public void setAddedFromDevice(boolean addedFromDevice) {
		computerComponentDeviceLabel.setVisible(false);
		computerComponentDeviceField.setVisible(false);
		this.addedFromDevice = addedFromDevice;
	}

	
}
