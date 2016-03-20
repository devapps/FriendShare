package com.vvraith.groupshare.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;

import com.vvraith.groupshare.models.FriendDetails;
import com.vvraith.groupshare.models.NotificationDetails;
import com.vvraith.groupshare.models.UserDetails;

/**
 * Created by Administrator on 06/03/2016.
 */
public class RequestExecutor extends AsyncTask<String,String,String>{

    public static final String HTTP_URL = "http://friendsharespringoauth.mybluemix.net";
    Map<String,String> inputMap=null;
    String requestUrl=null;
    MyAppActivity myAppActivity=null;
    String task="";

    public RequestExecutor(Map<String, String> inputMap, String requestUrl, MyAppActivity myAppActivity, String task) {
        this.inputMap = inputMap;
        this.requestUrl = HTTP_URL+requestUrl;
        this.myAppActivity = myAppActivity;
        this.task = task;
    }

    public static String execute(String requestUrl, Map<String,String> inputMap){
        StringBuilder response = new StringBuilder();
        try{
            String inputData="";
            for(String key: inputMap.keySet()){
                inputData+=key+"="+ URLEncoder.encode(inputMap.get(key), "ISO-8859-1")+"&";
            }
            requestUrl += inputData;
            URL url = new URL(requestUrl);

            System.out.println("response using url--->" + url);

            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }
            System.out.println("response using URL--->" + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    @Override
    protected String doInBackground(String... strings) {
        String resp=null;
        try{
        resp = execute(requestUrl, inputMap);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("response data from server--->" +  s);
        try {
            JSONObject jsonobject = new JSONObject(s);
            if (task.equals("LOGIN")) {
                if (jsonobject.has("value")) {
                    String data = jsonobject.getString("value");
                    System.out.println("response data--->" + data);
                    Sharable.setToken(data);
                    myAppActivity.onStep1Success();
                } else {
                    myAppActivity.onFailure();
                }
            } else {
                if (jsonobject.has("response") && jsonobject.getString("response").equals("OK")) {
                    System.out.println("response data--->" + s);

                            if (task.equals("NOTIFICATIONS")){
                                List<NotificationDetails> dataList = new ArrayList<>();
                                JSONArray jsonArray = jsonobject.getJSONArray("resultList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    dataList.add(new NotificationDetails(jsonArray.getJSONObject(i)));
                                }
                                UserDetails userDetails=new UserDetails(jsonobject.getJSONObject("result"));
                                Sharable.setMobileNo(userDetails.getMobileNo());
                                Sharable.setFirstName(userDetails.getFirstName());
                                Sharable.setLastName(userDetails.getLastName());
                                Sharable.setVirtualId(userDetails.getVirtualId());
                                Sharable.setNotificationsList(dataList);
                            }

                            if (task.equals("FRIENDS")){
                                List<FriendDetails> dataList = new ArrayList<>();
                                JSONArray jsonArray = jsonobject.getJSONArray("resultList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    dataList.add(new FriendDetails(jsonArray.getJSONObject(i)));
                                }
                                Sharable.setFriendsList(dataList);
                                myAppActivity.onStep1Success();
                            }
                    else
                            { myAppActivity.onSuccess();}
                } else {
                    myAppActivity.onFailure();
                    }
                }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




