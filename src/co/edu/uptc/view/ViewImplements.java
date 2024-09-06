package co.edu.uptc.view;

import java.util.Scanner;

public class ViewImplements implements View {
	private Scanner scanner;

	public ViewImplements() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void showMenu() {
		System.out.println("1. Crear nuevo almacén");
		System.out.println("2. Registrar entrada de producto");
		System.out.println("3. Ver producto por código");
		System.out.println("4. Ver número de artículos diferentes");
		System.out.println("5. Ver valor total del inventario del almacén");
		System.out.println("6. Registrar venta de producto");
		System.out.println("7. Ver valor total del inventario de la cadena");
		System.out.println("8. Mostrar archivo de inventario");
		System.out.println("9. Editar almacén");
		System.out.println("10. Eliminar almacén");
		System.out.println("11. Editar producto");
		System.out.println("12. Eliminar producto");
		System.out.println("13. Salir");
	}

	@Override
	public void showMessage(String message) {
		System.out.println(message);
	}

	@Override
	public String getInput() {
		return scanner.nextLine();
	}

	@Override
	public void displayInventory(String inventory) {
		System.out.println(inventory);
	}

	@Override
	public void showCreateNewStore() {
		System.out.println("Ingrese el nombre del nuevo almacén:");
	}

	@Override
	public void showRegisterProductEntry() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showViewProductByCode() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showViewNumberOfDifferentItems() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showViewTotalInventoryValueOfStore() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showRegisterProductSale() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showViewTotalInventoryValueOfChain() {
		System.out.println("Valor total del inventario de la cadena:");
	}

	@Override
	public void showDisplayInventoryFile() {
		System.out.println("Mostrando archivo de inventario:");
	}

	@Override
	public void showEditStore() {
		System.out.println("Ingrese el nombre del almacén a editar:");
	}

	@Override
	public void showDeleteStore() {
		System.out.println("Ingrese el nombre del almacén a eliminar:");
	}

	@Override
	public void showEditProduct() {
		System.out.println("Ingrese el nombre del almacén:");
	}

	@Override
	public void showDeleteProduct() {
		System.out.println("Ingrese el nombre del almacén:");
	}
}