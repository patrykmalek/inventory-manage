package kruszywo.sa.place.kartoteka.view.util;

import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

public class PMJasperViewer extends JRViewer{
	
	private static final long serialVersionUID = 3185883796232864857L;
	private JFrame frame = new JFrame();


	public PMJasperViewer(String titleFrame, JasperPrint jasperPrint) throws JRException {
		super(jasperPrint);
		createVisuals(titleFrame);
	}
	
	private void createVisuals(String titleFrame) {
		frame.setTitle(titleFrame);
        frame.getContentPane().add(this);
        frame.validate();
        frame.setSize(new Dimension(1200, 1000));
        frame.setLocation(300, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void displayViewer() {
		 frame.setVisible(true);
	}
	
	@Override
    protected JRViewerToolbar createToolbar() {
        JRViewerToolbar toolbar = super.createToolbar();

        Locale locale = viewerContext.getLocale();
        ResourceBundle resBundle = viewerContext.getResourceBundle();
        JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resBundle);
        JRDocxSaveContributor docx = new JRDocxSaveContributor(locale, resBundle);
        toolbar.setSaveContributors(new JRSaveContributor[] {pdf, docx});

        return toolbar;
    } 

}
