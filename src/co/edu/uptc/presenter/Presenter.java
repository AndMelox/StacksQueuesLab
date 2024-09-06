package co.edu.uptc.presenter;

import co.edu.uptc.model.*;
import co.edu.uptc.view.View;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Presenter {
	private View view;
	private String filePath;
	private MyList<Almacen> almacenes;

	public Presenter(View view, String filePath) {
		this.view = view;
		this.filePath = filePath;
		try {
			this.almacenes = new InventarioReader(filePath).readAlmacenes();
		} catch (IOException e) {
			view.showMessage("Error reading almacenes: " + e.getMessage());
		}
	}

	public void handleMenuOption(int option) {
		switch (option) {
		case 1:
			createNewStore();
			break;
		case 2:
			registerProductEntry();
			break;
		case 3:
			viewProductByCode();
			break;
		case 4:
			viewNumberOfDifferentItems();
			break;
		case 5:
			viewTotalInventoryValueOfStore();
			break;
		case 6:
			registerProductSale();
			break;
		case 7:
			viewTotalInventoryValueOfChain();
			break;
		case 8:
			displayInventoryFile();
			break;
		case 9:
			editStore();
			break;
		case 10:
			deleteStore();
			break;
		case 11:
			editProduct();
			break;
		case 12:
			deleteProduct();
			break;
		case 13:
			System.exit(0);
			break;
		default:
			view.showMessage("Opción no válida. Intente de nuevo.");
		}
	}

	private void createNewStore() {
		view.showCreateNewStore();
		String nombre = view.getInput();
		String direccion = view.getInput();
		almacenes.add(new Almacen(nombre, direccion));
		view.showMessage("Nuevo almacén creado exitosamente.");
		saveAlmacenesToFile();
	}

	private void registerProductEntry() {
		view.showRegisterProductEntry();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el código del producto:");
		String codigo = view.getInput();
		Producto productoExistente = almacen.getProductoByCodigo(codigo);
		if (productoExistente != null) {
			view.showMessage("Ingrese la cantidad adicional:");
			String cantidadAdicionalStr = view.getInput();
			if (cantidadAdicionalStr.isEmpty() || !isNumeric(cantidadAdicionalStr)) {
				view.showMessage("Cantidad no puede estar vacía y debe ser un número.");
				return;
			}
			int cantidadAdicional = Integer.parseInt(cantidadAdicionalStr);
			productoExistente.setCantidad(productoExistente.getCantidad() + cantidadAdicional);
			view.showMessage("Cantidad actualizada exitosamente.");
		} else {
			view.showMessage("Ingrese el nombre del producto:");
			String nombre = view.getInput();
			view.showMessage("Ingrese la cantidad:");
			String cantidadStr = view.getInput();
			if (cantidadStr.isEmpty() || !isNumeric(cantidadStr)) {
				view.showMessage("Cantidad no puede estar vacía y debe ser un número.");
				return;
			}
			int cantidad = Integer.parseInt(cantidadStr);
			view.showMessage("Ingrese el precio:");
			String precioStr = view.getInput();
			if (precioStr.isEmpty() || !isNumeric(precioStr)) {
				view.showMessage("Precio no puede estar vacío y debe ser un número.");
				return;
			}
			double precio = Double.parseDouble(precioStr);
			view.showMessage("Ingrese la cantidad vendida:");
			String cantidadVendidaStr = view.getInput();
			if (cantidadVendidaStr.isEmpty() || !isNumeric(cantidadVendidaStr)) {
				view.showMessage("Cantidad vendida no puede estar vacía y debe ser un número.");
				return;
			}
			int cantidadVendida = Integer.parseInt(cantidadVendidaStr);
			Producto producto = new Producto(codigo, nombre, cantidad, precio, cantidadVendida);
			almacen.addProducto(producto);
			view.showMessage("Producto registrado exitosamente.");
		}
		saveAlmacenesToFile();
	}

	private void viewProductByCode() {
		view.showViewProductByCode();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el código del producto:");
		String codigo = view.getInput();
		Producto producto = almacen.getProductoByCodigo(codigo);
		if (producto != null) {
			view.showMessage(producto.toString());
		} else {
			view.showMessage("Producto no encontrado.");
		}
	}

	private void viewNumberOfDifferentItems() {
		view.showViewNumberOfDifferentItems();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		int totalItems = almacen.getProductos().size();
		view.showMessage("Número total de artículos diferentes: " + totalItems);
	}

	private void viewTotalInventoryValueOfStore() {
		view.showViewTotalInventoryValueOfStore();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		double totalValue = almacen.getTotalInventoryValue();
		view.showMessage("Valor total del inventario del almacén: " + totalValue);
	}

	private void registerProductSale() {
		view.showRegisterProductSale();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el código del producto:");
		String codigo = view.getInput();
		Producto producto = almacen.getProductoByCodigo(codigo);
		if (producto == null) {
			view.showMessage("Producto no encontrado.");
			return;
		}

		view.showMessage("Ingrese la cantidad vendida:");
		String cantidadStr = view.getInput();
		if (cantidadStr.isEmpty() || !isNumeric(cantidadStr)) {
			view.showMessage("Cantidad no puede estar vacía y debe ser un número.");
			return;
		}
		int cantidad = Integer.parseInt(cantidadStr);
		if (cantidad > producto.getCantidad()) {
			view.showMessage("Cantidad vendida no puede ser mayor que la cantidad disponible.");
			return;
		}

		producto.setCantidad(producto.getCantidad() - cantidad);
		producto.setCantidadVendida(producto.getCantidadVendida() + cantidad);
		view.showMessage("Venta registrada exitosamente.");
		saveAlmacenesToFile();
	}

	private void viewTotalInventoryValueOfChain() {
		double totalValue = 0;
		for (Almacen almacen : almacenes) {
			totalValue += almacen.getTotalInventoryValue();
		}
		view.showMessage("Valor total del inventario de la cadena: " + totalValue);
	}

	private void displayInventoryFile() {
		try {
			InventarioReader reader = new InventarioReader(filePath);
			MyList<Almacen> almacenes = reader.readAlmacenes();
			StringBuilder inventory = new StringBuilder();
			for (Almacen almacen : almacenes) {
				inventory.append(almacen.getNombre()).append(", ").append(almacen.getDireccion()).append("\n");
				for (Producto producto : almacen.getProductos()) {
					inventory.append(producto.getCodigo()).append(", ").append(producto.getNombre()).append(", ")
							.append(producto.getCantidad()).append(", ").append(producto.getPrecio()).append(", ")
							.append(producto.getCantidadVendida()).append("\n");
				}
				inventory.append("---\n");
			}
			view.displayInventory(inventory.toString());
		} catch (IOException e) {
			view.showMessage("Error reading inventory: " + e.getMessage());
		}
	}

	private void saveAlmacenesToFile() {
		try {
			StringBuilder data = new StringBuilder();
			for (Almacen almacen : almacenes) {
				data.append(almacen.getNombre()).append(", ").append(almacen.getDireccion()).append("\n");
				for (Producto producto : almacen.getProductos()) {
					data.append(producto.getCodigo()).append(", ").append(producto.getNombre()).append(", ")
							.append(producto.getCantidad()).append(", ").append(producto.getPrecio()).append(", ")
							.append(producto.getCantidadVendida()).append("\n");
				}
				data.append("---\n");
			}
			Files.write(Paths.get(filePath), data.toString().getBytes());
		} catch (IOException e) {
			view.showMessage("Error saving almacenes: " + e.getMessage());
		}
	}

	private Almacen findAlmacenByName(String nombre) {
		for (Almacen almacen : almacenes) {
			if (almacen.getNombre().equalsIgnoreCase(nombre)) {
				return almacen;
			}
		}
		return null;
	}

	private void editStore() {
		view.showEditStore();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el nuevo nombre del almacén:");
		String nuevoNombre = view.getInput();
		view.showMessage("Ingrese la nueva dirección del almacén:");
		String nuevaDireccion = view.getInput();

		almacen.setNombre(nuevoNombre);
		almacen.setDireccion(nuevaDireccion);
		view.showMessage("Almacén editado exitosamente.");
		saveAlmacenesToFile();
	}

	private void deleteStore() {
		view.showDeleteStore();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		almacenes.remove(almacen);
		view.showMessage("Almacén eliminado exitosamente.");
		saveAlmacenesToFile();
	}

	private void editProduct() {
		view.showEditProduct();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el código del producto:");
		String codigo = view.getInput();
		Producto producto = almacen.getProductoByCodigo(codigo);
		if (producto == null) {
			view.showMessage("Producto no encontrado.");
			return;
		}

		view.showMessage("Ingrese el nuevo nombre del producto:");
		String nuevoNombre = view.getInput();
		view.showMessage("Ingrese la nueva cantidad del producto:");
		String nuevaCantidadStr = view.getInput();
		if (nuevaCantidadStr.isEmpty() || !isNumeric(nuevaCantidadStr)) {
			view.showMessage("Cantidad no puede estar vacía y debe ser un número.");
			return;
		}
		int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
		view.showMessage("Ingrese el nuevo precio del producto:");
		String nuevoPrecioStr = view.getInput();
		if (nuevoPrecioStr.isEmpty() || !isNumeric(nuevoPrecioStr)) {
			view.showMessage("Precio no puede estar vacío y debe ser un número.");
			return;
		}
		double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

		producto.setNombre(nuevoNombre);
		producto.setCantidad(nuevaCantidad);
		producto.setPrecio(nuevoPrecio);
		view.showMessage("Producto editado exitosamente.");
		saveAlmacenesToFile();
	}

	private void deleteProduct() {
		view.showDeleteProduct();
		String nombreAlmacen = view.getInput();
		Almacen almacen = findAlmacenByName(nombreAlmacen);
		if (almacen == null) {
			view.showMessage("Almacén no encontrado.");
			return;
		}

		view.showMessage("Ingrese el código del producto:");
		String codigo = view.getInput();
		Producto producto = almacen.getProductoByCodigo(codigo);
		if (producto == null) {
			view.showMessage("Producto no encontrado.");
			return;
		}

		almacen.removeProducto(producto);
		view.showMessage("Producto eliminado exitosamente.");
		saveAlmacenesToFile();
	}

	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}