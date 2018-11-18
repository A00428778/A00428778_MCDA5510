package com.mcds5510.entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Transaction {
	private int ID;
    private String NameOnCard;
    private String CardNumber;
    private Double UnitPrice;
    private Integer Quantity;
    private Double TotalPrice;
    private String ExpDate;
    private String CreatedOn;
    private String CreatedBy;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getNameOnCard() {
		return NameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		NameOnCard = nameOnCard;
	}
	public String getCardNumber() {
		return CardNumber;
	}
	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	public Double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		UnitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	public Double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getExpDate() {
		return ExpDate;
	}
	public void setExpDate(String expDate) {
		ExpDate = expDate;
	}
	public String getCreatedOn() {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now(); 
		  CreatedOn = dtf.format(now);

		
		System.out.println(CreatedOn);
		System.out.println("here");

		
		// because PreparedStatement#setDate(..) expects a java.sql.Date argument
		System.out.println(CreatedOn);

		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	public String getCreatedBy() {
		
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		String createBy=System.getProperty("user.name");
		CreatedBy = createdBy;
		System.out.println(createBy);
	}

    
    
}
