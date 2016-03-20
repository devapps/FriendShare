package com.friendshare.models;

import javax.persistence.*;

@Entity
@Table(name = "SHARETRANSACTIONDETAILS")
public class ShareTransactionDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

@Column(name = "transactionid")
private String transactionId;

   @Column(name = "shareid")
   private String shareId;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "sharename")
   private String shareName;
   
   @Column(name = "shareamount")
   private String shareAmount;
   
   @Column(name = "remarks")
   private String remarks;
   
   @Column(name = "paidfor")
   private String paidFor;

public ShareTransactionDetails(String transactionId, String shareId,
		String appUserName, String shareName, String shareAmount,
		String remarks, String paidFor) {
	super();
	this.transactionId = transactionId;
	this.shareId = shareId;
	this.appUserName = appUserName;
	this.shareName = shareName;
	this.shareAmount = shareAmount;
	this.remarks = remarks;
	this.paidFor = paidFor;
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

public String getShareId() {
	return shareId;
}

public void setShareId(String shareId) {
	this.shareId = shareId;
}

public String getAppUserName() {
	return appUserName;
}

public void setAppUserName(String appUserName) {
	this.appUserName = appUserName;
}

public String getShareName() {
	return shareName;
}

public void setShareName(String shareName) {
	this.shareName = shareName;
}

public String getShareAmount() {
	return shareAmount;
}

public void setShareAmount(String shareAmount) {
	this.shareAmount = shareAmount;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getPaidFor() {
	return paidFor;
}

public void setPaidFor(String paidFor) {
	this.paidFor = paidFor;
}
   
   
}