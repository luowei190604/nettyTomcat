package com.gupao.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class MyGPRequest {
	
	private ChannelHandlerContext context;
	
	private HttpRequest request;
	
	public MyGPRequest(ChannelHandlerContext context,HttpRequest request){
		this.context=context;
		this.request=request;
	}
	
	public String getUrl(){
		return request.uri();
	}
	
	public String getMethod(){
		return request.method().name();
	}
}
