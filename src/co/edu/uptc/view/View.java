package co.edu.uptc.view;

public interface View {
	void showMenu();

	void showMessage(String message);

	String getInput();

	void displayInventory(String inventory);

	void showCreateNewStore();

	void showRegisterProductEntry();

	void showViewProductByCode();

	void showViewNumberOfDifferentItems();

	void showViewTotalInventoryValueOfStore();

	void showRegisterProductSale();

	void showViewTotalInventoryValueOfChain();

	void showDisplayInventoryFile();

	void showEditStore();

	void showDeleteStore();

	void showEditProduct();

	void showDeleteProduct();
}