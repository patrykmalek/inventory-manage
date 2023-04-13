package inventory.manage.view.details.window;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inventory.manage.controller.Controller;
import inventory.manage.model.ConnectedDocument;
import inventory.manage.model.Document;
import inventory.manage.model.OperationType;
import inventory.manage.view.util.ButtonPanel;
import inventory.manage.view.util.PMCustomTextFieldWithButton;
import inventory.manage.view.util.PMJDateChooser;
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
import java.util.Date;
import java.util.List;

public class DocumentDetailsFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JLabel additionalTitleHeaderLabel;
	private JLabel documentNameLabel;
	private JLabel documentPathLabel;
	private JLabel documentDateLabel;
	

	private int documentID;
	private int assignedDeviceID;
	private int newAssignedDeviceID;
	
	private PMJTextField documentNameField;
	private PMCustomTextFieldWithButton<String> documentPathField;
	private PMJDateChooser documentDateField;
	
	private JPanel headerPanel;
	private JPanel detailsPanel;
	private JPanel footerPanel;
	
	private ButtonPanel buttonFooterPanel;
	
	private boolean editable;
	
	private OperationType operationType;
	
	private Document tempDocument;
	private Controller controller;
	
	
	public DocumentDetailsFrame(Controller controller) {
		super(controller.getMainFrame(), "Panel", true);
		this.controller = controller;
		this.controller.setDocumentDetailsFrame(this);
		createVisuals();
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
		
		JLabel titleHeaderLabel = new JLabel("Dokument");
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
		
		documentNameLabel = new JLabel("Nazwa dokumentu:");
		documentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(documentNameLabel, "cell 1 1,alignx left");
		
		documentNameField = new PMJTextField(true, 13, isEditable());
		documentNameField.setEditable(isEditable());
		documentNameField.setColumns(10);
		detailsPanel.add(documentNameField, "cell 2 1 4 1,grow");
		
		documentPathLabel = new JLabel("Ścieżka do pliku:");
		documentPathLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(documentPathLabel, "cell 1 2,alignx left");
		
		documentPathField = new PMCustomTextFieldWithButton<String>(isEditable());
		detailsPanel.add(documentPathField, "cell 2 2 4 1,grow");
		
		documentDateLabel = new JLabel("Data dokumentu:");
		documentDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		detailsPanel.add(documentDateLabel, "cell 1 3,alignx left");
		
		documentDateField = new PMJDateChooser();
		documentDateField.setEnabled(isEditable());
		documentDateField.setDateFormatString(controller.getDefaultDateFormat().toPattern());
		documentDateField.setCalendar(getController().getCalendarWithTodayDate());
		detailsPanel.add(documentDateField, "cell 2 3,grow");
		
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
					saveDocumentData();
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
		
		
		documentPathField.getButton().removeActionListener(documentPathField.getButton().getActionListeners()[0]);
		documentPathField.getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String choosenFilePath = getController().openFileChooserAndGetPath();
				if(choosenFilePath != "") documentPathField.addItem(choosenFilePath);
				if(documentNameField.getText().isEmpty()) {
					documentNameField.setText(getController().getFileNameFromFilePathWithoutExtension(choosenFilePath));
				}
			}
		});

		
	}
	
	public boolean addDocumentDataToView(Document document) {
		
		if(document == null) return false;
		setDocumentID(document.getDocumentID());
		setTempDocument(document);
		
		additionalTitleHeaderLabel.setText(document.getDocumentName());
		         
		documentNameField.setText(document.getDocumentName());
		documentPathField.addItem(document.getDocumentPath());
		documentDateField.setCalendar(getController().getCalendarWithCustomDate(document.getDocumentDate(), getController().getDefaultDateFormat(), false));
		
		return true;
	}
	
	public void saveDocumentData() {
		
		Document document = new Document();
		document.setDocumentID(getDocumentID());
		document.setDocumentName(documentNameField.getText());
		document.setDocumentDate(documentDateField.getCustomDate());

		if(getOperationType() == OperationType.INSERT) {
			document.setAddedDate(getController().getDefaultDateTimeFormat().format(new Date()));
			ConnectedDocument connectedDocument = new ConnectedDocument();
			connectedDocument.setDeviceID(assignedDeviceID);
			connectedDocument.setDeviceID(newAssignedDeviceID);
			document.setConnectedDocument(connectedDocument);
			document.setDocumentPath(documentPathField.getItem());
			document.setOriginalName(getController().getFileNameFromFilePath(document.getDocumentPath()));
		} else {
			document.setAddedDate(getTempDocument().getAddedDate());
			document.setConnectedDocument(getTempDocument().getConnectedDocument());
			document.setOriginalName(getTempDocument().getDocumentPath() != documentPathField.getItem() 
					? getController().getFileNameFromFilePath(documentPathField.getItem()) : getTempDocument().getOriginalName());
			document.setDocumentPath(getTempDocument().getDocumentPath() != documentPathField.getItem() 
					? documentPathField.getItem() : getTempDocument().getDocumentPath());
		}
		
		if(getController().getManagerDAO().getDocumentServiceDAO().checkIfDocumentAlreadyExists(document)) {
			getController().getManagerDAO().getDocumentServiceDAO().saveData(document, getOperationType());
			dispose();
		}
	}
	
	private boolean validateFields() {
		
		List<Boolean> errors = new ArrayList<>();
		
		errors.add(documentNameField.isEmpty());
		errors.add(documentPathField.isEmpty());
		
		return !errors.contains(true);
	}

	private String getCorrectTitle() {
		return (isEditable()) ? "Edycja dokumentu" : "Szczegóły dokumentu";
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

	public int getDocumentID() {
		return documentID;
	}

	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}


	public JPanel getDetailsPanel() {
		return detailsPanel;
	}

	public void setDetailsPanel(JPanel detailsPanel) {
		this.detailsPanel = detailsPanel;
	}

	public Document getTempDocument() {
		return tempDocument != null ? tempDocument : new Document();
	}

	public void setTempDocument(Document tempDocument) {
		this.tempDocument = tempDocument;
	}

	public int getAssignedDeviceID() {
		return assignedDeviceID;
	}

	public void setAssignedDeviceID(int assignedDeviceID) {
		this.assignedDeviceID = assignedDeviceID;
	}

	public int getNewAssignedDeviceID() {
		return newAssignedDeviceID;
	}

	public void setNewAssignedDeviceID(int newAssignedDeviceID) {
		this.newAssignedDeviceID = newAssignedDeviceID;
	}

	
}
