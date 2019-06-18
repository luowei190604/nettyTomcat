package com.gupao;

import java.nio.channels.ServerSocketChannel;

import com.gupao.http.MyGPRequest;
import com.gupao.http.MyGPResponse;
import com.gupao.servlet.MyFirstServletImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyNettyTomcat {
	
	private int port=8080;
	
	public static void main(String[] args) throws Exception {
		new MyNettyTomcat().startTomcat();
  }
	public class MyGPTomcatHandler extends ChannelInboundHandlerAdapter{

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			//System.out.println(msg.toString());
			System.out.println("hello world ");
			if(msg instanceof HttpRequest){
				HttpRequest request=(HttpRequest)msg;
				MyGPRequest myGPRequest = new MyGPRequest(ctx,request);
				MyGPResponse myGPResponse = new MyGPResponse(ctx);
				//获得处理资源
				String url = myGPRequest.getUrl();
				if(url.equalsIgnoreCase("/firstServlet.do")){
					MyFirstServletImpl newInstance = MyFirstServletImpl.class.newInstance();
					newInstance.service(myGPRequest, myGPResponse);
				}else{
					myGPResponse.response("NOT FOUND ARE YOU Stupid");
				}
				
			}else{
				throw new Exception("不知道你传的是什么东西,shit");
			}
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		}
		
	}
	public  void startTomcat() {
		//新建2个线程池 一个主线程 一个work线程
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try{
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup,workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel client) throws Exception {
					client.pipeline().addLast(new HttpResponseEncoder());
					client.pipeline().addLast(new HttpRequestDecoder());
					client.pipeline().addLast(new MyGPTomcatHandler());
				}
				
			}).option(ChannelOption.SO_BACKLOG, 10)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = server.bind(port).sync();
			System.out.println("端口号已经启动，等待连接");
			future.channel().closeFuture().sync();
			
			
//			ChannelFuture f = server.bind(port).sync();
//			System.out.println("GP Tomcat 已启动，监听的端口是：" + port);
//			f.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
}

