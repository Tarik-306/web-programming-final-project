package com.example.ecommerce.model;

import java.math.BigDecimal;

public class OrderItem {
    private int id, orderId, productId, quantity;
    private String productName;
    private BigDecimal unitPrice, subtotal;
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getOrderId(){return orderId;} public void setOrderId(int orderId){this.orderId=orderId;}
    public int getProductId(){return productId;} public void setProductId(int productId){this.productId=productId;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public String getProductName(){return productName;} public void setProductName(String productName){this.productName=productName;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal unitPrice){this.unitPrice=unitPrice;}
    public BigDecimal getSubtotal(){return subtotal;} public void setSubtotal(BigDecimal subtotal){this.subtotal=subtotal;}
}
