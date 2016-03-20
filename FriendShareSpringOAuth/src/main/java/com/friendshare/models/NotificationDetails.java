package com.friendshare.models;


import javax.persistence.*;

@Entity
@Table(name = "NOTIFICATIONDETAILS")
public class NotificationDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "notification")
   private String notification;
   
   @Column(name = "remarks")
   private String remarks;

   @Column(name = "transactionid")
   private String transactionid;
   
   public NotificationDetails() {}

public NotificationDetails(String appUserName, String notification,
		String remarks, String transactionid) {
	super();
	this.appUserName = appUserName;
	this.notification = notification;
	this.remarks = remarks;
	this.transactionid = transactionid;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getAppUserName() {
	return appUserName;
}

public void setAppUserName(String appUserName) {
	this.appUserName = appUserName;
}

public String getNotification() {
	return notification;
}

public void setNotification(String notification) {
	this.notification = notification;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getTransactionid() {
	return transactionid;
}

public void setTransactionid(String transactionid) {
	this.transactionid = transactionid;
}

}