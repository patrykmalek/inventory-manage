package computers.manage.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import computers.manage.controller.Controller;


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
		this.setTitle("Ewidencja urządzeń - InventoryPass");
		this.setIconImage(new ImageIcon(getClass().getResource("/industry-solid-25.png")).getImage());
		this.setSize(1600, 800);
		this.mainPanel = createMainPanel();
		this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		this.createListeners();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private JPanel createMainPanel() {
		JPanel mainContainerPanel = new JPanel();
		
		JPanel centerPanel = new JPanel();
		
		JPanel headerPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel footerPanel = new FooterPanel(controller);
		
		MenuBarPanel menuBarPanel = new MenuBarPanel(this.getController());
		TabbedPanel tabPanel = new TabbedPanel(this.getController());
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(tabPanel, BorderLayout.CENTER);
			
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setPreferredSize(new Dimension(100, 25));
		headerPanel.add(menuBarPanel);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder());
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainContainerPanel.setLayout(new BorderLayout());
		mainContainerPanel.add(headerPanel, BorderLayout.NORTH);
		mainContainerPanel.add(mainPanel, BorderLayout.CENTER);
		mainContainerPanel.add(footerPanel, BorderLayout.SOUTH);
		
		return mainContainerPanel;
	}
	
	private void createListeners() {		
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
