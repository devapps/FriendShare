package com.friendshare.controllers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.friendshare.models.FriendDetails;
import com.friendshare.models.MobileOTP;
import com.friendshare.models.NotificationDetails;
import com.friendshare.models.ShareDetails;
import com.friendshare.models.ShareMemberDetails;
import com.friendshare.models.ShareTransactionDetails;
import com.friendshare.models.TransactionDetails;
import com.friendshare.models.UserDetails;
import com.friendshare.response.MyResponse;
import com.friendshare.services.DataService;

/**
 * @author 
 *
 */
@Controller
public class RestController {

	@Autowired
	DataService dataService;
	
	public static final String OK_RESPONSE="OK";
	public static final String FAILED_RESPONSE="FAILED";
	public static final String FAILED_USERNAME_EXISTS_RESPONSE="FUSERNAME";
	
	@RequestMapping(value = "/generateotp", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse generateOTP(@RequestParam(value="mobileno") String mobileno) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		Random r = new Random();
			  String otp = ""+r.nextInt(9)+r.nextInt(9)+r.nextInt(9)+r.nextInt(9);
			  Integer genId=dataService.addMobileOTPDetails(new MobileOTP(mobileno,otp));
			  System.out.println("Row added with ID: "+genId);
			  try{
				
				  //TODO LASTMOMENT Uncomment below line to get sms
				//if(SMSService.sendSMS(toMobile,otp))
			    {System.out.println("Message was sent successfully " );
			    response.setResponse(OK_RESPONSE);
			    }
			  }catch(Exception e){
			   System.out.println("Unable to send message, possible cause: " + e.getMessage());
			  }
		return response;
	}
	
	@RequestMapping(value = "/verifyotp", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse verifyOTP(@RequestParam(value="mobileno") String mobileno,@RequestParam(value="otp") String otp) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		MobileOTP mobileOTPDetails = dataService.getMobileOTPDetails(mobileno);
		String correctOtp = mobileOTPDetails.getoTP();
			if(otp.equals(correctOtp))
				{
				mobileOTPDetails.setStatus("VERIFIED");
				dataService.updateMobileOTPDetails(mobileOTPDetails);
				response.setResponse(OK_RESPONSE);
				}
	return response;
	}
	
