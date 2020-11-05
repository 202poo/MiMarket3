package ventas.entities;

/**
 * @author Asullom
 * @version 1.0
 * @created 05-Nov-2020 9:01:47 am
 */
public class Caja {

	private Date fecha;
	private int esIngreso;
	private String glosa;
	private double monto;
	public Cliente m_Cliente;
	public Proveedor m_Proveedor;

	public Caja(){

	}

	public void finalize() throws Throwable {

	}

}