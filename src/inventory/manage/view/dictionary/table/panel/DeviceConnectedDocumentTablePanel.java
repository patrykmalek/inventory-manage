package inventory.manage.view.dictionary.table.panel;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import inventory.manage.controller.Controller;
import inventory.manage.model.Device;
import inventory.manage.model.Document;
import inventory.manage.model.OperationType;


public class DeviceConnectedDocumentTablePanel extends DictionaryTablePanel<Document> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	
	private Device device;
	private JButton insertButton;
	private JButton assignButton;
	private JButton detachButton;
	private JButton attachmentButton;
	
	private OperationType operationType;
	private boolean editable;
	
	
	public DeviceConnectedDocumentTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setDeviceConnectedDocumentTablePanel(this);
	}
	
	public void createPanel() {
		this.createTable();
		this.createCustomButton();
		this.setButtonEventListeners();
		setPanelTitle("Powiązane dokumenty");
		getTableContainer().setMinimumSize(new Dimension(600, 150));
	}
	
	private void createCustomButton() {
		
		getButtonPanel().removeAllButtons();
		
		insertButton = new JButton();
		insertButton.setEnabled(isEditable());
		
		assignButton = new JButton();
		assignButton.setEnabled(isEditable());
		
		detachButton = new JButton();
		detachButton.setEnabled(isEditable());
		
		attachmentButton = new JButton();
		attachmentButton.setEnabled(true);
		
		getButtonPanel().addInsertButton(insertButton);
		getButtonPanel().addAtachButton(assignButton);
		getButtonPanel().addDetachButton(detachButton);
		getButtonPanel().addAttachmentButton(attachmentButton);
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.util.Date.class,
				java.util.Date.class
		});
		this.setTableColumnNames(new String[] { 
				"ID powiązanego dokumentu",
				"ID powiązania",
				"Nazwa dokumentu",
				"Nazwa pliku",
				"Ścieżka pliku",
				"Data dokumentu",
				"Data dodania",
		});
		getTableColumnManager().hideColumn(1);
		getTableColumnManager().hideColumn(4);
	}

	@Override
	public void updateTable(List<Document> documents) {
		clearTable();
		if(isEmptyData(documents)) return;
		
		for( Document document : documents){
			addRowToTable(new Object[] {document.getDocumentID(), document.getConnectedDocument().getConnectedDocumentID(), document.getDocumentName(), document.getOriginalName(), document.getDocumentPath(), document.getDocumentDate(), document.getAddedDate()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		
		insertButton = getButtonPanel().getButtons().get(0);
		insertButton.removeActionListener(insertButton.getActionListeners()[0]);
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDocumentServiceDAO().openDocumentWindowToAddNewWithAssign(getDevice().getDeviceID());
			}
		});
		
		
		assignButton = getButtonPanel().getButtons().get(1);
		assignButton.removeActionListener(assignButton.getActionListeners()[0]);
		assignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDocumentServiceDAO().openAssignDocumentToDevice(getDevice());
				updateTable(getController().getManagerDAO().getDocumentDAO().getAllByDevice(getDevice().getDeviceID()));
			}
		});
		detachButton = getButtonPanel().getButtons().get(2);
		detachButton.removeActionListener(detachButton.getActionListeners()[0]);
		detachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					getController().getManagerDAO().getDocumentServiceDAO().deleteAssignDocumentToDevice(getIdFromTable(), getDevice().getDeviceID());
					updateTable(getController().getManagerDAO().getDocumentDAO().getAllByDevice(getDevice().getDeviceID()));
			}
		});
		
		attachmentButton = getButtonPanel().getButtons().get(3);
		attachmentButton.removeActionListener(attachmentButton.getActionListeners()[0]);
		attachmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDocumentServiceDAO().showDocumentFile(getIdFromTable());
			}
		});
		
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getDocumentServiceDAO().openDocumentWindowToOnlyShowDetails(getIdFromTable());
				}
			}
		});
		getTable().addKeyListener(new KeyListener() {
			
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
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					
				}
			}
		});
		
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		setEditable(operationType.isWindowEditable());
		this.operationType = operationType;
	}
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}


}
