package com.hong.����;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SendImg {

	private static String path = "F:\\html\\0621\\";
	/*
	ͨ��ʵ��Runnable����һ�������࣬
	�ҵ�����ǣ�
	д�ǲ��ᷢ�������ģ�
	�����̵߳���������Ϊ�����ӳ���
	����ͻ��˷���������������Ϊ��������û�д��͵�
	�ͻ��˺ͷ������˶��ᷢ������
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
			����������׼������һ���ͻ��˵�����
			���ܵ�һ���ͻ���֮��ʹ���̷߳���ͼƬ
			 */
			server = new ServerSocket(8080);
			System.out.println("�����������ɹ�");
			//�����������ɹ�
			Socket socket = server.accept();
			System.out.println("���յ��ͻ���");
			//���յ��ͻ���
			SendTask send = new SendTask(socket);
			Thread th = new Thread(send);
			th.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String packProtocal(int size){
		//��Ӧͷ
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
		//ͨ��Math��random�������������
		//��ΧΪ 0-1  ����0��������1
		//�������1��3�������
		int ran = (int)(Math.random()*3)+1;
		//ƴ��ͼƬ�ĵ�ַ
		path = path+ran+".jpg";
		InputStream in = null;
		try {
			//ͨ��FileInputStream��ȡͼƬ���ֽ���
			in =new FileInputStream(path);
			//����Ӧͷд���ͻ���
			int len = in.available();
			out.write(packProtocal(len).getBytes());
			//��ͼƬ����д���ͻ���
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
