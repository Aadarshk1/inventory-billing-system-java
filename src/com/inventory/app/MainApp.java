package com.inventory.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.inventory.dao.ProductDAO;
import com.inventory.exception.InsufficientStockException;
import com.inventory.model.Invoice;
import com.inventory.model.InvoiceLine;
import com.inventory.model.Product;
import com.inventory.service.InventoryService;

public class MainApp {
	
	private static final ProductDAO productDAO = new ProductDAO();
	private static final InventoryService inventoryService = new InventoryService();
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("================================");
		System.out.println(" INVENTORY & BILLING SYSTEM ");
		System.out.println("================================");
		
		while(true) {
			System.out.println("\n1. Add Product");
			System.out.println("2. View Product");
			System.out.println("3. Create Invoice");
			System.out.println("4. Update Stock");
			System.out.println("5. Delete Product");
			System.out.println("6. Exit");
			System.out.println("Choose option: ");
			
			int choice = Integer.parseInt(sc.nextLine());
			
			try {
				switch(choice) {
				case 1 -> addProduct(sc);
				case 2 -> viewProduct();
				case 3 -> createInvoice(sc);
				case 4 -> updateStock(sc);
				case 5 -> deleteProduct(sc);
				case 6 -> {
					System.out.println("Exiting... Thank you!");
					sc.close();
					return;
				}
				default -> System.out.println("Invalid option!");
			}	
		} catch (InsufficientStockException e) {
			System.out.println("BUSINESS ERROR: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DATABASES ERROR: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}

	// 1. Add Product 
	
	private static void addProduct(Scanner sc) throws SQLException{
		
		System.out.println("Enter product name: ");
		String name = sc.nextLine();
		
		System.out.println("Enter description: ");
		String desc = sc.nextLine();
		
		System.out.println("Enter price: ");
		BigDecimal price = new BigDecimal(sc.nextLine());
		
		System.out.println("Enter stock: ");
		int stock = Integer.parseInt(sc.nextLine());
		
		Product product = new Product(name, desc, price, stock);
		productDAO.addProduct(product);
		
		System.out.println("Product added successfully!");
		System.out.println("Product ID: " + product.getId());
		
	}
	
	//2. View Products
	
	private static void viewProduct() throws SQLException {
		
		List<Product> products = productDAO.getAllProduct();
		
		System.out.println("\n-- PRODUCT LIST ---");
		if(products.isEmpty()) {
			System.out.println("No products available.");
			return;
		}
		
		for(Product p : products) {
			System.out.println(
					p.getId() + " | " +
					p.getName() + " | " +
					p.getPrice() + " | " + 
					p.getStock()
				);
		}
	}
	
	//3. Create Invoice
	
	private static void createInvoice(Scanner sc) throws SQLException, InsufficientStockException {
		
		Invoice invoice = new Invoice();
		
		while(true) {
			System.out.println("Enter product ID (0 to finish): ");
			int productId = Integer.parseInt(sc.nextLine());
			
			if(productId == 0) break;
			
			Product product = productDAO.getProductById(productId);
			
			if(product == null) {
				System.out.println("Product not found!");
				continue;
			}
			
			System.out.println("Enter quantity: ");
			int qty = Integer.parseInt(sc.nextLine());
			
			InvoiceLine line = new InvoiceLine(product, qty);
			invoice.addLine(line);
			
			System.out.println("Added: " + product.getName());
		}
		
		if(invoice.getLines().isEmpty()) {
			System.out.println("Invoice cancelled (no items).");
			return;
		}
		
		inventoryService.createInvoice(invoice);
		
		System.out.println("Invoice created successfully!");
		System.out.println("TOTAL AMOUNT: " + invoice.getTotal());
	}
	
	//4. Update Stock
	
	private static void updateStock(Scanner sc) throws SQLException{
		
		System.out.println("Enter product Id: ");
		int productId = Integer.parseInt(sc.nextLine());
		
		Product product = productDAO.getProductById(productId);
		if(product == null) {
			System.out.println("Product not found!");
			return;
		}
		
		System.out.println("Current stock: " + product.getStock() + "\nEnter new Stock: ");
		int newStock = Integer.parseInt(sc.nextLine());
		
		if(newStock < 0) {
			System.out.println("Stock cannot be negative!");
			return;
		}
		
		productDAO.updateStock(productId, newStock);
		System.out.println("Stock updated successfully!");
	}
	
	//5. Delete product
	
	private static void deleteProduct(Scanner sc) throws SQLException{
		
		System.out.println("Enter product ID to delete: ");
		int productId = Integer.parseInt(sc.nextLine());
		
		Product product = productDAO.getProductById(productId);
		if(product == null) {
			System.out.println("Product not found!");
			return;
		}
		
		productDAO.deleteProduct(productId);
		System.out.println("Product deleted successfully!");
	}
}

