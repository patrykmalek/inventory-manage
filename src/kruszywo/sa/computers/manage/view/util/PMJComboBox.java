package kruszywo.sa.computers.manage.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.details.window.DeviceDetailsFrame;


public class PMJComboBox<T> extends JPanel {
	

	private static final long serialVersionUID = -7525085440768397157L;

	
	private JComboBox comboBox;
	private JButton dictionaryButton;
	
	public PMJComboBox () {
		super();
		this.createDefaultEventListeners();
	}

	public PMJComboBox (boolean custom) {
		super();
		if(custom) {
			this.setLayout(new BorderLayout());
//			comboBox.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
//			comboBox.setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			comboBox = new JComboBox<T>();
			comboBox.setBorder(null);
			comboBox.setBackground(SystemColor.green);
			comboBox.setEditable(true);
			comboBox.setFocusable(false);
//			comboBox.createCustomEventListeners();
			
			JButton dictionaryButton = new JButton();
			dictionaryButton.setFocusable(false);
			dictionaryButton.setMargin(new Insets(0, 0, 0, 0)); 
			dictionaryButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new DeviceDetailsFrame(new Controller(new DatabaseProvider("test")));
				}
			});
			
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
			
		   add(comboBox, BorderLayout.CENTER);
		   add(dictionaryButton, BorderLayout.EAST); 
		}
	}
	
	private Border getCustomFieldBorder(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private void createCustomEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!hasFocus()) {
					setBackground(SystemColor.inactiveCaptionBorder);
					setBorder(getCustomFieldBorder(new Color(0, 60, 110)));
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!hasFocus()) {
					setBackground(SystemColor.white);
					setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
				}
			}
			
		});
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				setBackground(SystemColor.inactiveCaption);
				setBorder(getCustomFieldBorder(new Color(0, 10, 110)));
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(SystemColor.white);
				setBorder(getCustomFieldBorder(UIManager.getColor("Button.shadow")));
			}

		});
	}
	
	private void createDefaultEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}

		});
	}


}
