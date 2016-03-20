package com.friendshare.models;

import javax.persistence.*;

@Entity
@Table(name = "SHAREMEMBERDETAILS")
public class ShareMemberDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "shareid")
   private String shareId;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "sharename")
   private String shareName;
   
   @Column(name = "totalpaid")
   private String totalPaid;
   
   @Column(name = "currentamount")
   private String currentAmount;

public ShareMemberDetails(String shareId, String appUserName, String shareName,
		String totalPaid, String currentAmount) {
	super();
	this.shareId = shareId;
	this.appUserName = appUserName;
	this.shareName = shareName;
	this.totalPaid = totalPaid;
	this.currentAmount = currentAmount;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
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

public String getTotalPaid() {
	return totalPaid;
}

public void setTotalPaid(String totalPaid) {
	this.totalPaid = totalPaid;
}

public String getCurrentAmount() {
	return currentAmount;
}

public void setCurrentAmount(String currentAmount) {
	this.currentAmount = currentAmount;
}
   
   
}
