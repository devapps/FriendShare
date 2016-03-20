package com.friendshare.response;

import java.util.ArrayList;
import java.util.List;

public class MyResponse {
private String response;
private Object result=new Object();
private List resultList=new ArrayList();
public MyResponse() {
	super();
}
public MyResponse(String response) {
	super();
	this.response = response;
}
public String getResponse() {
	return response;
}

public void setResponse(String response) {
	this.response = response;
}
public Object getResult() {
	return result;
}
public void setResult(Object result) {
	this.result = result;
}
public List getResultList() {
	return resultList;
}
public void setResultList(List resultList) {
	this.resultList = resultList;
}
}
