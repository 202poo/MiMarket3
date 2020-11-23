/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entities.VentaLinea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class VentaLineaData {
    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(VentaLineaData.class.getName());

    
    public static int create(VentaLinea d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO venta_lineas(descripcion, cant, precio, descto, subtotal, venta_id,  prod_id) " //activo
                + "VALUES(?,?,?,?,?   ,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getDescripcion());
            ps.setDouble(++i, d.getCant());
            ps.setDouble(++i, d.getPrecio());
            ps.setDouble(++i, d.getDescto());
            ps.setDouble(++i, d.getSubtotal());
            
            ps.setInt(++i, d.getVenta_id());
            ps.setInt(++i, d.getProd_id());
            
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from venta_lineas
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(VentaLinea d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE venta_lineas SET "
                + "descripcion=?, "
                + "cant=?, "
                + "precio=?, "
                + "descto=?, "
                + "subtotal=?, "
                + "prod_id=? "
                
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getDescripcion());
            ps.setDouble(++i, d.getCant());
            ps.setDouble(++i, d.getPrecio());
            ps.setDouble(++i, d.getDescto());
            ps.setDouble(++i, d.getSubtotal());
            
            //ps.setInt(++i, d.getVenta_id());
            ps.setInt(++i, d.getProd_id());
            
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM venta_lineas WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<VentaLinea> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<VentaLinea> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM venta_lineas ORDER BY id";
        } else {
            sql = "SELECT * FROM venta_lineas WHERE (id LIKE'" + filter + "%' OR "
                    + "descripcion LIKE'" + filter + "%' OR cant LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY descripcion";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                VentaLinea d = new VentaLinea();
                d.setId(rs.getInt("id"));
                d.setDescripcion(rs.getString("descripcion"));
                d.setCant(rs.getDouble("cant"));
                d.setPrecio(rs.getDouble("precio"));
                d.setDescto(rs.getDouble("descto"));
                d.setSubtotal(rs.getDouble("subtotal"));
                d.setProd_id(rs.getInt("prod_id"));
                d.setVenta_id(rs.getInt("venta_id"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static VentaLinea getByPId(int id) {
        VentaLinea d = new VentaLinea();

        String sql = "SELECT * FROM venta_lineas WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setDescripcion(rs.getString("descripcion"));
                 d.setCant(rs.getDouble("cant"));
                d.setPrecio(rs.getDouble("precio"));
                d.setDescto(rs.getDouble("descto"));
                d.setSubtotal(rs.getDouble("subtotal"));
                d.setProd_id(rs.getInt("prod_id"));
                d.setVenta_id(rs.getInt("venta_id"));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    
    public static List<VentaLinea> listByVenta(int venta_id) { // devolver todas las lineas de una venta

        System.out.println("listByVenta.comp_id:" + venta_id);

        List<VentaLinea> ls = new ArrayList();

        String sql = "";
         sql = " SELECT * FROM venta_lineas "
                + " WHERE venta_id = " + venta_id + " "
                + " ORDER BY id ";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                VentaLinea d = new VentaLinea();
                d.setId(rs.getInt("id"));
                d.setDescripcion(rs.getString("descripcion"));                
                d.setCant(rs.getDouble("cant"));
                d.setPrecio(rs.getDouble("precio"));
                d.setDescto(rs.getDouble("descto"));
                d.setSubtotal(rs.getDouble("subtotal"));
                d.setProd_id(rs.getInt("prod_id"));
                d.setVenta_id(rs.getInt("venta_id"));
                
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }
    
}
