package com.SQLInjection.Model;

public class Supplier {
	private int supId;
	private String product;
	private double price;

	public int getSupId() {
        return supId;
    }
    
    public void setSupId(int supId) {
        this.supId = supId;
    }
    
    public String getProduct() {
        return product;
    }
    
    public void setProducte(String product) {
        this.product = product;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}
