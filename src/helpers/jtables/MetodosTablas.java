/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.jtables;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author jafeth888
 */
public class MetodosTablas {
    
     public void addCheckBox(int column, JTable table)
    {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
    
    public boolean IsSelected(int row, int column, JTable table){ // determina si una fila con campo checkbox que esta seleccinado   
        return table.getValueAt(row, column) != null;                       
    }    
    
}
