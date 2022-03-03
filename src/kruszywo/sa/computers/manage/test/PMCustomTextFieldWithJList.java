package kruszywo.sa.computers.manage.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.BoxLayout;

public class PMCustomTextFieldWithJList extends JPanel {


	private static final long serialVersionUID = -1190813197642099916L;

	private JButton dictionaryButton;
	private ImageIcon dictionaryButtonIcon;
	private ImageIcon dictionaryButtonIconActive;
	
	private JTextField customTextField;
	private Border customTextFieldBorder;
	
	private int DEFAULT_FONT_SIZE = 13;
	
	private final Color customTextFieldColor = SystemColor.white;
	private final Color customTextFieldColorActive = SystemColor.inactiveCaption;
	private final Color customTextFieldColorHover = SystemColor.inactiveCaptionBorder;
	
	private final Color customBorderColor = UIManager.getColor("Button.shadow");
	private final Color customBorderColorActive = new Color(0, 10, 110);
	private final Color customBorderColorHover = new Color(0, 60, 110);
	
	
	
	
	public static void main(String[] args) {
		
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
		
		PMCustomTextFieldWithJList textField = new PMCustomTextFieldWithJList();
		
//		textField.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		
		JPanel panel = new JPanel();
		
//		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.green);
		
		panel.add(textField);
		
		JFrame frame = new JFrame();
		frame.setTitle("Testowe okno");
		frame.setIconImage(new ImageIcon("C:\\Users\\pmalek\\eclipse\\Java\\ComputersManage\\resources\\icon\\industry-solid-25.png").getImage());
		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(textField);
		
		frame.setVisible(true);
	}
	
	public PMCustomTextFieldWithJList() {
		createVisuals();
	}


	private void createVisuals() {
		setDictionaryButtonIcon(createDictionaryButtonIcon());
		setDictionaryButtonIconActive(createDictionaryButtonIconActive());
		setDictionaryButton(createDictionaryButton());
		
		setCustomTextField(createCustomTextField());
		setCustomTextFieldBorder(createCustomBorderForTextField(getCustomBorderColor()));

		JPanel inputPanel = createInputPanel();

		add(inputPanel, BorderLayout.CENTER);
	}
	
	private JPanel createInputPanel() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputPanel.setBackground(Color.white);
		inputPanel.add(getCustomTextField());
		inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		inputPanel.add(getDictionaryButton());
		
		return inputPanel;
	}
	
	private JTextField createCustomTextField() {
		JTextField textField = new JTextField();
		
		textField.setFont(new Font("Tahoma", Font.PLAIN, getFontSize()));
		textField.setBorder(createCustomBorderForTextField(customBorderColor));
		textField.setBackground(SystemColor.white);
		textField.setPreferredSize(new Dimension(200, 20));
		textField.setMinimumSize(new Dimension(100, 20));
		textField.setMaximumSize(new Dimension(600, 20));
		
		
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!getCustomTextField().hasFocus()) {
					setTextFieldHover();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!getCustomTextField().hasFocus()) {
					setTextFieldDefault();
				}
			}
			
		});
		
		textField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				setTextFieldActive();
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				setTextFieldDefault();
			}

		});
		
		return textField;
	}
	
	private Border createCustomBorderForTextField(Color color) {
		Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        
        return new CompoundBorder(matteBorder, emptyBorder);
	}
	
	private ImageIcon createDictionaryButtonIcon() {
		return new ImageIcon(getClass().getResource("/bars-solid-default-20.png"));
	}
	
	private ImageIcon createDictionaryButtonIconActive() {
		return new ImageIcon(getClass().getResource("/bars-solid-hover-20.png"));
	}
	
	private JButton createDictionaryButton() {
		JButton dictionaryButton = new JButton();
		
		dictionaryButton.setFocusable(false);
		dictionaryButton.setMargin(new Insets(0, 0, 0, 0)); 
		dictionaryButton.setContentAreaFilled(false);
		dictionaryButton.setBackground(Color.WHITE);
		dictionaryButton.setContentAreaFilled(false);
		dictionaryButton.setOpaque(true);
		dictionaryButton.setFocusPainted(false);
		dictionaryButton.setIcon(getDictionaryButtonIcon());
		dictionaryButton.setBorder(null);
		dictionaryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JPanel(), "Przycisk nie ma jeszcze podpiętej akcji.");
			}
		});
		dictionaryButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				dictionaryButton.setIcon(getDictionaryButtonIconActive());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				dictionaryButton.setIcon(getDictionaryButtonIcon());
			}
		});
		
		return dictionaryButton;
	}
	
	
	private void setTextFieldDefault() {
		getCustomTextField().setBackground(getCustomTextFieldColor());
		getCustomTextField().setBorder(createCustomBorderForTextField(getCustomBorderColor()));
	}
	
	
	private void setTextFieldHover() {
		getCustomTextField().setBackground(getCustomTextFieldColorHover());
		getCustomTextField().setBorder(createCustomBorderForTextField(getCustomBorderColorHover()));
	}
	
	
	private void setTextFieldActive() {
		getCustomTextField().setBackground(getCustomTextFieldColorActive());
		getCustomTextField().setBorder(createCustomBorderForTextField(getCustomBorderColorActive()));
	}
	
	
	public JButton getDictionaryButton() {
		return dictionaryButton;
	}
	

	public void setDictionaryButton(JButton dictionaryButton) {
		this.dictionaryButton = dictionaryButton;
	}


	public ImageIcon getDictionaryButtonIconActive() {
		return dictionaryButtonIconActive;
	}


	public void setDictionaryButtonIconActive(ImageIcon dictionaryButtonIconActive) {
		this.dictionaryButtonIconActive = dictionaryButtonIconActive;
	}


	public ImageIcon getDictionaryButtonIcon() {
		return dictionaryButtonIcon;
	}


	public void setDictionaryButtonIcon(ImageIcon dictionaryButtonIcon) {
		this.dictionaryButtonIcon = dictionaryButtonIcon;
	}
	
	
	public int getFontSize() {
		return DEFAULT_FONT_SIZE;
	}


	public void setFontSize(int fontSize) {
		this.customTextField.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
		this.DEFAULT_FONT_SIZE = fontSize;
	}


	public JTextField getCustomTextField() {
		return customTextField;
	}


	public void setCustomTextField(JTextField customTextField) {
		this.customTextField = customTextField;
	}


	public Color getCustomBorderColor() {
		return customBorderColor;
	}


	public Color getCustomBorderColorActive() {
		return customBorderColorActive;
	}


	public Border getCustomTextFieldBorder() {
		return customTextFieldBorder;
	}


	public void setCustomTextFieldBorder(Border customTextFieldBorder) {
		this.customTextFieldBorder = customTextFieldBorder;
	}

	public Color getCustomBorderColorHover() {
		return customBorderColorHover;
	}


	public Color getCustomTextFieldColor() {
		return customTextFieldColor;
	}


	public Color getCustomTextFieldColorActive() {
		return customTextFieldColorActive;
	}


	public Color getCustomTextFieldColorHover() {
		return customTextFieldColorHover;
	}
	
}
