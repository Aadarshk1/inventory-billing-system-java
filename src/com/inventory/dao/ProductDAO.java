package com.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.inventory.model.Product;
import com.inventory.util.DBConnection;

public class ProductDAO {
	
	//Add product to DB
	public Product addProduct(Product product) throws SQLException{
		String query = "Insert into product(name, description, price, stock) values (?,?,?,?)";
		
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setBigDecimal(3, product.getPrice());
			preparedStatement.setInt(4, product.getStock());
			
			preparedStatement.executeUpdate();
			
			//get auto-generated product_id
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next()){
				product.setId(rs.getInt(1));
			}
		}
		return product;
	}
	
	// Get product by ID
	public Product getProductById(int id) throws SQLException{
		String query = "Select * from product where product_id = ?";
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				return mapRowToProduct(rs);
			}
		}
		
		
		return null;
	}
	public Product getProductById(int id, Connection connection) throws SQLException{
		
		String query = "Select * from product where product_id = ?";
		
		try(	PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				return mapRowToProduct(rs);
			}
		}
		
		return null;
	}
	
	//Get all Product
	public List<Product> getAllProduct() throws SQLException{
		
		List<Product> products = new ArrayList<>();
		String query = "Select * from product";
		
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				products.add(mapRowToProduct(rs));
			}
			
		}
		
		return products;
	}
	
	// Update stock (used during invoice creation)
	public void updateStock(int productId, int newStock) throws SQLException{
		
		String query = "Update product set stock = stock + ? where product_id = ?";
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			preparedStatement.setInt(1, newStock);
			preparedStatement.setInt(2, productId);
			preparedStatement.executeUpdate();
		}
	}
	

	public void updateStock(int productId, int newStock, Connection connection) throws SQLException{
		
		String query = "Update product set stock = ? where product_id = ?";
		
		try(	PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			preparedStatement.setInt(1, newStock);
			preparedStatement.setInt(2, productId);
			preparedStatement.executeUpdate();
		}
	}
	
	// Delete Product
	public void deleteProduct(int id) throws SQLException{
		
		String query = "Delete from product where product_id =?";
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		}
		
	}

	// helper method: DB row -> Product object
	private Product mapRowToProduct(ResultSet rs) throws SQLException{
		
		Product product = new Product();
		
		product.setId(rs.getInt("product_id"));
		product.setName(rs.getString("name"));
		product.setDescription(rs.getString("description"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setStock(rs.getInt("stock"));
		
		return product;
	}
}
