package kruszywo.sa.computers.manage.exception;

import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JFrame;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextPane;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.view.WaitWindow;

public class SystemOperationException extends Exception{

	private static final long serialVersionUID = 1L;
	private JDialog errorFrame;
	private final String logFileName = "SystemOperationException.log";
	
	private JPanel mainPanel;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel; 

	public SystemOperationException(final String loginException, final SQLException errorException) {
		super(loginException, errorException);
		this.addErrors(this.getStackTraceAsString());
		this.writeLogToFile();
		
		if(errorException.getErrorCode() != 19) {
			this.createVisuals();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Błąd prawdopodobnie związany z usuwaniem obiektu, który ma powiązanie z inną tabelą. ---> \n" 
					+ errorException.getMessage(), "Błąd zapytania bazy danych.", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public SystemOperationException(final String errorMessage, final Throwable errorException) {
		super(errorMessage, errorException);
		this.addErrors(this.getStackTraceAsString());
		this.writeLogToFile();
		this.createVisuals();
	}
	/**
	 * @wbp.parser.constructor
	 */
	public SystemOperationException(final String errorMessage) {
		super(errorMessage);
		this.addErrors(this.getStackTraceAsString());
		this.writeLogToFile();
		this.createVisuals();
	}

	private void createVisuals() {
		
		if(getWaitWindow() != null) {
			getWaitWindow().closeWindow();
		}
		
		this.createFrame();
		this.createButtons();
		this.createLabels();
		if (this.getErrors().size() == 1) {
			this.showWindow();
			this.getErrors().clear();
		}
	}

	private void createFrame() {
		
		errorFrame = new JDialog();
		errorFrame.setTitle("Błąd programu..");
		errorFrame.setAlwaysOnTop( true );
		errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorFrame.getContentPane().setLayout(new BorderLayout());
		errorFrame.setSize(new Dimension(850, 300));
		errorFrame.getContentPane().setBackground(SystemColor.inactiveCaption);
		errorFrame.setResizable(true);
		errorFrame.setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		errorFrame.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		
		errorFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Controller.getErrors().clear();
			}
		});
		
	}
	
	private void createLabels() {
		JLabel errorTitleLabel = new JLabel(this.getMessage());
		errorTitleLabel.setForeground(new Color(165, 42, 42));
		errorTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JTextPane errorMessageTextArea = new JTextPane();
		errorMessageTextArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorMessageTextArea.setBackground(SystemColor.controlHighlight);
		errorMessageTextArea.setEditable(false);
		errorMessageTextArea.setText(this.getStackTraceAsString());
		errorMessageTextArea.setCaretPosition(0);
		JScrollPane errorMessageTextAreaContainer = new JScrollPane(errorMessageTextArea);		

		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout(10, 10));
		northPanel.setBackground(SystemColor.inactiveCaption);
		northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		northPanel.add(errorTitleLabel, BorderLayout.CENTER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBackground(SystemColor.inactiveCaption);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
		centerPanel.add(errorMessageTextAreaContainer, BorderLayout.CENTER);
		
		errorFrame.getContentPane().add(northPanel, BorderLayout.NORTH);
		errorFrame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}
	
	private void createButtons() {
		
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout(10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
		southPanel.setBackground(SystemColor.inactiveCaption);
		
		
		JButton btnCloseButton = new JButton("Zamknij");
		btnCloseButton.setBackground(SystemColor.activeCaptionBorder);
		btnCloseButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCloseButton.setFocusable(false);
		btnCloseButton.setPreferredSize(new Dimension(90, 25));
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorFrame.dispose();
			}
		});

		JButton btnLogButton = new JButton("Pokaż logi");
		btnLogButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnLogButton.setFocusable(false);
		btnLogButton.setBackground(SystemColor.activeCaptionBorder);
		btnLogButton.setPreferredSize(new Dimension(90, 25));
		btnLogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileWithLog();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(new BorderLayout(10, 10));
		panel.add(btnLogButton, BorderLayout.WEST);
		panel.add(btnCloseButton, BorderLayout.EAST);
		
		southPanel.add(panel, BorderLayout.LINE_END);
		errorFrame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
	}
	
	public void showWindow() {
		this.errorFrame.setModal(true);
		this.errorFrame.setVisible(true);
	}

	public void writeLogToFile() {
		try {
			createLogFolderIfNotExist();
			FileWriter fileWriter;
			File file = new File(this.getLogFilePath());
			fileWriter = new FileWriter(file.getAbsolutePath());
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(String.join("\n", this.getErrors()));
			printWriter.close();
		} catch (IOException e) {
			new SystemOperationException("Błąd podczas zapisu logów.", e);
		}

	}
	
	private void createLogFolderIfNotExist() {
		File directory = new File(getLogPath());
		
		if(!directory.exists()) directory.mkdir();
	}
	
	public void openFileWithLog() {
		try {
			ProcessBuilder processBuilder;
			File file = new File(this.getLogFilePath());
			processBuilder = new ProcessBuilder("notepad.exe", file.getAbsolutePath());
			processBuilder.start();
		} catch (IOException e) {
			new SystemOperationException("Błąd podczas otwarcia pliku logów", e);
		}
	}

	
	public String getStackTraceAsString() {
		StringWriter errors = new StringWriter();
		PrintWriter printWriter = new PrintWriter(errors);
		this.printStackTrace(printWriter);
		
		return errors.toString();
	}

	public String getLogFileName() {
		return this.logFileName;
	}
	
	private void addErrors(String errorMessage) {
		Controller.getErrors().add(errorMessage);
	}
	
	private List<String> getErrors() {
		return Controller.getErrors();
	}
	
	private String getLogPath() {
		return System.getProperty("user.dir") + "\\log\\";
	}
	
	private String getLogFilePath() {
			return System.getProperty("user.dir") + "\\log\\" + this.getLogFileName();
	}
	
	private WaitWindow getWaitWindow() {
		return Controller.waitWindow;
	}

}
