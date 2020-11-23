/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.ventas.ventas;

import data.VentaLineaData;
import entities.VentaLinea;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import util.MsgPanel;

/**
 *
 * @author Asullom
 */
public class TableCelCantEditor extends AbstractCellEditor implements TableCellEditor {

    JTextField valor;
    private String valorInicial = "";
    private String valorActual = "";
    private int fila;
    private int col;

    VentasPanel compra;
    private JTable tabla;

    public VentasPanel getCompra() {
        return compra;
    }

    public TableCelCantEditor(VentasPanel compra) {
        this.compra = compra;
        valor = new JTextField();
        valor.setHorizontalAlignment(JTextField.RIGHT);
        valor.setFont(new Font("Tahoma", 1, 14));
        valor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        valor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String filterStr = "0123456789.";
                char c = (char) e.getKeyChar();
                if (filterStr.indexOf(c) < 0) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField tmp = (JTextField) e.getSource();
                valorActual = tmp.getText();
                System.out.println("keyReleased valorActual: " + valorActual);
                System.out.println("keyReleased valorInicial: " + valorInicial);

                VentaLineasTableModel mt = ((VentaLineasTableModel) tabla.getModel());
                      
                if (!valorActual.trim().isEmpty()) { 
                    
                    mt.setValueAt(valorActual, fila, col);
                    tmp.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1));
                    MsgPanel.error("");

                    VentaLinea d = ((VentaLinea) mt.getRow(fila));
                    System.out.println("getCant : " + d.getCant());
                    
                    d.setSubtotal(d.getCant()*d.getPrecio());
                    /*
                    if (d.getMov_tipo() == 1) { //producto es selected
                        d.setComp_id(d.getComp_id());
                        d.setMov_tipo(d.getMov_tipo());
                        
                        d.setGlosa(d.getGlosa() );
                        
                        d.setCant_gr( Double.parseDouble(valorActual + "") );
                        d.setOnza(d.getOnza());
                        d.setPorc(d.getPorc());
                        d.setLey(d.getLey());
                        d.setSistema(d.getSistema());
                        d.setTc(d.getTc());
                        int opcion = CompraDetData.update(d);
                        if (opcion != 0) {
                            MsgPanel.success("Se ha modificado el detalle de la compra");
                            //pintar campos calculados: totales
                            VentaLinea dt = VentaLineaData.getByPId(d.getId());
                            //d.setPrecio_do(dt.getPrecio_do());
                            //d.setPrecio_so(dt.getPrecio_so());
                            d.setTotal_so(dt.getTotal_so());
                            d.setTotal_do(dt.getTotal_do());
                        }
                    } else if (d.getMov_tipo() == 2) {
                        
                    } else {
                        MsgPanel.success("Debe seleccionar un tipo de movimiento");
                        fireEditingStopped();
                    }*/
                } else {
                    tmp.setBorder(new LineBorder(new java.awt.Color(255, 0, 0), 3));
                    MsgPanel.error("cantidad es requerido", true);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        if (valorActual.trim().isEmpty()) { 
            return valorInicial;
        }else {
            return valorActual;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        System.out.println("getTableCellEditorComponent  cant fila: " + row);
        tabla = table;
        fila = row;
        col = column;
        valorInicial = ((VentaLineasTableModel) table.getModel()).getValueAt(row, column) + "";
        valorActual = value == null ? "" : "" + value;
        valor.setText(valorActual);        
        //System.out.println("Component valorActual : " + valorActual);
        //System.out.println("Component valorInicial : " + valorInicial);
        return valor;
    }

}
