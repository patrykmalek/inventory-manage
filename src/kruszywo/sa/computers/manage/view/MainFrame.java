package kruszywo.sa.computers.manage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import kruszywo.sa.computers.manage.controller.Controller;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6578553391432363839L;
	private Controller controller;
	private JPanel mainPanel;
	
	
	public MainFrame(Controller controller){

		this.controller = controller;
		this.controller.setMainFrame(this);
		this.createVisuals();
	}
	
	private void createVisuals() {
		this.setTitle("Ewidencja urządzeń");
		this.setSize(1900, 800);
		this.mainPanel = createMainPanel();
		this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		this.createListeners();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private JPanel createMainPanel() {
		JPanel mainContainerPanel = new JPanel();
		
//		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		
		JPanel headerPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel footerPanel = new JPanel();
		
		MenuBarPanel menuBarPanel = new MenuBarPanel(this.getController());
//		PropertiesPanel propertiesPanel = new PropertiesPanel(this.getController());
//		PMChartPanel chartPanel = new PMChartPanel(this.getController());
		TabbedPanel tabPanel = new TabbedPanel(this.getController());
		
//		propertiesPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
		
		
//		northPanel.setLayout(new BorderLayout(10, 10));
//		northPanel.add(employeeDetailsPanel, BorderLayout.LINE_START);
//		northPanel.add(propertiesPanel, BorderLayout.LINE_END);
//		
		centerPanel.setLayout(new BorderLayout());
//		centerPanel.add(chartPanel, BorderLayout.LINE_START);
		centerPanel.add(tabPanel, BorderLayout.CENTER);
//		centerPanel.add(propertiesPanel, BorderLayout.LINE_END);
			
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setPreferredSize(new Dimension(100, 25));
		headerPanel.add(menuBarPanel);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder());
//		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		footerPanel.setLayout(new BorderLayout(10, 10));
		footerPanel.setPreferredSize(new Dimension(100, 25));
		footerPanel.setBackground(Color.WHITE);
		
		mainContainerPanel.setLayout(new BorderLayout());
		mainContainerPanel.add(headerPanel, BorderLayout.NORTH);
		mainContainerPanel.add(mainPanel, BorderLayout.CENTER);
		mainContainerPanel.add(footerPanel, BorderLayout.SOUTH);
		
		return mainContainerPanel;
	}
	
	private void createListeners() {
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) { 	
//		    	controller.resizeWindowElements();
		    }
		});
			
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent windowEvent) {
//				controller.resizeWindowElements();
				
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				int result = JOptionPane.showOptionDialog(new JFrame(),
					    "Czy napewno chcesz wyjść z programu?",
					    "Potwierdzenie zamknięcia",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    new Object[] {"Tak","Nie"},
					    "Tak");
			
				 if(result == JOptionPane.OK_OPTION) {
					controller.getDatabaseProvider().close();
				    System.exit(0); 	
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

	public void displayWindow() {
		this.setVisible(true);
	}
}
