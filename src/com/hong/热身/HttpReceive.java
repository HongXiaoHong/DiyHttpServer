package com.hong.热身;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/*
@Author:洪晓鸿
function:编写一个简单的服务器接受浏览器的访问，
并且将访问的信息通过控制进行输出

 */
public class HttpReceive {
//Link:   http://localhost:8088/
	public static void main(String[] args) {
		HttpReceive receive = new HttpReceive();
		receive.initServer();
	}
	//开启服务的端口，也就是访问地址时需要写上的端口
	private static final int port = 8088;
	public void initServer() {
		Socket socket = null;
		ServerSocket server;
		try {
			//通过ServerSocket构造器开启服务器
			server = new ServerSocket(port);
			System.out.println("开启服务器成功");
			
			
			final byte[] buffer = new byte[1024];
			//接受客户端的连接，这里如果没有客户端连接会发生阻塞
			socket = server.accept();
			InetAddress inetAddress = socket.getInetAddress();
			System.out.println("有客户端连接进来，端口号为："+inetAddress.getHostName());
			//从接受的客户端得到输入流
			final InputStream in = socket.getInputStream();
			//socket.setSoTimeout(2000);
			//开启一个线程接受客户端的信息
			(new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int len = 0;
					String temp = null;
					try{
						
						while(true){
							//因为read方法会产生阻塞，所以这里开启线程进行接受
							//如果不开启线程就要设置线程的超时
							len = in.read(buffer);
							if(len>0){
								temp = new String(buffer,0,len);
								System.out.println("temp  :  "+temp);
							}else{
								break;
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				
			})).start();
			
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}
}
