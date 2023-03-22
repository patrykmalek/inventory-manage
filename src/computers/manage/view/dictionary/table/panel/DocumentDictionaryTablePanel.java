package computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import computers.manage.controller.Controller;
import computers.manage.model.Document;


public class DocumentDictionaryTablePanel extends DictionaryTablePanel<Document> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	
	private JButton showDocumentButton;
	

	public DocumentDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setDocumentDictionaryTable(this);
		this.createTable();
		
		showDocumentButton = new JButton();
		getButtonPanel().addAttachmentButton(showDocumentButton);
		
		this.setButtonEventListeners();
		setPanelTitle("Powiązane dokumenty");
		
		
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.util.Date.class,
				java.util.Date.class
		});
		this.setTableColumnNames(new String[] { 
				"ID Dokumentu",
				"Nazwa dokumentu",
				"Nazwa pliku - Źródło",
				"Ścieżka pliku",
				"Data dokumentu",
				"Data dodania",
		});
		getTableColumnManager().hideColumn(3);
	}

	@Override
	public void updateTable(List<Document> documents) {
		clearTable();
		if(isEmptyData(documents)) return;
		
		for( Document document : documents){
			addRowToTable(new Object[] {document.getDocumentID(), document.getDocumentName(), document.getOriginalName(), document.getDocumentPath(), document.getDocumentDate(), document.getAddedDate()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDocumentServiceDAO().openDocumentWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDocumentServiceDAO().openDocumentWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDocumentServiceDAO().deleteDocumentWithPrompt(getIdFromTable());
			}
		});
		
		showDocumentButton = getButtonPanel().getButtons().get(4);
		showDocumentButton.removeActionListener(showDocumentButton.getActionListeners()[0]);
		showDocumentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
					updateTable(getController().getManagerDAO().getDocumentDAO().getAll());
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


}
