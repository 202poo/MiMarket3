package entities;

/**
 *
 * @author Asullom
 */
public class Cliente {

    private int id;
    private String nombres;
    private String info_adic;
    
    public String toString() {
        return nombres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getInfo_adic() {
        return info_adic;
    }

    public void setInfo_adic(String info_adic) {
        this.info_adic = info_adic;
    }
    

}
