package co.edu.uptc.presenter;

import co.edu.uptc.view.View;
import co.edu.uptc.view.ViewImplements;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		String filePath = "C:\\Users\\Usuario\\Desktop\\Laboratorio\\src\\co\\edu\\uptc\\model\\Inventario.txt";

		if (!Files.exists(Paths.get(filePath))) {
			System.err.println("Error: The file " + filePath + " does not exist.");
			return;
		}

		View view = new ViewImplements();
		Presenter presenter = new Presenter(view, filePath);

		while (true) {
			view.showMenu();
			String input = view.getInput();
			if (!presenter.isNumeric(input)) {
				view.showMessage("Opción no válida. Debe ser un número.");
				continue;
			}
			int option = Integer.parseInt(input);
			presenter.handleMenuOption(option);
		}
	}
}