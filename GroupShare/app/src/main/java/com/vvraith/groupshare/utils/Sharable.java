package com.vvraith.groupshare.utils;

import com.vvraith.groupshare.models.FriendDetails;
import com.vvraith.groupshare.models.NotificationDetails;

import java.util.List;

/**
 * Created by Administrator on 14/03/2016.
 */
public class Sharable {

    public static String token="";

    public static String mobileNo="";

    public static String appUserName="";

    public static String firstName="";

    public static String lastName="";

    public static String virtualId="";

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Sharable.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Sharable.lastName = lastName;
    }

    public static String getVirtualId() {
        return virtualId;
    }

    public static void setVirtualId(String virtualId) {
        Sharable.virtualId = virtualId;
    }

    public static List<FriendDetails> friendsList=null;

    public static List<NotificationDetails> notificationsList=null;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Sharable.token = token;
    }

    public static String getMobileNo() {
        return mobileNo;
    }

    public static void setMobileNo(String mobileNo) {
        Sharable.mobileNo = mobileNo;
    }

    public static String getAppUserName() {
        return appUserName;
    }

    public static void setAppUserName(String appUserName) {
        Sharable.appUserName = appUserName;
    }

    public static List<FriendDetails> getFriendsList() {
        return friendsList;
    }

    public static void setFriendsList(List<FriendDetails> friendsList) {
        Sharable.friendsList = friendsList;
    }

    public static List<NotificationDetails> getNotificationsList() {
        return notificationsList;
    }

    public static void setNotificationsList(List<NotificationDetails> notificationsList) {
        Sharable.notificationsList = notificationsList;
    }
}
