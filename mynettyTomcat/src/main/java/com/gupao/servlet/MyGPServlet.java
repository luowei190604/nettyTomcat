package com.gupao.servlet;

import com.gupao.http.MyGPRequest;
import com.gupao.http.MyGPResponse;

public abstract class MyGPServlet {
	
	public void service(MyGPRequest request,MyGPResponse response){
		String method = request.getMethod();
		if(method.equalsIgnoreCase("get")){
			doGet(request,response);
		}else{
			doPost(request,response);
		}
	}
	
	protected abstract void  doGet(MyGPRequest request,MyGPResponse response);
	
	protected abstract void  doPost(MyGPRequest request,MyGPResponse response);
}
