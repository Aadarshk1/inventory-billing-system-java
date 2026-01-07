package com.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.inventory.model.Invoice;
import com.inventory.model.InvoiceLine;

public class InvoiceDAO {

	//Insert invoice and its lines
	public void saveInvoice(Invoice invoice, Connection connection) throws SQLException{
		
		String invoiceQuery ="Insert into invoice(invoice_date, total) values(?,?)";
		String lineQuery = "Insert into invoice_line(invoice_id, product_id, qty, unit_price, line_total) values(?,?,?,?,?)";
		
		// Insert invoice
		try(PreparedStatement preparedStatement = connection.prepareStatement(invoiceQuery, Statement.RETURN_GENERATED_KEYS)){
			
			preparedStatement.setTimestamp(1, Timestamp.valueOf(invoice.getDate()));
			preparedStatement.setBigDecimal(2, invoice.getTotal());
			
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			
			if(rs.next()) {
				invoice.setId(rs.getInt(1));
			} else {
				throw new SQLException("Failed to generate invoice ID");
			}
		}
		
		//Insert invoice lines
		try(PreparedStatement preparedStatement = connection.prepareStatement(lineQuery)){
			
			for(InvoiceLine line : invoice.getLines()) {
				
				preparedStatement.setInt(1, invoice.getId());
				preparedStatement.setInt(2, line.getProduct().getId());
				preparedStatement.setInt(3, line.getQty());
				preparedStatement.setBigDecimal(4, line.getUnitPrice());
				preparedStatement.setBigDecimal(5, line.getLineTotal());
				
				preparedStatement.addBatch();				
			}
			preparedStatement.executeBatch();
		}
	}
}
