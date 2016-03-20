package com.friendshare.services;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class SMSService {
//To create apiKey go to https://2factor.in/CP/register.php
 static String apiKey = "5b295f1e-dfb8-11e5-9a14-00163ef91450";
 private static DefaultHttpClient httpclient=new DefaultHttpClient();

 public static boolean sendSMS(String toMobile,String otp) throws ClientProtocolException, IOException{
  HttpPost httpost = new HttpPost("https://2factor.in/API/V1/"+apiKey+"/SMS/"+toMobile+"/"+otp);
  HttpResponse response = httpclient.execute(httpost);
  HttpEntity entity = response.getEntity();
  if(entity != null) {
     System.out.println("entity " + response.getStatusLine().getStatusCode());
   return true;
  }
  return false;
 }
}