	@RequestMapping(value = "/api/getuserdetails", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getuserdetails(@RequestParam(value="appusername") String appusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			UserDetails userDetails=dataService.getUserDetails(appusername);
			userDetails.setAppPin("XXXXX");
			response.setResult(userDetails);
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/api/updateuserdetails", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse updateuserdetails(@RequestParam(value="appusername") String appusername,@RequestParam(value="virtualid") String virtualid, @RequestParam(value="firstname") String firstname,@RequestParam(value="lastname") String lastname) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			UserDetails userDetails=dataService.getUserDetails(appusername);
			userDetails.setVirtualId(virtualid);
			userDetails.setFirstName(firstname);
			userDetails.setLastName(lastname);
			dataService.updateUserDetails(userDetails);
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/api/changeuserpassword", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse changeuserpassword(@RequestParam(value="appusername") String appusername, @RequestParam(value="apppin")String apppin, @RequestParam(value="newapppin")String newapppin) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			UserDetails userDetails=dataService.getUserDetails(appusername);
			if(userDetails.getAppPin().equals(apppin)){
			userDetails.setAppPin(newapppin);
			dataService.updateUserDetails(userDetails);
			response.setResponse(OK_RESPONSE);
			}
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/createuser", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse createUser(@RequestParam(value="mobileno") String mobileno,@RequestParam(value="virtualid") String virtualid, @RequestParam(value="firstname") String firstname,@RequestParam(value="lastname") String lastname,@RequestParam(value="appusername") String appusername, @RequestParam(value="apppin") String apppin) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			if(dataService.getUserDetails(appusername)!=null)
			{
				response.setResponse(FAILED_USERNAME_EXISTS_RESPONSE);
			}
			if(dataService.getMobileOTPDetails(mobileno).getStatus().equals("VERIFIED") && dataService.getUserDetails(appusername)==null)
				{Integer genId=dataService.addUserDetails(new UserDetails(appusername, apppin, virtualid,
						firstname,lastname,mobileno));
				System.out.println("Row added with ID: "+genId);
				response.setResponse(OK_RESPONSE);
				}
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/api/paymoney/friend", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse payMoneyToFriend(@RequestParam(value="transactionid") String transactionid,@RequestParam(value="appusername") String appusername, @RequestParam(value="tousername") String tousername,  @RequestParam(value="amount") String amount,@RequestParam(value="remarks") String remarks,@RequestParam(value="myname") String myname,@RequestParam(value="friendname") String friendname) {
		//Pay request to PSP APP
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			dataService.addTransactionDetails(new TransactionDetails(transactionid, appusername,
					tousername, amount, remarks));
			dataService.addNotificationDetails(new NotificationDetails(appusername,"You paid Rs "+amount+" to "+friendname,
					remarks,transactionid));
			dataService.addNotificationDetails(new NotificationDetails(tousername,friendname+" paid Rs "+amount+" to You",
					remarks,transactionid));
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/api/paymoney/virtualid", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse payMoneyToVPA(@RequestParam(value="transactionid") String transactionid,@RequestParam(value="appusername") String appusername,@RequestParam(value="virtualid") String virtualid,  @RequestParam(value="amount") String amount,@RequestParam(value="remarks") String remarks,@RequestParam(value="myname") String myname) {
		//Pay request to PSP APP
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			dataService.addTransactionDetails(new TransactionDetails(transactionid, appusername,
					virtualid, amount, remarks));
			dataService.addNotificationDetails(new NotificationDetails(appusername,"You paid Rs "+amount+" to "+virtualid,
					remarks,transactionid));
			response.setResponse(OK_RESPONSE);
			}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/collectmoney/friend", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse requestMoneyFromFriend(@RequestParam(value="transactionid") String transactionid,@RequestParam(value="appusername") String appusername, @RequestParam(value="tousername") String tousername,  @RequestParam(value="amount") String amount,@RequestParam(value="remarks") String remarks,@RequestParam(value="myname") String myname,@RequestParam(value="friendname") String friendname) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			dataService.addNotificationDetails(new NotificationDetails(appusername,"You requested Rs "+amount+" from "+friendname,
					remarks,transactionid));
			dataService.addNotificationDetails(new NotificationDetails(tousername,myname+" requested Rs "+amount+" from You",
					remarks,transactionid));
			response.setResponse(OK_RESPONSE);
			}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/collectmoney/virtualid", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse requestMoneyFromVPA(@RequestParam(value="transactionid") String transactionid,@RequestParam(value="appusername") String appusername, @RequestParam(value="virtualid") String virtualid,  @RequestParam(value="amount") String amount,@RequestParam(value="remarks") String remarks,@RequestParam(value="myname") String myname) {
		//TODO NOTURGENT Collect request to PSP APP
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			dataService.addNotificationDetails(new NotificationDetails(appusername,"You requested Rs "+amount+" from "+virtualid,
					remarks,transactionid));
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/addfriend", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse addFriend(@RequestParam(value="appusername") String appusername,@RequestParam(value="friendusername") String friendusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			UserDetails friendDetails=dataService.getUserDetails(friendusername);
			if(friendDetails!=null)
			{
				dataService.addFriendDetails(new FriendDetails(appusername, friendusername, friendDetails.getVirtualId(), friendDetails.getFirstName()+" "+friendDetails.getLastName()));
				response.setResponse(OK_RESPONSE);
			}
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/removefriend", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse removeFriend(@RequestParam(value="appusername") String appusername,@RequestParam(value="friendusername") String friendusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			FriendDetails friendDetails=dataService.getFriendDetail(appusername,friendusername);
			dataService.removeFriendDetails(friendDetails);
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/listnotifications", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse listNotifications(@RequestParam(value="appusername") String appusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			List<NotificationDetails> notificationDetails=dataService.getNotificationDetails(appusername);
			response.setResultList(notificationDetails);
			response.setResult(dataService.getUserDetails(appusername));
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/listfriends", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse listFriends(@RequestParam(value="appusername") String appusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			List<FriendDetails> friendDetails=dataService.getFriendDetails(appusername);
			response.setResultList(friendDetails);
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/api/share/addshare", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse addShare(@RequestParam(value="shareid") String shareid,@RequestParam(value="remarks") String remarks,@RequestParam(value="sharetype") String sharetype,@RequestParam(value="createdby") String createdby,@RequestParam(value="sharename") String sharename,@RequestParam(value="active") String active) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			if(dataService.getShareDetails(shareid)!=null)
			{
				dataService.addShareDetails(new ShareDetails(shareid, remarks, sharetype,
						createdby, sharename, active));
				response.setResponse(OK_RESPONSE);
			}
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/share/listshare", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse listShare(@RequestParam(value="appusername") String appusername) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			List<ShareMemberDetails> shareMemberDetails=dataService.getShareMemberDetails(appusername);
			response.setResultList(shareMemberDetails);
			response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/share/addsharemembers", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse addShareMembers(@RequestParam(value="shareid") String shareid,@RequestParam(value="appusernames[]") List<String> appusernames,@RequestParam(value="sharename") String sharename) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			for (String appusername : appusernames) {
				dataService.addShareMemberDetails(new ShareMemberDetails(shareid, appusername, sharename,
					 "0", "0"));
			}
				response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
	
	@RequestMapping(value = "/api/share/addsharetransaction", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse addShareTransaction(@RequestParam(value="transactionid") String transactionid,@RequestParam(value="shareid") String shareid,@RequestParam(value="appusernames[]") List<String> appusernames,@RequestParam(value="shareamounts[]") List<String> shareamounts,@RequestParam(value="paidfors[]") List<String> paidfors,@RequestParam(value="sharename") String sharename,@RequestParam(value="remarks") String remarks,@RequestParam(value="myname") String myname) {
		MyResponse response=new MyResponse(FAILED_RESPONSE);
		try{
			ShareMemberDetails shareMemberDetails;
			for (String appusername : appusernames) {
				int index=appusernames.indexOf(appusername);
				float shareamount=Float.parseFloat(shareamounts.get(index));
				shareMemberDetails=dataService.getShareMemberDetail(appusername,shareid);
				if(shareamount>0){
					shareMemberDetails.setTotalPaid(String.valueOf(shareamount + Float.parseFloat(shareMemberDetails.getTotalPaid())));
					dataService.addNotificationDetails(new NotificationDetails(shareid, myname+" paid Rs "+String.valueOf(shareamount), remarks, transactionid));
				}
				shareMemberDetails.setCurrentAmount(String.valueOf(shareamount + Float.parseFloat(shareMemberDetails.getCurrentAmount())));
				dataService.updateShareMemberDetails(shareMemberDetails);
				dataService.addShareTransactionDetails(new ShareTransactionDetails(transactionid, shareid,
						appusername, sharename, String.valueOf(shareamount),
						remarks, paidfors.get(index)));
				}
				response.setResponse(OK_RESPONSE);
		}
		catch(Exception e){
			response.setResponse(e.getMessage());
		}
		return response;
		}
}
