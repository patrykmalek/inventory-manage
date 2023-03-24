package inventory.manage.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import inventory.manage.controller.Controller;
import inventory.manage.model.CommonFunctions;

import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;

public class WaitWindow extends JDialog {

	private static final long serialVersionUID = 5468690162537001608L;
	private Controller controller;
	
	private JProgressBar progressBar;
	
	private long startTime;
	private long endTime;
	private long duration;
	
	
	public WaitWindow(Controller controller) {
		super(controller.getMainFrame(), "Proszę czekać...", true);
		this.setController(controller);
		createVisuals();
	}

	private void createVisuals() {
        getContentPane().setLayout(new BorderLayout(10, 10));
        ((JComponent) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel messageLabel = new JLabel(" Trwa wykonywanie operacji...");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(messageLabel);
        this.setSize(new Dimension(300, 100));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        setProgressBar();
	}
	
	public void setProgressBar() {
		progressBar = new JProgressBar();
        progressBar.setForeground(new Color(0, 204, 0));
        progressBar.setIndeterminate(true);
        getContentPane().add(progressBar, BorderLayout.SOUTH);
	}
	
	
	public void showWindow() {
		this.setStartTime(System.currentTimeMillis());
		this.setVisible(true);
	}
	
	public void closeWindow() {
		this.setEndTime(System.currentTimeMillis());
		this.dispose();
	}
	
	public void done(String successInformation) {
		this.closeWindow();
		successInformation += "\nCzas trwania: " + CommonFunctions.millisToShortDHMS(this.getDuration());
		JOptionPane.showMessageDialog(controller.getMainFrame(), successInformation);
	}
	
	public void done() {
		this.closeWindow();
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getDuration() {
		this.duration = getEndTime() - getStartTime();
		return duration;
	}

}
