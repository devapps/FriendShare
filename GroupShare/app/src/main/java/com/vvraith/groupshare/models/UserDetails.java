package com.vvraith.groupshare.models;


import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails {

   private int id;

   private String appUserName;

   private String appPin;

   private String virtualId;

   private String firstName;

   private String lastName;

   private String mobileNo;
   
   public UserDetails() {}

public UserDetails(String appUserName, String appPin, String virtualId,
		String firstName, String lastName, String mobileNo) {
	super();
	this.appUserName = appUserName;
	this.appPin = appPin;
	this.virtualId = virtualId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.mobileNo = mobileNo;
}

	public UserDetails(JSONObject jsonobject) throws JSONException {
		super();
		this.id = Integer.parseInt(jsonobject.getString("id"));
		this.appUserName = jsonobject.getString("appUserName");
		this.appPin = jsonobject.getString("appPin");
		this.virtualId = jsonobject.getString("virtualId");
		this.firstName = jsonobject.getString("firstName");
		this.lastName = jsonobject.getString("lastName");
		this.mobileNo = jsonobject.getString("mobileNo");
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

public String getAppPin() {
	return appPin;
}

public void setAppPin(String appPin) {
	this.appPin = appPin;
}

public String getVirtualId() {
	return virtualId;
}

public void setVirtualId(String virtualId) {
	this.virtualId = virtualId;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getMobileNo() {
	return mobileNo;
}

public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

}