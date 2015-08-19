package org.in.model;

import java.util.List;

public class Order {
	private long id;
	private long customerId;
	private long date;
	private List<LineItem> lineItems;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	// TODO
	public void addLineItem(LineItem item) {

	}

	public void removeLineItem(LineItem item) {

	}
}
