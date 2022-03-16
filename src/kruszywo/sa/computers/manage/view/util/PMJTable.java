package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class PMJTable extends JTable {


	private static final long serialVersionUID = 7352425134966455629L;
	
	private Border paddingBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
	
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> tableSorter;
	
	private Color evenRowBackgroundColor = SystemColor.inactiveCaptionBorder;
	private Color oddRowBackgroundColor = SystemColor.white;
	private Color selectedRowBackgroundColor = SystemColor.activeCaption;
	private Color selectedRowForegroundColor = SystemColor.black;
	
	private Color searchFieldBackgroundColor = SystemColor.white;
	
	private static int DEFAULT_FONT_SIZE = 13;
	
	private boolean isEditable;


	public PMJTable (boolean isEditable) {
		super();
		this.isEditable = isEditable;
		this.setEditable(this.isEditable);
		this.createVisuals();
		this.setTableRenderer();
	}
	
	private void createVisuals() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(25);
		this.setFont(new Font("Tahoma", Font.PLAIN, DEFAULT_FONT_SIZE));
		this.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, DEFAULT_FONT_SIZE));
		this.setShowGrid(false);
		this.setIntercellSpacing(new Dimension(0, 0));
		this.setSelectionBackground(selectedRowBackgroundColor);
		this.setSelectionForeground(selectedRowForegroundColor);
	}
	
	public void setTableRenderer() {
		TableCellRenderer tableHeadaerRenderer = this.getTableHeader().getDefaultRenderer();
		this.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
	            @Override
	            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	                JLabel headerLabel = (JLabel) tableHeadaerRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	                if(table.getColumnClass(column) == Date.class || table.getColumnClass(column) == Boolean.class) {
	                	headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
	                } else {
	                	headerLabel.setHorizontalAlignment(SwingConstants.LEFT);
	                }
	                return headerLabel;
	            }
	        });
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		this.setDefaultRenderer(String.class, leftRenderer);
		this.setDefaultRenderer(Integer.class, leftRenderer);
		this.setDefaultRenderer(Double.class, leftRenderer);
		this.setDefaultRenderer(Date.class, centerRenderer);
	}
	

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component comp = super.prepareRenderer(renderer, row, column);
			        if (JComponent.class.isInstance(comp) && getColumnClass(column) != java.util.Date.class){
			            ((JComponent)comp).setBorder(paddingBorder);
			        }
			        Color c = (row % 2 == 0 ? evenRowBackgroundColor : oddRowBackgroundColor);
					if(!comp.getBackground().equals(getSelectionBackground())) {
						comp.setBackground(c);
					}	
					return comp;
	}
	
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = this.getColumnModel();
	    for (int column = 0; column < this.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < this.getRowCount(); row++) {
	            TableCellRenderer renderer = this.getCellRenderer(row, column);
	            Component comp = this.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width + 10 , width);
	        }
	        if(width > 300) width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void setEditable(boolean isEditable) {
		if (!isEditable) this.setDefaultEditor(Object.class, null);
	}
	
	public void setDefaultTableModel(@SuppressWarnings("rawtypes") Class[] types) {
		DefaultTableModel tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}	
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return isEditable;
		    }
			
			
		};
		
		this.tableModel = tableModel;
		this.setModel(this.tableModel);
	}
	
	public JPanel getSearchTablePanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(searchFieldBackgroundColor);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		PMJTextField searchTableField = new PMJTextField("Wyszukaj");
		searchTableField.setFont(new Font("Tahoma", Font.PLAIN, DEFAULT_FONT_SIZE));
		searchTableField.setPreferredSize(new Dimension(125, 25));
		searchTableField.setSearchField(this);
		
		Component verticalStrut = Box.createVerticalStrut(30);
		searchPanel.add(verticalStrut);
		searchPanel.add(searchTableField);
		
		return searchPanel;
	}
	
	public DefaultTableModel getDefaultTableModel() {
		return this.tableModel;
	}

	public TableRowSorter<DefaultTableModel> getTableSorter() {
		return tableSorter;
	}

	public void setTableSorter(DefaultTableModel tableModel) {
		this.tableSorter = new TableRowSorter<DefaultTableModel>(tableModel);
	}
	
}
