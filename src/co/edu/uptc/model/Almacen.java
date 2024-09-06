package co.edu.uptc.model;

public class Almacen {
	private String nombre;
	private String direccion;
	private MyList<Producto> productos;

	public Almacen(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.productos = new MyList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public MyList<Producto> getProductos() {
		return productos;
	}

	public void addProducto(Producto producto) {
		productos.add(producto);
	}

	public Producto getProductoByCodigo(String codigo) {
		for (Producto producto : productos) {
			if (producto.getCodigo().equals(codigo)) {
				return producto;
			}
		}
		return null;
	}

	public void removeProducto(Producto producto) {
		productos.remove(producto);
	}

	public double getTotalInventoryValue() {
		double totalValue = 0;
		for (Producto producto : productos) {
			totalValue += producto.getCantidad() * producto.getPrecio();
		}
		return totalValue;
	}
}