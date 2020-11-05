package ventas.entities;

/**
 * @author Asullom
 * @version 1.0
 * @created 05-Nov-2020 9:01:42 am
 */
public class Venta {

	private String clie_nom;
	/**
	 * 1 por de defecto, 0 anulados
	 */
	private int activo;
	private Date fecha;
	public Cliente clie_id;
	public TIPO_COMPROBANTE Xtipo_comp;

	public Venta(){

	}

	public void finalize() throws Throwable {

	}

}