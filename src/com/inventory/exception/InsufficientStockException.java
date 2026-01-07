package com.inventory.exception;

// Business exception thrown when stock is insufficient

public class InsufficientStockException extends Exception{

	public InsufficientStockException(String message) {
		super(message);
	}

}
