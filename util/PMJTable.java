package kruszywo.sa.place.kartoteka.view.util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class PMJTable extends JTable{


	private static final long serialVersionUID = 7352425134966455629L;
	
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> tableSorter;
	private boolean isEditable;


	public PMJTable (Boolean isEditable) {
		super();
		this.isEditable = isEditable;
		this.setEditable(this.isEditable);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = this.getColumnModel();
	    for (int column = 0; column < this.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < this.getRowCount(); row++) {
	            TableCellRenderer renderer = this.getCellRenderer(row, column);
	            Component comp = this.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width + 10 , width);
	        }
	        if(width > 300) width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void setEditable(boolean isEditable) {
		if (!isEditable) this.setDefaultEditor(Object.class, null);
	}
	
	public void setDefaultTableModel(@SuppressWarnings("rawtypes") Class[] types) {
		DefaultTableModel tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}	
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return isEditable;
		    }
			
			
		};
		
		this.tableModel = tableModel;
		this.setModel(this.tableModel);
	}
	
	public DefaultTableModel getDefaultTableModel() {
		return this.tableModel;
	}

	public TableRowSorter<DefaultTableModel> getTableSorter() {
		return tableSorter;
	}

	public void setTableSorter(DefaultTableModel tableModel) {
		this.tableSorter = new TableRowSorter<DefaultTableModel>(tableModel);
	}
	
}
