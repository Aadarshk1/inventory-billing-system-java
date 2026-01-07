package com.inventory.model;

import java.math.BigDecimal;

public class Product {
	private int id;
	private String name;
	private String description;
	private BigDecimal price;
	private int stock;
	
	public Product() {}

	public Product(int id, String name, String description, BigDecimal price, int stock) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public Product(String name, String description, BigDecimal price, int stock) {
		this.id = 0;
		this.name=name;
		this.description = description;
		this.price =price;
		this.stock = stock;
	}

	//getters & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		
		return String.format("[%d] %s | %s | %s | stock=%d",id, name, description, price, stock);
	}
	
	
}
