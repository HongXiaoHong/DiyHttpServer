package com.hong.one;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
	(a) 动手写一个服务器
	(a)-1: 绑定端口号: 8080。
	(a)-2: 负责接收客户端的请求
		   你就给它发一个资源 (图片)
		(d)-1: 拼接响应头。
		(d)-2: 连同资源的内容一起发送给浏览器。
*/
public class SendImage {
	
	public void initServer(){
		ServerSocket server = null;
		try {
			server = new ServerSocket(8080);
			System.out.println("[SERVER] 服务器已开启 ..");
			while( true ){
				Socket socket = server.accept();
				SocketTask task = new SocketTask(socket);
				(new Thread( task )).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//[1] 编写一个内部类, 来处理 Socket 套接字。
	class SocketTask implements Runnable {
		Socket socket;
		public SocketTask(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			OutputStream os = null;
			try {
				os = socket.getOutputStream();
				sendImage( os );
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try { socket.close(); }
				catch (IOException e) { }
			}
		}
	}

	//模块 [1] 拼接响应头 (已完成)
	public String packResponse(int size){
		return  "HTTP/1.1 200 OK\r\n"+
				"Server: Tengine\r\n"+
				"Content-Type: image/jpg\r\n"+
				"Content-Length: "+ size +"\r\n"+
				"Connection: keep-alive\r\n"+
				"Cache-Control: max-age=86400\r\n"+
				"Last-Modified: Sat, 28 Oct 2017 06:08:41 GMT\r\n"+
				"Accept-Ranges: bytes\r\n"+
				"Age: 69030\r\n\r\n";
	}
	
	//模块[2] 发送图片
	public void sendImage(OutputStream os){
		String basePath = "E:\\dir\\images\\";
		int ran = (int)(Math.random()*3)+1;
		String path = basePath +"p"+ ran +".jpg";   //[PS] 确认选中哪一幅图片..
		
		FileInputStream is = null;
		String line = null;
		try {
			//[1] 获取图片的输入流。
			is = new FileInputStream( path );
			//[2] 拿到 "图片" 的大小。
			//[3] 调用 packResponse 生成响应头。
			line = packResponse( is.available() );
			//[4] 发送响应头的数据 (通过 os 来发)。
			os.write( line.getBytes() );
			//[5] 边读(图片), 边写到客户端 (通过 os 来写)。
			byte[] buff = new byte[8192];
			int count = 0;
			while( is.available()>0 ){       //确认有数据可读 ..
				count = is.read( buff );
				os.write( buff, 0, count );  //读多少写多少..
				System.out.println("[SERVER] write:"+ count );
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SendImage send = new SendImage();
		send.initServer();
	}
	
}
