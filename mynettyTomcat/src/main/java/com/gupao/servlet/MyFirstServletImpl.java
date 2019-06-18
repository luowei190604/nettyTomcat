package com.gupao.servlet;

import com.gupao.http.MyGPRequest;
import com.gupao.http.MyGPResponse;

public class MyFirstServletImpl extends MyGPServlet{

	@Override
	protected void doGet(MyGPRequest request,MyGPResponse response) {
		doPost(request,response);
	}

	@Override
	protected void doPost(MyGPRequest request,MyGPResponse response) {
		response.response("hello world this is my first netty tomcat servlet");
	}

}
