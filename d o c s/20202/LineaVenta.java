package ventas.entities;

/**
 * @author Asullom
 * @version 1.0
 * @created 05-Nov-2020 9:00:41 am
 */
public class LineaVenta {

	private String descripcion;
	private double cant;
	private double precio_un;
	private double total_dscto;
	private double total;
	public Producto m_Producto;
	public Venta m_Venta;

	public LineaVenta(){

	}

	public void finalize() throws Throwable {

	}

}