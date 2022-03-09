package kruszywo.sa.computers.manage.view.util;

import java.awt.Component;

import javax.swing.JScrollPane;

public class PMJScrollPane extends JScrollPane {


	private static final long serialVersionUID = 6506498201357579479L;

	public PMJScrollPane(Component view) {
		super(view);
		createVisuals();
	}

	private void createVisuals() {
		setBorder(null);
	}
	
}
