package com.hong.����;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPSend {

	
	public static void main(String[] args) {
		// ����ĳһ��ַ����Դ���ص�����
		HTTPSend send = new HTTPSend();
		send.initClient();
	}

	private String path = "F:\\html\\0620\\aa.jpg";
	private int port = 80;
	public void initClient(){
		try {
			//ͨ����ַ�Ͷ˿ںͲ���̳�ȡ������
			final Socket socket = new Socket("183.61.14.102",port);
			System.out.println("���ӳɹ�");
			//����ͷ���ݣ���\r\n�ָ�
			String content   = 	"GET http://www.runoob.com/wp-content/uploads/2013/10/bs.png HTTP/1.1\r\n"+
								"Host:183.61.14.102:80\r\n"+
								"Connection:keep-alive\r\n"+
								"User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36\r\n"+
								"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n"+
								"Accept-Encoding: gzip, deflate, br\r\n"+
								"Accept-Language: zh-CN,zh;q=0.9\r\n\r\n";
			//�õ�����������̷̳�������ͷ
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
							//Ϊ�ο���ʹ��char�����byte������±�
							//��Ϊ һ����ĸ����˵����ʹ��ascii��ʾ�Ķ�ʹ��һ���ֽ�
							//��ȡ���ַ�������Ȼ�����char����
							//����һ����ĸ����ռ��һ���ַ������Եõ����±����ԭ��byte������±�
							index = temp.indexOf("\r\n\r\n");//\r\n\r\n������Ӧͷ�Լ�����֮��ķָ���
							offset = index + 4;//�������Ӧͷ�Լ��ָ������±�
							os.write(buffer, offset, count-offset);
							
						}
						//����������ݣ�ʹ��ѭ��������ȡ
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
