package com.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

public class Invoice {
	private int id;
	private LocalDateTime date;
	private BigDecimal total;
	private List<InvoiceLine> lines = new ArrayList<>();
	
	
	public Invoice() {
		this.date = LocalDateTime.now();
		this.total = BigDecimal.ZERO;
	}
	
	//add line will update total
	public void addLine(InvoiceLine line) {
		lines.add(line);
		if(line.getLineTotal() != null) {
			total = total.add(line.getLineTotal());
		}
	}


	//getters & setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<InvoiceLine> getLines() {
		return lines;
	}

	public void setLines(List<InvoiceLine> lines) {
		this.lines = lines;
	}
	
}
