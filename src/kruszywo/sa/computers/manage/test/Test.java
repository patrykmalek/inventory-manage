package kruszywo.sa.computers.manage.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.device.DeviceDetailsFrame;
import kruszywo.sa.computers.manage.view.util.PMJComboBox;
import kruszywo.sa.computers.manage.view.util.PMJTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Test extends JFrame {

	private static final long serialVersionUID = 6578553391432363839L;
	
	
	public Test(){
		this.createVisuals();
		this.addPanel();
	}
	
	private void createVisuals() {
		this.setTitle("Testowe okno");
		this.setIconImage(new ImageIcon(getClass().getResource("/industry-solid-25.png")).getImage());
		this.setSize(300, 500);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	
	private void addPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[][][][][][][grow][]"));
		
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		
		JButton dictionaryButton = new JButton();
		dictionaryButton.setFocusable(false);
		dictionaryButton.setMargin(new Insets(0, 0, 0, 0)); 
//		dictionaryButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(new JPanel(), "Test");
//			}
//		});
		
		dictionaryButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				dictionaryButton.setBackground(SystemColor.inactiveCaption );
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				dictionaryButton.setBackground(Color.WHITE);
			}
		});
		dictionaryButton.setContentAreaFilled(false);
		dictionaryButton.setBackground(Color.WHITE);
		dictionaryButton.setContentAreaFilled(false);
		dictionaryButton.setOpaque(true);
		dictionaryButton.setFocusPainted(false);
		dictionaryButton.setIcon(new ImageIcon("C:\\Users\\Patryk\\Documents\\EclipseWorkspace\\Java\\ComputersManage\\resources\\icon\\bars-solid.png"));
		

		
		PMJTextField customTextField_1 = new PMJTextField(true, 13);

		
		inputPanel.add(customTextField_1, BorderLayout.CENTER);
		inputPanel.add(dictionaryButton, BorderLayout.EAST);
		
		panel.add(inputPanel, "cell 1 5 4 1,growx");
		
		
		JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				System.out.println(list.getSelectedValue());
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"test_1", "test_2", "test_3", "test_4", "test_5", "test_6", "test_7", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8", "test_8"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane listContainer = new JScrollPane(list);
		listContainer.setPreferredSize(new Dimension(100, 150));
		inputPanel.add(listContainer, BorderLayout.SOUTH);
		list.setVisible(false);
		listContainer.setVisible(false);
	
		
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		
		dictionaryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (listContainer.isVisible()) {
					listContainer.setVisible(false);
					list.setVisible(false);
				} else {
					listContainer.setVisible(true);
					list.setVisible(true);
				}
				
				panel.revalidate();
			}
		});
		
		
	
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws ParseException, IOException {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
			insets.top = -1;
			UIManager.put("TabbedPane.contentBorderInsets", insets);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			return;
		} 

		
		javax.swing.SwingUtilities.invokeLater(new Runnable()  {
			public void run() {
				Test test = new Test();
				
				test.setVisible(true);
			}
		});
	}
	
}