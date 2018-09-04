package com.hong.����;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/*
@Author:������
function:��дһ���򵥵ķ���������������ķ��ʣ�
���ҽ����ʵ���Ϣͨ�����ƽ������

 */
public class HttpReceive {
//Link:   http://localhost:8088/
	public static void main(String[] args) {
		HttpReceive receive = new HttpReceive();
		receive.initServer();
	}
	//��������Ķ˿ڣ�Ҳ���Ƿ��ʵ�ַʱ��Ҫд�ϵĶ˿�
	private static final int port = 8088;
	public void initServer() {
		Socket socket = null;
		ServerSocket server;
		try {
			//ͨ��ServerSocket����������������
			server = new ServerSocket(port);
			System.out.println("�����������ɹ�");
			
			
			final byte[] buffer = new byte[1024];
			//���ܿͻ��˵����ӣ��������û�пͻ������ӻᷢ������
			socket = server.accept();
			InetAddress inetAddress = socket.getInetAddress();
			System.out.println("�пͻ������ӽ������˿ں�Ϊ��"+inetAddress.getHostName());
			//�ӽ��ܵĿͻ��˵õ�������
			final InputStream in = socket.getInputStream();
			//socket.setSoTimeout(2000);
			//����һ���߳̽��ܿͻ��˵���Ϣ
			(new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int len = 0;
					String temp = null;
					try{
						
						while(true){
							//��Ϊread����������������������￪���߳̽��н���
							//����������߳̾�Ҫ�����̵߳ĳ�ʱ
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
