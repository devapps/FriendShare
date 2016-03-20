package com.friendshare.services;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private SessionFactory sessionFactory;
	
	public static final String OK_RESPONSE="OK";
	public static final String FAILED_RESPONSE="FAILED";

	@Override
	public Integer addMobileOTPDetails(MobileOTP mobileOTP){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(mobileOTP); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }
	
	@Override
	public void updateMobileOTPDetails(MobileOTP mobileOTP){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try{
	         tx = session.beginTransaction();
	         session.update(mobileOTP); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }

	@Override
	public MobileOTP getMobileOTPDetails(String mobileno){
		Session session = sessionFactory.openSession();
		MobileOTP mobileOTP=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(MobileOTP.class);
	 		 cr.add(Restrictions.eq("mobileNo", mobileno));
	 		 cr.addOrder(Order.desc("id"));
	 		mobileOTP =  (MobileOTP) cr.list().get(0);  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return mobileOTP;
	}
	
	@Override
	public Integer addUserDetails(UserDetails userDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(userDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }
	
	@Override
	public void updateUserDetails(UserDetails userDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try{
	         tx = session.beginTransaction();
	         session.update(userDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	
	@Override
	public UserDetails getUserDetails(String appUserName){
		Session session = sessionFactory.openSession();
		UserDetails userDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(UserDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		 if(!cr.list().isEmpty())
	 		userDetails =  (UserDetails) cr.list().get(0); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return userDetails;
	}
	
	@Override
	public List<NotificationDetails> getNotificationDetails(String appUserName){
		Session session =sessionFactory.openSession();
		List<NotificationDetails> notificationDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(NotificationDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		 cr.addOrder( Order.desc("id") );
	 		notificationDetails = cr.list();  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return notificationDetails;
	}
	
	@Override
	public Integer addNotificationDetails(NotificationDetails notificationDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(notificationDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }
	
	@Override
	public List<FriendDetails> getFriendDetails(String appUserName){
		Session session = sessionFactory.openSession();
		List<FriendDetails> friendDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(FriendDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		cr.addOrder( Order.asc("friendName") );
	 		friendDetails = cr.list();  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return friendDetails;
	}
	
	@Override
	public FriendDetails getFriendDetail(String appUserName, String friendUserName){
		Session session = sessionFactory.openSession();
		FriendDetails friendDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(FriendDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		cr.add(Restrictions.eq("friendusername", friendUserName));
	 		friendDetails = (FriendDetails) cr.list().get(0);  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return friendDetails;
	}
	
	@Override
	public Integer addFriendDetails(FriendDetails friendDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(friendDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }
	
	@Override
	public Integer removeFriendDetails(FriendDetails friendDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         session.delete(friendDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }
	
	@Override
	public Integer addTransactionDetails(TransactionDetails transactionDetails){
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(transactionDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	   }

	@Override
	public Integer addShareDetails(ShareDetails shareDetails) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(shareDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	}

	@Override
	public Integer addShareMemberDetails(ShareMemberDetails shareMemberDetails) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(shareMemberDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	}

	@Override
	public ShareMemberDetails getShareMemberDetail(String appusername, String shareid) {
		Session session = sessionFactory.openSession();
		ShareMemberDetails shareMemberDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(UserDetails.class);
	 		 cr.add(Restrictions.eq("appusername", appusername));
	 		cr.add(Restrictions.eq("shareid", shareid));
	 		 if(!cr.list().isEmpty())
	 			shareMemberDetails =  (ShareMemberDetails) cr.list().get(0);  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return shareMemberDetails;
	}

	@Override
	public void updateShareMemberDetails(ShareMemberDetails shareMemberDetails) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try{
	         tx = session.beginTransaction();
	         session.update(shareMemberDetails); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	}

	@Override
	public Integer addShareTransactionDetails(
			ShareTransactionDetails shareTransactionDetail) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      Integer transactionID = null;
	      try{
	         tx = session.beginTransaction();
	         transactionID = (Integer) session.save(shareTransactionDetail); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return transactionID;
	}

	@Override
	public ShareDetails getShareDetail(String shareId) {
		Session session = sessionFactory.openSession();
		ShareDetails shareDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(UserDetails.class);
	 		 cr.add(Restrictions.eq("shareId", shareId));
	 		 if(!cr.list().isEmpty())
	 			shareDetails =  (ShareDetails) cr.list().get(0);  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return shareDetails;
	}

	@Override
	public List<ShareDetails> getShareDetails(String appUserName) {
		Session session =sessionFactory.openSession();
		List<ShareDetails> shareDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(NotificationDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		shareDetails = cr.list();  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return shareDetails;
	}
	
	@Override
	public List<ShareMemberDetails> getShareMemberDetails(String appUserName) {
		Session session =sessionFactory.openSession();
		List<ShareMemberDetails> shareMemberDetails=null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(NotificationDetails.class);
	 		 cr.add(Restrictions.eq("appUserName", appUserName));
	 		shareMemberDetails = cr.list();  
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return shareMemberDetails;
	}
	
}
