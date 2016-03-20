package com.friendshare.models;

import javax.persistence.*;

@Entity
@Table(name = "SHAREDETAILS")
public class ShareDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "shareid")
   private String shareId;

   @Column(name = "remarks")
   private String remarks;

   @Column(name = "sharetype")
   private String shareType;
   
   @Column(name = "createdby")
   private String createdBy;

   @Column(name = "sharename")
   private String shareName;

   @Column(name = "active")
   private String active;

public ShareDetails(String shareId, String remarks, String shareType,
		String createdBy, String shareName, String active) {
	super();
	this.shareId = shareId;
	this.remarks = remarks;
	this.shareType = shareType;
	this.createdBy = createdBy;
	this.shareName = shareName;
	this.active = active;
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

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getShareType() {
	return shareType;
}

public void setShareType(String shareType) {
	this.shareType = shareType;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public String getShareName() {
	return shareName;
}

public void setShareName(String shareName) {
	this.shareName = shareName;
}

public String getActive() {
	return active;
}

public void setActive(String active) {
	this.active = active;
}
   
}