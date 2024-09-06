package co.edu.uptc.model;

public class CadenaAlmacenes {
	private MyList<Almacen> almacenes;

	public CadenaAlmacenes() {
		this.almacenes = new MyList<>();
	}

	public void crearNuevoAlmacen(Almacen almacen) throws IllegalArgumentException {
		if (almacenExiste(almacen.getNombre())) {
			throw new IllegalArgumentException("Error: Almacén con el mismo nombre ya existe.");
		}
		almacenes.add(almacen);
	}

	public boolean almacenExiste(String nombre) {
		for (Almacen almacen : almacenes) {
			if (almacen.getNombre().equalsIgnoreCase(nombre)) {
				return true;
			}
		}
		return false;
	}

	public void editarNombreAlmacen(String nombreActual, String nuevoNombre) throws IllegalArgumentException {
		for (Almacen almacen : almacenes) {
			if (almacen.getNombre().equalsIgnoreCase(nombreActual)) {
				if (almacenExiste(nuevoNombre)) {
					throw new IllegalArgumentException("Error: Almacén con el mismo nombre ya existe.");
				}
				almacen.setNombre(nuevoNombre);
				return;
			}
		}
		throw new IllegalArgumentException("Error: Almacén no encontrado.");
	}

	public void eliminarAlmacen(String nombre) {
		almacenes.removeIf(almacen -> almacen.getNombre().equalsIgnoreCase(nombre));
	}

	public double valorTotalInventarioCadena() {
		double total = 0;
		for (Almacen almacen : almacenes) {
			total += almacen.getTotalInventoryValue();
		}
		return total;
	}

	public MyList<Almacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(MyList<Almacen> almacenes) {
		this.almacenes = almacenes;
	}

	public Almacen getAlmacenByName(String nombre) {
		for (Almacen almacen : almacenes) {
			if (almacen.getNombre().equalsIgnoreCase(nombre)) {
				return almacen;
			}
		}
		return null;
	}
}