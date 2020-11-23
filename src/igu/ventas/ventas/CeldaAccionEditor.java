/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.ventas.ventas;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Asullom
 */
public class CeldaAccionEditor extends AbstractCellEditor implements TableCellEditor{

    private AddDelProductPanel pa;
    public CeldaAccionEditor(JPanel ifr) {
        pa = new AddDelProductPanel(ifr);
        pa.setCeldaEditor(this);
        
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        pa.setTabla(table);
        pa.setIndexFila(row);        
        return pa;
    }
    
    public void lanzarDetencionEdicion()
    {
        this.fireEditingStopped();
    }
    
}

    

