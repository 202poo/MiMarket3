package igu.ventas.ventas;

import data.VentaData;
import data.VentaLineaData;
import entities.Venta;
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
    private String[] columns = {"#", "Producto", "Precio un", "Cantidad", "Subtotal", "Acciones"};
    private Class[] columnsType = {Integer.class, String.class, Double.class, Double.class, Double.class, Object.class};

    public VentaLineasTableModel() {
        // lis = VentaLineaData.list("");
    }

    public VentaLineasTableModel(Venta d) {
        this.lis = VentaLineaData.listByVenta(d.getId());
        this.lis.add(new VentaLinea());
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

    @Override
    public void setValueAt(Object valor, int row, int column) {
        VentaLinea d = (VentaLinea) lis.get(row);
        switch (column) {

            case 3:

                System.out.println("setValueAt : " + "" + valor);
                double gr = 0;
                try {
                    gr = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setCant(gr);
                System.out.println("getCant : " + "" + d.getCant());
                //d.setSubtotal( d.getCant()*d.getPrecio() );
                break;
            case 2:
                //  d.setInfo_adic("" + valor);
                break;

        }
        this.fireTableRowsUpdated(row, row);
        //fireTableCellUpdated(row, row);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        VentaLinea d = (VentaLinea) lis.get(row);
        //Venta ventaSelected = VentaData.getByPId(d.getVenta_id());
        //System.out.println("d.getProd_id() : " + d.getProd_id());
        //System.out.println("column : " + column);
        if (d.getProd_id() > 0 && d.getActivo() == 1) {
            if (column >= 0 && column != 0 && column != 1 && column != 2 && column != 4) { // editar solo la col 3
                return true;
            }
        } else if (d.getProd_id() == 0) {
            if (column == this.columns.length - 1) {
                return true;
            }
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
