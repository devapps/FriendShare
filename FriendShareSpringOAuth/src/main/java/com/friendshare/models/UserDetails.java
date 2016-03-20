package com.friendshare.models;


import javax.persistence.*;

@Entity
@Table(name = "USERDETAILS")
public class UserDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "apppin")
   private String appPin;
   
   @Column(name = "virtualid")
   private String virtualId;

   @Column(name = "firstname")
   private String firstName;
   
   @Column(name = "lastname")
   private String lastName;

   @Column(name = "mobileno")
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