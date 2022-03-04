package kruszywo.sa.computers.manage.view;

import java.util.List;

import javax.swing.JScrollPane;

public interface TablePanel<T> {

	void clearTable();

	void updateTable(List<T> data);
	
	void resizeTable();
	
	boolean isEmptyData(List<T> data);
	
	@SuppressWarnings("rawtypes")
	void setTableModelAndSorter(Class[] types);
	
	void setTableColumnNames(String[] columnNames);

	void createEventListeners();

	void createVisuals();
	
	void createTable();

	JScrollPane getTableContainer();

	void addRowToTable(Object[] rowData);

}
