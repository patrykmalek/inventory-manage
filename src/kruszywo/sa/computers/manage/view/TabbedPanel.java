package kruszywo.sa.computers.manage.view;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.view.util.ButtonTabComponent;

public class TabbedPanel extends JPanel{

	private static final long serialVersionUID = -6195938473783626517L;
	
	public static final String DEVICES_PANEL = "Urządzenia";
	public static final String DEVICES_TYPE_DICTIONARY_PANEL = "Typy urządzeń";
	public static final String DEPARTMENT_DICTIONARY_PANEL = "Miejsca użytkowania";
	public static final String EMPLOYEE_DICTIONARY_PANEL = "Pracownicy";
	
	private JTabbedPane tabbedPane;

	private Controller controller;
	
	public TabbedPanel(Controller controller) {
		this.controller = controller;
		this.controller.setTabbedPanel(this);
		this.createVisuals();
		this.createEventListeners();
	}

	private void createVisuals() {
		
		CardLayout panelLayout = new CardLayout();
		this.setLayout(panelLayout);
		this.setBorder(BorderFactory.createEmptyBorder());
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFocusable(false);
		
		this.add(tabbedPane);
	}
	
	private void createEventListeners() {
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent event) {
//				System.out.println(tabbedPane.getSelectedIndex());
				
			}
		});
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
	public void addTabbedPanel(String title, Component component) {
		if (tabbedPanelExist(title)) return;
		tabbedPane.add(title, component);
		int nextIndex = tabbedPane.getTabCount() - 1;
		tabbedPane.setTabComponentAt(nextIndex, new ButtonTabComponent(tabbedPane));
		tabbedPane.setSelectedIndex(nextIndex);
	}

	public boolean tabbedPanelExist(String title)  
	{
	  int tabCount = tabbedPane.getTabCount();
	  for (int i=0; i < tabCount; i++) 
	  {
	    String tabTitle = tabbedPane.getTitleAt(i);
	    if (tabTitle.equals(title)) return true;
	  }
	  return false;
	}
}
