package entities;

import java.util.Date;

/**
 *
 * @author Asullom
 */
public class Venta {

    private int id;
    private String clie_nom;
    private int activo;
    private Date fecha;
    public int clie_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClie_nom() {
        return clie_nom;
    }

    public void setClie_nom(String clie_nom) {
        this.clie_nom = clie_nom;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getClie_id() {
        return clie_id;
    }

    public void setClie_id(int clie_id) {
        this.clie_id = clie_id;
    }
    
    
}
