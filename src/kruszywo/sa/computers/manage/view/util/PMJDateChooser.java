package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import com.toedter.calendar.JDateChooser;

import kruszywo.sa.computers.manage.model.CommonFunctions;

public class PMJDateChooser extends JDateChooser {

	private static final long serialVersionUID = 1153173872592503229L;
	private int DEFAULT_FONT_SIZE = 13;
	
	private ImageIcon calendarButtonIcon;
	private ImageIcon calendarButtonIconActive;
	
	public PMJDateChooser() {
		createVisuals();
	}


	private void createVisuals() {
		setCalendarButtonIcon(createCalendarButtonIcon());
		setCalendarButtonIconActive(createCalendarButtonIconActive());
		createCustomTextField();
		createCustomCalendarButton();
	}
	
	private void createCustomTextField() {
		setFont(new Font("Tahoma", Font.PLAIN, getFontSize()));
	}
	
	private void createCustomCalendarButton() {
		getCalendarButton().setBorder(null);
		getCalendarButton().setFocusable(false);
		getCalendarButton().setContentAreaFilled(false);
		getCalendarButton().setBackground(Color.WHITE);
		getCalendarButton().setContentAreaFilled(false);
		getCalendarButton().setOpaque(true);
		getCalendarButton().setFocusPainted(false);
		getCalendarButton().setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		getCalendarButton().setIcon(getCalendarButtonIcon());
		getCalendarButton().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(isEnabled()) {
					getCalendarButton().setIcon(getCalendarButtonIcon());
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(isEnabled()) {
					getCalendarButton().setIcon(getCalendarButtonIconActive());
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ImageIcon createCalendarButtonIcon() {
		return new ImageIcon(getClass().getResource("/calendar-default-25.png"));
	}
	
	private ImageIcon createCalendarButtonIconActive() {
		return new ImageIcon(getClass().getResource("/calendar-plus-hover-25.png"));
	}


	public ImageIcon getCalendarButtonIcon() {
		return calendarButtonIcon;
	}


	public void setCalendarButtonIcon(ImageIcon calendarButtonIcon) {
		this.calendarButtonIcon = calendarButtonIcon;
	}


	public ImageIcon getCalendarButtonIconActive() {
		return calendarButtonIconActive;
	}


	public void setCalendarButtonIconActive(ImageIcon calendarButtonIconActive) {
		this.calendarButtonIconActive = calendarButtonIconActive;
	}


	public int getFontSize() {
		return DEFAULT_FONT_SIZE;
	}


	public void setFontSize(int fontSize) {
		this.DEFAULT_FONT_SIZE = fontSize;
	}
	
	public String getCustomDate() {
		String date = "";
		if(this.getDate() != null) {
			date = CommonFunctions.formatDate(this.getDate(), this.getDateFormatString());
		}
		return date;
	}
	

}
