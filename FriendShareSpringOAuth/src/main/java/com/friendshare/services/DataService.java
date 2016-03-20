package com.friendshare.services;


import java.util.List;

import com.friendshare.models.FriendDetails;
import com.friendshare.models.MobileOTP;
import com.friendshare.models.NotificationDetails;
import com.friendshare.models.ShareDetails;
import com.friendshare.models.ShareMemberDetails;
import com.friendshare.models.ShareTransactionDetails;
import com.friendshare.models.TransactionDetails;
import com.friendshare.models.UserDetails;
/**
 * @author 
 *
 */
public interface DataService {

	public Integer addMobileOTPDetails(MobileOTP mobileOTP);

	public MobileOTP getMobileOTPDetails(String mobileno);

	public void updateMobileOTPDetails(MobileOTP mobileOTPDetails);

	public UserDetails getUserDetails(String appusername);

	public Integer addUserDetails(UserDetails userDetails);

	public Integer addTransactionDetails(TransactionDetails transactionDetails);

	public Integer addNotificationDetails(NotificationDetails notificationDetails);

	public Integer addFriendDetails(FriendDetails friendDetails);

	public List<NotificationDetails> getNotificationDetails(String appusername);

	public List<FriendDetails> getFriendDetails(String appusername);

	public void updateUserDetails(UserDetails userDetails);

	public Integer removeFriendDetails(FriendDetails friendDetails);

	public FriendDetails getFriendDetail(String appUserName, String friendUserName);
	
	public Integer addShareDetails(ShareDetails shareDetails);
	
	public ShareDetails getShareDetail(String shareId);
	
	public List<ShareDetails> getShareDetails(String appUserName);
	
	public Integer addShareMemberDetails(ShareMemberDetails shareMemberDetails);

	public ShareMemberDetails getShareMemberDetail(String appusername,String shareid);
	
	public List<ShareMemberDetails> getShareMemberDetails(String appusername);
	
	public void updateShareMemberDetails(ShareMemberDetails shareMemberDetails);
	
	public Integer addShareTransactionDetails(ShareTransactionDetails shareTransactionDetail);

}
