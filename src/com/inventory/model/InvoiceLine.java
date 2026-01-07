package com.inventory.model;

import java.math.BigDecimal;

public class InvoiceLine {

	private int id;
	private Product product;
	private int qty;
	private BigDecimal unitPrice;
	private BigDecimal lineTotal; //unitPrice * qty
	
	public InvoiceLine() {}
	
	public InvoiceLine(Product product, int qty) {
		this.product = product;
		this.qty = qty;
		this.unitPrice = product.getPrice();
		this.lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));  
	}
	
	//getters & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	
}
