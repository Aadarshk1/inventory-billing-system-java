package com.inventory.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.inventory.dao.InvoiceDAO;
import com.inventory.dao.ProductDAO;
import com.inventory.exception.InsufficientStockException;
import com.inventory.model.Invoice;
import com.inventory.model.InvoiceLine;
import com.inventory.model.Product;
import com.inventory.util.DBConnection;

// this layer handle all the business logic and transactions

public class InventoryService {

	private final ProductDAO productDAO= new ProductDAO();
	private final InvoiceDAO invoiceDAO = new InvoiceDAO();
	
	/* Create invoice safely using transaction
	 * Steps: 
	 * 1. Validate stock
	 * 2. Reduce stock
	 * 3. Save invoice + invoice lines
	 * 4. Commit if success
	 * 5. Rollback if failure
	 */
	
	public void createInvoice(Invoice invoice) throws SQLException, InsufficientStockException {
		
		// Get database connection
		try (Connection conn = DBConnection.getConnection()){
			
			try {
				// Start transaction
				conn.setAutoCommit(false);
				
				// Validate stock and reduce it
				for(InvoiceLine line : invoice.getLines()) {
					
					// Get product from DB using same connection
					Product product = productDAO.getProductById(line.getProduct().getId(), conn);
				
					// Product not found
					if(product == null) {
						throw new SQLException("Product not found : ID " + line.getProduct().getId());
					}
					
					// Insufficient stock
					if(product.getStock() < line.getQty()) {
						throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
					}
					
					
					// Reduce stock
					int newStock = product.getStock() - line.getQty();
					productDAO.updateStock(product.getId(), newStock, conn);
	
 				}
				
				// Save invoice and invoice lines
				invoiceDAO.saveInvoice(invoice, conn);
				
				// Commit transaction
				conn.commit();
				
			} catch(Exception e) {
				
				// Rollback if any error happens
				conn.rollback();
				throw e;
				
			} finally {
				
				// Restore default auto-commit behaviour
				conn.setAutoCommit(true);
			}
		}
	}
}
