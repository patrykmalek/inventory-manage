package kruszywo.sa.computers.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;


import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerCPU;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
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
import java.util.ArrayList;
import java.util.List;


public class ComputerCPUDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private int computerCpuID;
	
	private JLabel additionalTitleHeaderLabel;
	private PMJTextField computerCpuNameField;
	private PMJTextField computerCpuCoresNumberField;
	private PMJTextField computerCpuClockSpeedField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public ComputerCPUDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setComputerCPUDetailsFrame(this);
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
	
	private JPanel createDetailsPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBackground(SystemColor.text);
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px][grow][10px]"));
		
		JLabel computerCpuNameLabel = new JLabel("Nazwa:");
		computerCpuNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerCpuNameLabel, "cell 1 1,alignx left");
		
		computerCpuNameField = new PMJTextField(true, 13);
		computerCpuNameField.setEditable(isEditable());
		computerCpuNameField.setColumns(10);
		detailsPanel.add(computerCpuNameField, "cell 2 1 4 1,grow");
		
		JLabel computerCpuCoresNumberLabel = new JLabel("Ilość rdzeni:");
		computerCpuCoresNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerCpuCoresNumberLabel, "cell 1 2,alignx left");
		
		computerCpuCoresNumberField = new PMJTextField(true, 13);
		computerCpuCoresNumberField.setEditable(isEditable());
		computerCpuCoresNumberField.setColumns(10);
		detailsPanel.add(computerCpuCoresNumberField, "cell 2 2 4 1,grow");
		
		JLabel computerCpuClockSpeedLabel = new JLabel("Szybkość zegara:");
		computerCpuClockSpeedLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(computerCpuClockSpeedLabel, "cell 1 3,alignx left");
		
		computerCpuClockSpeedField = new PMJTextField(true, 13);
		computerCpuClockSpeedField.setEditable(isEditable());
		computerCpuClockSpeedField.setColumns(10);
		detailsPanel.add(computerCpuClockSpeedField, "cell 2 3 4 1,grow");
		
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
					saveComputerCPUData();
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
	
	public boolean addComputerCPUDataToView(ComputerCPU computerCpu) {
		
		if(computerCpu == null) return false;
		setComputerCPUID(computerCpu.getComputerCpuID());
		
		additionalTitleHeaderLabel.setText("Szczegóły");
		
		computerCpuNameField.setText(computerCpu.getComputerCpuName());
		computerCpuCoresNumberField.setText(String.valueOf(computerCpu.getComputerCpuCoresNumber()));
		computerCpuClockSpeedField.setText(String.valueOf(computerCpu.getComputerCpuClockSpeed()));
		
		return true;
	}
	
	public void saveComputerCPUData() {
		
		ComputerCPU computerCpu = new ComputerCPU();

			computerCpu.setComputerCpuID(getComputerCPUID());
		 	computerCpu.setComputerCpuName(computerCpuNameField.getText());
		 	computerCpu.setComputerCpuCoresNumber(computerCpuCoresNumberField.getInt());
		 	computerCpu.setComputerCpuClockSpeed(computerCpuClockSpeedField.getInt());

		getController().getManagerDAO().getComputerCPUServiceDAO().saveData(computerCpu, getOperationType());
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(computerCpuNameField.isEmpty());
		errors.add(!computerCpuCoresNumberField.isInteger());
		errors.add(!computerCpuClockSpeedField.isInteger());
		
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

	public int getComputerCPUID() {
		return computerCpuID;
	}

	public void setComputerCPUID(int computerCpuID) {
		this.computerCpuID = computerCpuID;
	}

	
}
