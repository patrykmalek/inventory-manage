package computers.manage.view;



import javax.swing.JPanel;
import javax.swing.WindowConstants;


import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class AboutFrame extends JDialog {
	
	private static final long serialVersionUID = -3755174073235233937L;
	private JPanel panel;
	
	public AboutFrame() {
		createVisuals();
		this.setModal(true);
		this.setVisible(true);
	}
	
	private void createVisuals() {
		this.setTitle("O programie");
		this.setSize(400, 500);
		this.setIconImage(new ImageIcon(getClass().getResource("/info-solid.png")).getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.panel = createPanel();
		getContentPane().add(this.panel);
		this.setResizable(false);
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel headerTitle = new JLabel("Ewidencja urządzeń - InventoryPass");
		headerTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		headerTitle.setBounds(10, 11, 364, 15);
		panel.add(headerTitle);
		
		JLabel lblVersion = new JLabel("Version 1.0.10");
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVersion.setBounds(10, 26, 364, 15);
		panel.add(lblVersion);
		
		JLabel lblCopyrightPatryk = new JLabel("Copyright © 2021-2022 Patryk Małek");
		lblCopyrightPatryk.setVerticalAlignment(SwingConstants.TOP);
		lblCopyrightPatryk.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCopyrightPatryk.setBounds(10, 62, 364, 15);
		panel.add(lblCopyrightPatryk);
		
		JLabel lblCopyright = new JLabel("Ewidencja urządzeń - InventoryPass is Open Source Software");
		lblCopyright.setVerticalAlignment(SwingConstants.TOP);
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCopyright.setBounds(10, 81, 364, 15);
		panel.add(lblCopyright);
		
		JLabel lblNewLabel = new JLabel("MIT License");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(10, 110, 165, 15);
		panel.add(lblNewLabel);
		
		JLabel lblCopyright_1 = new JLabel("<html>Permission is hereby granted, free of charge, to any person obtaining a copy\r\n"
				+ "of this software and associated documentation files (the \"Software\"), to deal\r\n"
				+ "in the Software without restriction, including without limitation the rights\r\n"
				+ "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\r\n"
				+ "copies of the Software, and to permit persons to whom the Software is\r\n"
				+ "furnished to do so, subject to the following conditions:</html>");
		lblCopyright_1.setVerticalAlignment(SwingConstants.TOP);
		lblCopyright_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCopyright_1.setBounds(10, 136, 364, 86);
		panel.add(lblCopyright_1);
		
		JLabel lblNewLabel_1 = new JLabel("<html>The above copyright notice and this permission notice shall be included in all\r\n"
				+ "copies or substantial portions of the Software.</html>");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setBounds(10, 233, 364, 28);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("<html>THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\r\n"
				+ "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\r\n"
				+ "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\r\n"
				+ "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\r\n"
				+ "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\r\n"
				+ "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\r\n"
				+ "SOFTWARE.</html>");
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1.setBounds(10, 272, 364, 178);
		panel.add(lblNewLabel_1_1);
		
		return panel;
	}
}
