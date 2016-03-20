package com.vvraith.groupshare.models;


import org.json.JSONException;
import org.json.JSONObject;

public class FriendDetails {

   private int id;

   private String appUserName;

   private String friendUserName;

   private String friendVirtualId;

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

	public FriendDetails(JSONObject jsonobject) throws JSONException {
		super();
		this.id = Integer.parseInt(jsonobject.getString("id"));
		this.appUserName = jsonobject.getString("appUserName");
		this.friendUserName = jsonobject.getString("friendUserName");
		this.friendVirtualId = jsonobject.getString("friendVirtualId");
		this.friendName = jsonobject.getString("friendName");
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