package inventory.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inventory.manage.controller.Controller;
import inventory.manage.model.Department;
import inventory.manage.model.OperationType;
import inventory.manage.view.util.ButtonPanel;
import inventory.manage.view.util.PMJScrollPane;
import inventory.manage.view.util.PMJTextField;

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
public class DepartmentDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private int departmentID;
	
	private JLabel additionalTitleHeaderLabel;
	private PMJTextField departmentCodeField;
	private PMJTextField departmentNameField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Controller controller;
	
	
	public DepartmentDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setDepartmentDetailsFrame(this);
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
		
		JLabel titleHeaderLabel = new JLabel("Miejsce użytkowania");
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
		detailsPanel.setLayout(new MigLayout("", "[10px][150px][grow][grow][grow][grow][grow][10px]", "[25px][25px][25px][25px]"));
		
		JLabel deviceCodeLabel = new JLabel("Kod:");
		deviceCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceCodeLabel, "cell 1 1,alignx left");
		
		departmentCodeField = new PMJTextField(true, 13, isEditable());
		departmentCodeField.setColumns(10);
		detailsPanel.add(departmentCodeField, "cell 2 1 4 1,grow");
		
		JLabel deviceNameLabel = new JLabel("Nazwa:");
		deviceNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(deviceNameLabel, "cell 1 2,alignx left");
		
		departmentNameField = new PMJTextField(true, 13, isEditable());
		departmentNameField.setColumns(10);
		detailsPanel.add(departmentNameField, "cell 2 2 4 1,grow");
		
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
					saveDepartmentData();
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
	
	public boolean addDepartmentDataToView(Department department) {
		
		if(department == null) return false;
		setDepartmentID(department.getDepartmentID());
		
		additionalTitleHeaderLabel.setText(department.toString());
		
		departmentCodeField.setText(department.getDepartmentCode());
		departmentNameField.setText(department.getDepartmentName());
		
		return true;
	}
	
	public void saveDepartmentData() {
		
		Department department = new Department();
		
		department.setDepartmentID(getDepartmentID());
		department.setDepartmentCode(departmentCodeField.getText());
		department.setDepartmentName(departmentNameField.getText());
		
		getController().getManagerDAO().getDepartmentServiceDAO().saveData(department, getOperationType());
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(departmentCodeField.isEmpty());
		errors.add(departmentNameField.isEmpty());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja miejsca użytkowania" : "Szczegóły miejsca użytkowania";
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

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	
}
