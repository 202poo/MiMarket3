package data;

import entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import util.ErrorLogger;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Asullom
 */
public class ProductoData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ProductoData.class.getName());
    
    //static SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
    static SimpleDateFormat sdf = new SimpleDateFormat(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT);
        
    public static int create(Producto d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO productos(nombre, detalle, precio, fecha_ven) "
                + "VALUES(?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getDetalle());
            ps.setDouble(++i, d.getPrecio());
            System.out.println("d.getFecha_ven(): " + d.getFecha_ven());
            ps.setString(++i, sdf.format(  d.getFecha_ven()  ) );
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from productos
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

    public static int update(Producto d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE productos SET "
                + "nombre=?, "
                + "detalle=?, "
                + "precio=?, "
                + "fecha_ven=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getDetalle());
            ps.setDouble(++i, d.getPrecio());
            
            
            
            System.out.println("u d.getFecha_ven(): " + d.getFecha_ven());
            //System.out.println("u convert( d.getFecha_ven() ): " + convert( d.getFecha_ven() ));
            //System.out.println("dateFormated date is : " + df.format( convert( d.getFecha_ven() ) ) );
             ps.setString(++i, sdf.format(  d.getFecha_ven()  ) );
            
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM productos WHERE id = ?";
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

    public static List<Producto> listCmb(String filter) {
        List<Producto> ls = new ArrayList();
        //ls.add(new Producto("Seleccione..."));
        ls.addAll(list(filter));
        return ls;
    }

    public static List<Producto> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Producto> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM productos ORDER BY id";
        } else {
            sql = "SELECT * FROM productos WHERE (id LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR detalle LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto d = new Producto();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setDetalle(rs.getString("detalle"));
                d.setPrecio(rs.getDouble("precio"));
                try {
                    d.setFecha_ven(sdf.parse(rs.getString("fecha_ven")));
                } catch (Exception e) {
                }
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Producto getByPId(int id) {
        Producto d = new Producto();

        String sql = "SELECT * FROM productos WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setDetalle(rs.getString("detalle"));
                d.setPrecio(rs.getDouble("precio"));
                try {
                    d.setFecha_ven(sdf.parse(rs.getString("fecha_ven")));
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
}
