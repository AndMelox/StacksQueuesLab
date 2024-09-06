package co.edu.uptc.model;

public class Producto {
	private String codigo;
	private String nombre;
	private int cantidad;
	private double precio;
	private int cantidadVendida;

	public Producto(String codigo, String nombre, int cantidad, double precio, int cantidadVendida) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.cantidadVendida = cantidadVendida;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	@Override
	public String toString() {
		return "Producto{" + "codigo='" + codigo + '\'' + ", nombre='" + nombre + '\'' + ", cantidad=" + cantidad
				+ ", precio=" + precio + ", cantidadVendida=" + cantidadVendida + '}';
	}
}