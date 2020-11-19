/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entities.Cliente;
import entities.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class VentaData {
    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(VentaData.class.getName());
    
    //static SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
    static SimpleDateFormat sdf = new SimpleDateFormat(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT);
    
    static Date dt = new Date();
    static String currentTime = sdf.format(dt);
    
    public static int create(Venta d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO ventas(clie_nom, clie_id, activo, fecha) "
                + "VALUES(?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getClie_nom());
            ps.setInt(++i, d.getClie_id());
            ps.setInt(++i, d.getActivo());
            System.out.println("d.getFecha(): " + d.getFecha());
            ps.setString(++i, sdf.format(  dt  ) ); //d.getFecha()
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from ventas
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

    public static int update(Venta d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE ventas SET "
                + "clie_nom=?, "
                + "clie_id=?, "
                + "activo=?, "
                + "fecha=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getClie_nom());
            ps.setInt(++i, d.getClie_id());
            ps.setInt(++i, d.getActivo());
            
            
            
            System.out.println("u d.getFecha(): " + d.getFecha());
            //System.out.println("u convert( d.getFecha() ): " + convert( d.getFecha() ));
            //System.out.println("dateFormated date is : " + df.format( convert( d.getFecha() ) ) );
             ps.setString(++i, sdf.format(  d.getFecha()  ) );
            
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM ventas WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Clie_id:" + ex.getMessage());
        }
        return comit;
    }


    public static List<Venta> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Venta> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM ventas ORDER BY id";
        } else {
            sql = "SELECT * FROM ventas WHERE (id LIKE'" + filter + "%' OR "
                    + "clie_nom LIKE'" + filter + "%' OR clie_id LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY clie_nom";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta d = new Venta();
                d.setId(rs.getInt("id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setClie_id(rs.getInt("clie_id"));
                d.setActivo(rs.getInt("activo"));
                try {
                    d.setFecha(sdf.parse(rs.getString("fecha")));
                } catch (Exception e) {
                }
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }
    
    
    public static List<Venta> list(String filter, Date fi, Date ff) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);
        
        
        String fechati = null;
        if (fi == null) {
            System.out.println("list.fechat: SIN FECHAAAiiiiii");
            fechati = currentTime;
        } else {
            fechati = sdf.format(fi);
        }
         System.out.println("list.fechati:" + fechati);
        
        String fechat = null;
        if (ff == null) {
            System.out.println("list.fechat: SIN FECHAAA");
            fechat = currentTime;
        } else {
            fechat = sdf.format(ff);
        }
        System.out.println("list.fechat:" + fechat);
        
        
        

        List<Venta> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            //sql = "SELECT * FROM ventas ORDER BY id";
             sql = "SELECT * FROM ventas "
                    + " WHERE strftime('%Y-%m-%d', fecha) >= strftime('%Y-%m-%d', '" + fechati + "') "
                    + " AND strftime('%Y-%m-%d', fecha) <= strftime('%Y-%m-%d', '" + fechat + "') "
                    + "ORDER BY fecha";
        } else {
            //sql = "SELECT * FROM ventas WHERE (id LIKE'" + filter + "%' OR "
            //        + "clie_nom LIKE'" + filter + "%' OR clie_id LIKE'" + filter + "%' OR "
            //        + "id LIKE'" + filter + "%') "
            //        + "ORDER BY clie_nom";
            sql = "SELECT * FROM ventas WHERE (id LIKE '" + filtert + "%'  "
                    + " OR clie_nom LIKE '" + filtert + "%' )"
                    + " AND strftime('%Y-%m-%d', fecha) >= strftime('%Y-%m-%d', '" + fechati + "') "
                    + " AND strftime('%Y-%m-%d', fecha) <= strftime('%Y-%m-%d', '" + fechat + "') "
 
                    + "ORDER BY fecha";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta d = new Venta();
                d.setId(rs.getInt("id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setClie_id(rs.getInt("clie_id"));
                d.setActivo(rs.getInt("activo"));
                try {
                    d.setFecha(sdf.parse(rs.getString("fecha")));
                } catch (Exception e) {
                }
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Venta getByPId(int id) {
        Venta d = new Venta();

        String sql = "SELECT * FROM ventas WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setClie_id(rs.getInt("clie_id"));
                d.setActivo(rs.getInt("activo"));
                try {
                    d.setFecha(sdf.parse(rs.getString("fecha")));
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    
    
    
    public static List<Venta> listActivesByCliente(int clie_id) {
        System.out.println("listActivesByCliente.clie_id:" + clie_id);

        List<Venta> ls = new ArrayList();

        String sql = "";

        sql = " SELECT * FROM ventas "
                + " WHERE clie_id = " + clie_id + " and activo=1 "
                + " ORDER BY id ";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta d = new Venta();
                d.setId(rs.getInt("id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setClie_id(rs.getInt("clie_id"));
                d.setActivo(rs.getInt("activo"));
                try {
                    d.setFecha(sdf.parse(rs.getString("fecha")));
                } catch (Exception e) {
                }
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listActivesByCliente", ex);
        }
        return ls;
    }
}
