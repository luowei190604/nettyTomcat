package com.gupao.http;

import java.nio.ByteBuffer;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class MyGPResponse {

	private ChannelHandlerContext context;
	
	
	public MyGPResponse(ChannelHandlerContext context){
		this.context=context;
	}
	
	public void response(String msg){
		if(Objects.isNull(msg)){
			return ;
		};
		try{
			FullHttpResponse fullResponse = 
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, 
							HttpResponseStatus.OK,
							Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
			//fullResponse.replace(Unpooled.wrappedBuffer(msg.getBytes()));
			fullResponse.headers().set("Context-Type","text/html;");
			
			context.write(fullResponse);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			context.flush();
			context.close();
		}
		
	}
}
