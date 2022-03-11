package kruszywo.sa.computers.manage.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.details.window.DeviceDetailsFrame;
import kruszywo.sa.computers.manage.view.util.PMJComboBox;
import kruszywo.sa.computers.manage.view.util.PMJTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JScrollBar;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;

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
		panel.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[][][][][][grow][]"));
		
		
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
		dictionaryButton.setIcon(new ImageIcon(getClass().getResource("/bars-solid.png")));
		

		
		PMJTextField customTextField_1 = new PMJTextField(true, 13);

		JPanel test = new JPanel();
		test.setLayout(new BorderLayout());

		
		
		JList list = new JList();

		JScrollPane listContainer = new JScrollPane(list);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				customTextField_1.setText((String) list.getSelectedValue());
				list.setVisible(false);
				listContainer.setVisible(false);
				panel.revalidate();
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
		
		listContainer.setPreferredSize(new Dimension(customTextField_1.getWidth()-100, 150));
		dictionaryButton.setPreferredSize(new Dimension(20, customTextField_1.getHeight()));
		
		test.add(customTextField_1, BorderLayout.CENTER);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(customTextField_1, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
//		JComboBox comboBox = new JComboBox();
//		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SSD", "HDD", "Optic"}));
//		comboBox.setBackground(Color.white);
////		comboBox.setFocusable(false);
//		comboBox.setEditable(true);
//		
//		ComboBoxEditor comboBoxEditor = comboBox.getEditor();
//		Component editor = comboBoxEditor.getEditorComponent();
//		if( editor instanceof JTextField ) {
//			JTextField textField = (JTextField) editor;
//
//	
//			textField.setBorder( BorderFactory.createMatteBorder(0, 0, 3, 0, Color.red));
//		}
//		
//		for (int i = 0; i < comboBox.getComponentCount(); i++) 
//		{
////		    if (comboBox.getComponent(i) instanceof JComponent) {
////		        ((JComponent) comboBox.getComponent(i)).setBorder(new EmptyBorder(0,0,0,0));
////		    }
//
//		    if (comboBox.getComponent(i) instanceof AbstractButton) {
//		        ((AbstractButton) comboBox.getComponent(i)).setBorderPainted(true);
//		    	((AbstractButton) comboBox.getComponent(i)).setBorder(BorderFactory.createMatteBorder(5, 10, 5, 10, Color.red));
//		    }
//		}
		PMJComboBox<String> comboBox = new PMJComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SSD", "HDD", "Optic"}));
		panel.add(comboBox, "cell 1 1 4 1,growx");
		
		
		PMJTextField testField = new PMJTextField(true, 13);
//		panel.add(testField, "cell 1 4 4 1,growx");
		popupMenu.add(mntmNewMenuItem);
		
		
		
		test.add(dictionaryButton, BorderLayout.EAST);
		
		
		test.add(listContainer, BorderLayout.SOUTH);
		
		inputPanel.add(test, BorderLayout.CENTER);
		
		
//		panel.add(inputPanel, "cell 1 5 4 1,growx");
		
		
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

		list.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
				int indexMouse = list.locationToIndex(e.getPoint());
				
				list.setCellRenderer(new DefaultListCellRenderer() {
				     @Override
				     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				         Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				         	if(index == indexMouse) {
				         		c.setBackground(SystemColor.inactiveCaptionBorder);
				         		c.setForeground(Color.black);
				         	} else {
				         		c.setBackground(Color.white);
				         		c.setForeground(Color.black);
				         	}
				             
				       
				         return c;
				     }
				});
			}
		});
	
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws ParseException, IOException {
		
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
//			insets.top = -1;
//			UIManager.put("TabbedPane.contentBorderInsets", insets);
//		} catch (ClassNotFoundException | InstantiationException
//				| IllegalAccessException | UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//			return;
//		} 

		
		javax.swing.SwingUtilities.invokeLater(new Runnable()  {
			public void run() {
				Test test = new Test();
				
				test.setVisible(true);
			}
		});
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}