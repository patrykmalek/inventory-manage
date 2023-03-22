package computers.manage.view;



import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import computers.manage.controller.Controller;
public class ConfigFrame extends JDialog {
	
	private static final long serialVersionUID = -3755174073235233937L;
	private JPanel panel;
	private JTextField databasePathField;
	private JButton databaseChooserButton;
	private JButton submitButton;
	private Controller controller;
	
	public ConfigFrame(Controller controller) {
		this.controller = controller;
		this.controller.setConfigFrame(this);
		createVisuals();
		createEventListeners();
		
		this.controller.updateConfigView();
		this.setModal(true);
		this.setVisible(true);
	}
	
	private void createVisuals() {
		this.setTitle("Ustawienia");
		this.setSize(400, 225);
		this.setIconImage(new ImageIcon(getClass().getResource("/database-solid.png")).getImage());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.panel = createPanel();
		getContentPane().add(this.panel);
		this.setResizable(false);
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel headerTitle = new JLabel("Konfiguracja bazy danych");
		headerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		headerTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		headerTitle.setBounds(39, 11, 300, 30);
		panel.add(headerTitle);
		
		JLabel databasePathLabel = new JLabel("Ścieżka bazy danych:");
		databasePathLabel.setBounds(20, 60, 140, 20);
		databasePathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		databasePathLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		databasePathLabel.setToolTipText("Ścieżka bazy danych:");
		panel.add(databasePathLabel);
		
		databasePathField = new JTextField();
		databasePathField.setBounds(20, 85, 320, 20);
		databasePathField.setColumns(10);
		panel.add(databasePathField);
		
		databaseChooserButton = new JButton("...");
		databaseChooserButton.setBounds(344, 84, 30, 22);
		panel.add(databaseChooserButton);
		
		submitButton = new JButton("Zapisz");
		submitButton.setBounds(140, 150, 100, 25);
		submitButton.setFocusable(false);
		panel.add(submitButton);

		return panel;
	}
	
	private void createEventListeners() {
		
		databaseChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(databasePathField.getText()));
			      file.setMultiSelectionEnabled(true);
			      file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			      file.setFileHidingEnabled(false);
			      if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			         java.io.File f = file.getSelectedFile();
			         databasePathField.setText(f.getPath());
			      }
			}
		});
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					dispose();
			}
		});
	}
	
	private void submit() {
		if (validateField()) {
			dispose();
			updateConfig();
		}
	}
	
	private void updateConfig() {
		this.controller.updateDatabasePath(databasePathField.getText());
	}
	
	private boolean validateField() {
		
		if (databasePathField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Pole ścieżka serwera nie może być puste!", "Puste pole", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		return true;
	}
	
	public JTextField getDatabasePathField() {
		return databasePathField;
	}
}
