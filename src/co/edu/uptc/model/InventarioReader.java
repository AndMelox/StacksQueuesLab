package co.edu.uptc.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InventarioReader {
	private String filePath;

	public InventarioReader(String filePath) {
		this.filePath = filePath;
	}

	public MyList<Almacen> readAlmacenes() throws IOException {
		MyList<Almacen> almacenes = new MyList<>();
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		Almacen currentAlmacen = null;

		for (String line : lines) {
			if (line.startsWith("---")) {
				currentAlmacen = null;
			} else if (currentAlmacen == null) {
				String[] parts = line.split(", ");
				if (parts.length >= 2) {
					currentAlmacen = new Almacen(parts[0], parts[1]);
					almacenes.add(currentAlmacen);
				} else {
					throw new IOException("Invalid almacen line format: " + line);
				}
			} else {
				String[] parts = line.split(", ");
				if (parts.length >= 5) {
					try {
						Producto producto = new Producto(parts[0], parts[1], Integer.parseInt(parts[2]),
								Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
						currentAlmacen.addProducto(producto);
					} catch (NumberFormatException e) {
						throw new IOException("Invalid number format in producto line: " + line, e);
					}
				} else {
					throw new IOException("Invalid producto line format: " + line);
				}
			}
		}
		return almacenes;
	}

	public void escribirInventario(MyList<Almacen> almacenes) throws IOException {
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
	}
}