package com.hong.热身;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPSend {

	
	public static void main(String[] args) {
		// 请求某一网址后将资源下载到本地
		HTTPSend send = new HTTPSend();
		send.initClient();
	}

	private String path = "F:\\html\\0620\\aa.jpg";
	private int port = 80;
	public void initClient(){
		try {
			//通过地址和端口和菜鸟教程取得连接
			final Socket socket = new Socket("183.61.14.102",port);
			System.out.println("连接成功");
			//请求头内容，以\r\n分隔
			String content   = 	"GET http://www.runoob.com/wp-content/uploads/2013/10/bs.png HTTP/1.1\r\n"+
								"Host:183.61.14.102:80\r\n"+
								"Connection:keep-alive\r\n"+
								"User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\r\n"+
								"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n"+
								"Accept-Encoding: gzip, deflate, br\r\n"+
								"Accept-Language: zh-CN,zh;q=0.9\r\n\r\n";
			//得到输出流向菜鸟教程发送请求头
			OutputStream out = socket.getOutputStream();
			out.write(content.getBytes());
			final InputStream in = socket.getInputStream();
			socket.setSoTimeout(2000);
			(new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					byte[] buffer = new byte[1024];
					int count = 0;
					int index = -1;
					int offset = 0;
					OutputStream os = null;
					try {
						os = new FileOutputStream(path);
						count = in.read(buffer);
						
						if( count>0 ){
							String temp = new String(buffer,0,count);
							//为何可以使用char计算出byte数组的下标
							//因为 一个字母或者说可以使用ascii表示的都使用一个字节
							//读取到字符串中虽然编程了char类型
							//但是一个字母依旧占用一个字符，所以得到的下标便是原来byte数组的下标
							index = temp.indexOf("\r\n\r\n");//\r\n\r\n就是响应头以及内容之间的分隔符
							offset = index + 4;//计算出响应头以及分隔符的下标
							os.write(buffer, offset, count-offset);
							
						}
						//如果还有内容，使用循环继续读取
						while( (count=in.read(buffer))>0 ){
							os.write(buffer, 0, count);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							if(os!=null)
							os.close();
							if(socket!=null)
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					
				}
				
			})).start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}




/*

 */
