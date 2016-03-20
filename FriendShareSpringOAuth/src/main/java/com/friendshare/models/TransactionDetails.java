package com.friendshare.models;


import javax.persistence.*;

@Entity
@Table(name = "TRANSACTIONDETAILS")
public class TransactionDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "transactionid")
   private String transactionId;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "tousername")
   private String toUserName;

   @Column(name = "amount")
   private String amount;

   @Column(name = "remarks")
   private String remarks;
   
   public TransactionDetails() {}

public TransactionDetails(String transactionId, String appUserName,
		String toUserName, String amount, String remarks) {
	super();
	this.transactionId = transactionId;
	this.appUserName = appUserName;
	this.toUserName = toUserName;
	this.amount = amount;
	this.remarks = remarks;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getTransactionId() {
	return transactionId;
}

public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}

public String getAppUserName() {
	return appUserName;
}

public void setAppUserName(String appUserName) {
	this.appUserName = appUserName;
}

public String getToUserName() {
	return toUserName;
}

public void setToUserName(String toUserName) {
	this.toUserName = toUserName;
}

public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

}