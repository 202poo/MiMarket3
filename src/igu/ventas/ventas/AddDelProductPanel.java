/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.ventas.ventas;

import entities.Producto;
import entities.Venta;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Asullom
 */
public class AddDelProductPanel extends javax.swing.JPanel {
    private JPanel ifr;
    private JTable tabla;
    private VentaLineasTableModel mtdc;
    private int indexFila = -1;
    private CeldaAccionEditor cae;
    
    public AddDelProductPanel(JPanel ifr) {
        initComponents();
        this.ifr = ifr;

    }

    public void setCeldaEditor(CeldaAccionEditor cae) {
        this.cae = cae;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
        mtdc = (VentaLineasTableModel) this.tabla.getModel();
    }

    public void setIndexFila(int fila) {
        this.indexFila = fila;
    }

    /**
     * Creates new form AddDelProductPanel
     */
    public AddDelProductPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/igu/imgs/icons/agregar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/igu/imgs/icons/eliminar.png"))); // NOI18N
        add(jButton2);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("add");
        if (this.tabla.getSelectedRow() != -1) {
            this.indexFila = this.tabla.getSelectedRow();
            System.err.println("add " + this.indexFila);
        }
        //if (((VentasPanel) this.ifr).isEsActualizacion()) {
        //    System.out.println("isEsActualizacion  true ");
        //}

        VentasPanel cp = ((VentasPanel) this.ifr);
        Venta ventaSelected = cp.getVentaSelected();
        if (ventaSelected != null) {
            BuscarProductoPanel mov = new BuscarProductoPanel(ventaSelected);
            // mov.setCompraSelected(compraSelected);
            //mov.getCompraSelected().getEsdolares()
            JOptionPane.showOptionDialog(null, mov,
                    "Elija un producto ",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null);
            
            Producto pSelected=mov.getProducto();
            if (pSelected != null) {
                System.err.println("Producto seleccionado " + pSelected.getNombre() );
                
            }else {
                System.err.println("Producto NO seleccionado " );
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
