package com.hong.one;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
	(a) ����дһ��������
	(a)-1: �󶨶˿ں�: 8080��
	(a)-2: ������տͻ��˵�����
		   ��͸�����һ����Դ (ͼƬ)
		(d)-1: ƴ����Ӧͷ��
		(d)-2: ��ͬ��Դ������һ���͸��������
*/
public class SendImage {
	
	public void initServer(){
		ServerSocket server = null;
		try {
			server = new ServerSocket(8080);
			System.out.println("[SERVER] �������ѿ��� ..");
			while( true ){
				Socket socket = server.accept();
				SocketTask task = new SocketTask(socket);
				(new Thread( task )).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//[1] ��дһ���ڲ���, ������ Socket �׽��֡�
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

	//ģ�� [1] ƴ����Ӧͷ (�����)
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
	
	//ģ��[2] ����ͼƬ
	public void sendImage(OutputStream os){
		String basePath = "E:\\dir\\images\\";
		int ran = (int)(Math.random()*3)+1;
		String path = basePath +"p"+ ran +".jpg";   //[PS] ȷ��ѡ����һ��ͼƬ..
		
		FileInputStream is = null;
		String line = null;
		try {
			//[1] ��ȡͼƬ����������
			is = new FileInputStream( path );
			//[2] �õ� "ͼƬ" �Ĵ�С��
			//[3] ���� packResponse ������Ӧͷ��
			line = packResponse( is.available() );
			//[4] ������Ӧͷ������ (ͨ�� os ����)��
			os.write( line.getBytes() );
			//[5] �߶�(ͼƬ), ��д���ͻ��� (ͨ�� os ��д)��
			byte[] buff = new byte[8192];
			int count = 0;
			while( is.available()>0 ){       //ȷ�������ݿɶ� ..
				count = is.read( buff );
				os.write( buff, 0, count );  //������д����..
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
