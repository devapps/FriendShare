package com.friendshare.models;


import javax.persistence.*;

@Entity
@Table(name = "FRIENDDETAILS")
public class FriendDetails {

@Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "appusername")
   private String appUserName;

   @Column(name = "friendusername")
   private String friendUserName;

   @Column(name = "friendvirtualid")
   private String friendVirtualId;

   @Column(name = "friendname")
   private String friendName;
   
   public FriendDetails() {}

public FriendDetails(String appUserName, String friendUserName,
		String friendVirtualId, String friendName) {
	super();
	this.appUserName = appUserName;
	this.friendUserName = friendUserName;
	this.friendVirtualId = friendVirtualId;
	this.friendName = friendName;
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

public String getFriendUserName() {
	return friendUserName;
}

public void setFriendUserName(String friendUserName) {
	this.friendUserName = friendUserName;
}

public String getFriendVirtualId() {
	return friendVirtualId;
}

public void setFriendVirtualId(String friendVirtualId) {
	this.friendVirtualId = friendVirtualId;
}

public String getFriendName() {
	return friendName;
}

public void setFriendName(String friendName) {
	this.friendName = friendName;
}

}