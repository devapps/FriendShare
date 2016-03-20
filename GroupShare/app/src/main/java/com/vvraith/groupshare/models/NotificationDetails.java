package com.vvraith.groupshare.models;


import org.json.JSONException;
import org.json.JSONObject;

public class NotificationDetails {

	private int id;

   private String appUserName;

   private String notification;

   private String remarks;

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
	public NotificationDetails(JSONObject jsonobject) throws JSONException{
		super();
		this.id = Integer.parseInt(jsonobject.getString("id"));
		this.appUserName = jsonobject.getString("appUserName");
		this.notification = jsonobject.getString("notification");
		this.remarks = jsonobject.getString("remarks");
		this.transactionid = jsonobject.getString("transactionid");
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