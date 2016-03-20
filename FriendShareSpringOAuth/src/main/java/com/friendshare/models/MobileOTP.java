package com.friendshare.models;


import javax.persistence.*;

@Entity
@Table(name = "MOBILEOTP")
public class MobileOTP {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

@Column(name = "mobileno")
   private String mobileNo;

   @Column(name = "otp")
   private String oTP;
   
   @Column(name = "status")
   private String status;
   
   public MobileOTP() {}

   public MobileOTP(String mobileNo, String oTP) {
	super();
	this.mobileNo = mobileNo;
	this.oTP = oTP;
	this.setStatus("OTP_CREATED");
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getMobileNo() {
	return mobileNo;
}

public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

public String getoTP() {
	return oTP;
}

public void setoTP(String oTP) {
	this.oTP = oTP;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}


}