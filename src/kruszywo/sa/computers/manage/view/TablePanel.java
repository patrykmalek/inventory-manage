package kruszywo.sa.computers.manage.view;

import java.util.List;

import javax.swing.JScrollPane;

public interface TablePanel<T> {

	void clearTable();

	void updateTable(List<T> t);

	void createEventListeners();

	void createVisuals();

	JScrollPane getTableContainer();

}
