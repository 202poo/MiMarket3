package igu.ventas.ventas;

import data.VentaLineaData;
import entities.VentaLinea;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asullom
 */
public class VentaLineasTableModel extends AbstractTableModel {

    private List<VentaLinea> lis = new ArrayList();
    private String[] columns = {"#", "Producto",    "Precio un", "Cantidad", "Subtotal", "Acciones"};
    private Class[] columnsType = {Integer.class, String.class, Double.class, Double.class, Double.class, String.class};

    public VentaLineasTableModel() {
        lis = VentaLineaData.list("");
    }

    public VentaLineasTableModel(String filter) {

        lis = VentaLineaData.list(filter);
    }

   // public List<VentaLinea> getRegistros() {
  //      return CienteData.listCmb("");
   // }

    // public List<VentaLinea> getlist(String filter) {
    //    lis = CienteData.list(filter);
    //    return lis;
    // }
    @Override
    public Object getValueAt(int row, int column) {
        VentaLinea d = (VentaLinea) lis.get(row);
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return d.getDescripcion();
            case 2:
                return d.getPrecio();
            case 3:
                return d.getCant();
            case 4:
                return d.getSubtotal();
            case 5:
                return "Add/delete";   
             
            default:
                return null;
        }
    }

    /*
    @Override
    public void setValueAt(Object valor, int row, int column) {
        VentaLinea d = (VentaLinea) lis.get(row);
        switch (column) {
            
           // case 0:
           //     int id = 0;
           //     try {
            //        if (valor instanceof Number) {
           //             id = Integer.parseInt("" + valor);
           //         }
           //     } catch (NumberFormatException nfe) {
            //        System.err.println("" + nfe);
             //   }
            //    d.setId(id);
             //   break;
             
            case 1:
                d.setNombres("" + valor);
                break;
            case 2:
                d.setInfo_adic("" + valor);
                break;

        }
        this.fireTableRowsUpdated(row, row);
        //fireTableCellUpdated(row, row);
    }
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        //VentaLinea c = (VentaLinea) lis.get(row);
        if (column >= 0 && column != 0) {
            //return true;
        }
        return false;//bloquear edicion
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public void addRow(VentaLinea d) { // con db no se usa
        this.lis.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lis.size(), lis.size());
    }

    public void removeRow(int linha) { // con db no se usa
        this.lis.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
