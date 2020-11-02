package borrador;

import data.Conn;
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
import util.Config;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class CajaAperCierreData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(CajaAperCierreData.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);
    static String currentTime = sdf.format(dt);

    public static int create(CajaAperCierre d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO caja_aper_cierre(fecha,  esaper, saldo_do, saldo_so, saldo_bancos_do, "
                + "saldo_bancos_so,   " //gramos,
                + "user_id) "
                + "VALUES(?,?,?,?,?  ,?,? )";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);

            String fecha = sdf.format(d.getFecha());
            ps.setString(++i, fecha);
            ps.setInt(++i, d.getEsaper());
            ps.setDouble(++i, d.getSaldo_do());
            ps.setDouble(++i, d.getSaldo_so());
            ps.setDouble(++i, d.getSaldo_bancos_do());
            ps.setDouble(++i, d.getSaldo_bancos_so());
            ps.setInt(++i, d.getUser_id());

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from caja_aper_cierre
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

    public static int update(CajaAperCierre d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE caja_aper_cierre SET "
                + "fecha=?, "
                + "esaper=?, "
                + "saldo_do=?, "
                + "saldo_so=?, "
                + "saldo_bancos_do=?, "
                + "saldo_bancos_so=?, "
                + "user_id=?, "
                + "last_updated=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            String fecha = sdf.format(d.getFecha());
            ps.setString(++i, fecha);
            ps.setInt(++i, d.getEsaper());
            ps.setDouble(++i, d.getSaldo_do());
            ps.setDouble(++i, d.getSaldo_so());
            ps.setDouble(++i, d.getSaldo_bancos_do());
            ps.setDouble(++i, d.getSaldo_bancos_so());
            ps.setInt(++i, d.getUser_id());
            ps.setString(++i, sdf.format(dt));
            ps.setInt(++i, d.getId());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM caja_aper_cierre WHERE id = ?";
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

    public static List<CajaAperCierre> list(Date fechai, Date fecha, String busca) {
        String fechati = null;
        if (fechai == null) {
            System.out.println("list.fechat: SIN FECHAAAiiiiii");
            fechati = currentTime;
        } else {
            fechati = sdf.format(fechai);
        }
         System.out.println("list.fechati:" + fechati);
        
        String fechat = null;
        if (fecha == null) {
            System.out.println("list.fechat: SIN FECHAAA");
            fechat = currentTime;
        } else {
            fechat = sdf.format(fecha);
        }
        System.out.println("list.fechat:" + fechat);
        
        List<CajaAperCierre> ls = new ArrayList<CajaAperCierre>();
        String sql = "";
        
        if (busca.equals("")) {
            sql = "SELECT * FROM caja_aper_cierre "
                    + "WHERE strftime('%Y-%m-%d', fecha) between strftime('%Y-%m-%d', '" + fechati + "') and strftime('%Y-%m-%d', '" + fechat + "') "
                    + "ORDER BY fecha";
        } else {
            sql = "SELECT * FROM caja_aper_cierre WHERE (id LIKE'" + busca + "%'  "
                    + " OR esaper LIKE'" + busca + "%' OR "
                    + "id LIKE'" + busca + "%') "
                    + " AND strftime('%Y-%m-%d', fecha)  between strftime('%Y-%m-%d', '" + fechati + "') and strftime('%Y-%m-%d', '" + fechat + "') "
                    + "ORDER BY fecha";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CajaAperCierre d = new CajaAperCierre();
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date date = sdf.parse(fechax);
                    d.setFecha(date);
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }
                d.setEsaper(rs.getInt("esaper"));
                d.setSaldo_do(rs.getDouble("saldo_do"));
                d.setSaldo_so(rs.getDouble("saldo_so"));
                d.setSaldo_bancos_do(rs.getDouble("saldo_bancos_do"));
                d.setSaldo_bancos_so(rs.getDouble("saldo_bancos_so"));
                d.setGramos(rs.getDouble("gramos"));
                d.setUser_id(rs.getInt("user_id"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static CajaAperCierre getByPId(int id) {
        CajaAperCierre d = new CajaAperCierre();

        String sql = "SELECT * FROM caja_aper_cierre WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date date = sdf.parse(fechax);
                    d.setFecha(date);
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }
                d.setEsaper(rs.getInt("esaper"));
                d.setSaldo_do(rs.getDouble("saldo_do"));
                d.setSaldo_so(rs.getDouble("saldo_so"));
                d.setSaldo_bancos_do(rs.getDouble("saldo_bancos_do"));
                d.setSaldo_bancos_so(rs.getDouble("saldo_bancos_so"));
                d.setGramos(rs.getDouble("gramos"));
                d.setUser_id(rs.getInt("user_id"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    public static CajaAperCierre getByFechaAndEsaper(Date date, int esaper) {
        CajaAperCierre d = new CajaAperCierre();
        String sql = "SELECT * FROM caja_aper_cierre WHERE strftime('%Y-%m-%d', fecha)= strftime('%Y-%m-%d', '" + sdf.format(date) + "') and esaper = '" + esaper + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                String fecha = rs.getString("fecha");
                System.out.println("getById.fecha:" + fecha);
                try {
                    Date datex = sdf.parse(fecha);
                    System.out.println("getById.datex:" + datex);
                    d.setFecha(date);
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }

                d.setEsaper(rs.getInt("esaper"));
                d.setSaldo_do(rs.getDouble("saldo_do"));
                d.setSaldo_so(rs.getDouble("saldo_so"));
                d.setSaldo_bancos_do(rs.getDouble("saldo_bancos_do"));
                d.setSaldo_bancos_so(rs.getDouble("saldo_bancos_so"));
                d.setGramos(rs.getDouble("gramos"));
                d.setUser_id(rs.getInt("user_id"));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByFechaAndEsaper", ex);
        }
        return d;
    }
}
