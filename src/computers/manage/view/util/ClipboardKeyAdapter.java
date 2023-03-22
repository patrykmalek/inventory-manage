package computers.manage.view.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

//StackOverflow Code https://stackoverflow.com/a/26780661
public class ClipboardKeyAdapter extends KeyAdapter {

        private static final String LINE_BREAK = "\r"; 
        private static final String CELL_BREAK = "\t"; 
        private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard(); 

        private final PMJTable table; 

        public ClipboardKeyAdapter(PMJTable table) { 
                this.table = table; 
        } 

        @Override 
        public void keyReleased(KeyEvent event) { 
                if (event.isControlDown()) { 
                        if (event.getKeyCode()==KeyEvent.VK_C) { // Copy                        
                                cancelEditing(); 
                                copyToClipboard(); 
                        }
                } 
        } 

        private void copyToClipboard() { 
                int numCols=table.getSelectedColumnCount(); 
                int numRows=table.getSelectedRowCount(); 
                int[] rowsSelected=table.getSelectedRows(); 
                int[] colsSelected=table.getSelectedColumns(); 
                if (numRows!=rowsSelected[rowsSelected.length-1]-rowsSelected[0]+1 || numRows!=rowsSelected.length || 
                                numCols!=colsSelected[colsSelected.length-1]-colsSelected[0]+1 || numCols!=colsSelected.length) {

                        JOptionPane.showMessageDialog(null, "", "Invalid Copy Selection", JOptionPane.ERROR_MESSAGE);
                        return; 
                } 

                StringBuffer excelStr=new StringBuffer(); 
                for (int col = 0; col < numCols; col++) {
                		String headersName = table.getColumnName(colsSelected[col]);
            			excelStr.append(headersName);
                		if (col<numCols-1) { 
                            excelStr.append(CELL_BREAK); 
                    } 
                }
         
                excelStr.append(LINE_BREAK);
                for (int row=0; row<numRows; row++) { 
                        for (int col=0; col<numCols; col++) {
                        	String escapedValue = escape(table.getValueAt(rowsSelected[row], colsSelected[col]));
                        		if (table.getModel().getColumnClass(col) == java.lang.Double.class) {
                        			escapedValue = doubleFormat(escapedValue);
                        		}
                                excelStr.append(escapedValue); 
                                if (col<numCols-1) { 
                                        excelStr.append(CELL_BREAK); 
                                } 
                        } 
                        excelStr.append(LINE_BREAK); 
                } 

                StringSelection sel  = new StringSelection(excelStr.toString()); 
                CLIPBOARD.setContents(sel, sel); 
        } 

        private void cancelEditing() { 
                if (table.getCellEditor() != null) { 
                        table.getCellEditor().cancelCellEditing(); 
            } 
        } 

        private String escape(Object cell) { 
        		
        		if(cell == null) return "";
        	
                return cell.toString().replace(LINE_BREAK, " ").replace(CELL_BREAK, " "); 
        } 
        
        private String doubleFormat(String cell) {
        	return cell.replace(".", ",");
        }
}