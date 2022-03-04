package kruszywo.sa.computers.manage.view;


import java.util.List;


import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Department;

public class DepartmentDictionaryTablePanel extends DictionaryTablePanel<Department> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	
	public DepartmentDictionaryTablePanel(Controller controller) {
		this.controller = controller;
		this.controller.setDepartmentDictionaryTable(this);
	}

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTable(List<Department> devices) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setButtonEventListeners() {
		// TODO Auto-generated method stub
		
	}


}