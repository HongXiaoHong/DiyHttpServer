package com.hong.热身;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SendImg {

	private static String path = "F:\\html\\0621\\";
	/*
	通过实现Runnable定义一个任务类，
	我的理解是：
	写是不会发生阻塞的，
	开启线程的理由是因为网络延迟吗
	如果客户端发生阻塞，或者因为网络阻塞没有传送到
	客户端和服务器端都会发生阻塞
	 */
	class SendTask implements Runnable{

		Socket socket = null;
		public SendTask(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			OutputStream out = null;
			try {
				out = socket.getOutputStream();
				sendData(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public void initServer(){
		ServerSocket server = null;
		try {
			/*
			开启服务器准备接受一个客户端的连接
			接受到一个客户端之后使用线程发送图片
			 */
			server = new ServerSocket(8080);
			System.out.println("开启服务器成功");
			//开启服务器成功
			Socket socket = server.accept();
			System.out.println("接收到客户端");
			//接收到客户端
			SendTask send = new SendTask(socket);
			Thread th = new Thread(send);
			th.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String packProtocal(int size){
		//响应头
		return "HTTP/1.1 200 OK\r\n"+
				"Server: Tengine\r\n"+
				"Content-Type: image/jpg\r\n"+
				"Content-Length: "+ size +"\r\n"+
				"Connection: keep-alive\r\n"+
				"Cache-Control: max-age=86400\r\n"+
				"Last-Modified: Sat, 28 Oct 2017 06:08:41 GMT\r\n"+
				"Accept-Ranges: bytes\r\n"+
				"Age: 69030\r\n\r\n";
	}
	public void sendData(OutputStream out){
		//通过Math的random方法产生随机数
		//范围为 0-1  包括0，不包括1
		//下面产生1到3的随机数
		int ran = (int)(Math.random()*3)+1;
		//拼接图片的地址
		path = path+ran+".jpg";
		InputStream in = null;
		try {
			//通过FileInputStream读取图片的字节流
			in =new FileInputStream(path);
			//将响应头写到客户端
			int len = in.available();
			out.write(packProtocal(len).getBytes());
			//将图片内容写到客户端
			int count = 0;
			byte[] buffer = new byte[1024];
			while((count = in.read(buffer ))>0){
				out.write(buffer, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		//TEST
		SendImg sendImg = new SendImg();
		sendImg.initServer();
	}

}
