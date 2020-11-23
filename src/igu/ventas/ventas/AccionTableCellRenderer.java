/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.ventas.ventas;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Asullom
 */
public class AccionTableCellRenderer implements TableCellRenderer {
    private AddDelProductPanel comp;

     public AccionTableCellRenderer(final JPanel ifr) {
       comp = new AddDelProductPanel(ifr);
    }

    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        comp.setTabla(table);        
        return comp;
    }
    
    
}