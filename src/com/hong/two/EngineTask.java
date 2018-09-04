package com.hong.two;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.hong.one.HttpRequest;
import com.hong.one.HttpRequestParser;

/*
 	EnginTask ��������
	ps: ʵ�� Runnable �ӿ�
*/
public class EngineTask implements Runnable {

	private Socket socket = null;
	private ContinerCallback callback = null;

	public EngineTask(Socket socket, ContinerCallback callback){
		// ��Ա������ʼ��
		this.socket = socket;
		this.callback = callback;
	}
	public void run(){ 
		// --- ������Ĵ��� ----
		try {
			System.out.println("���ܸÿͻ��˵�����");
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			HttpRequestParser parser = new HttpRequestParser();
			HttpRequest req = parser.parse(in);
			String action = req.getAction();
			HttpResponse resp = new HttpResponseImpl(out); 
			proccessAction(action, req, resp);
			resp.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {socket.close();}
			catch (IOException e) {e.printStackTrace();}
		}
		
	}
	private void proccessAction(String action, HttpRequest req, HttpResponse resp){
		// --- ������Ĵ��� ----
		if(action != null)
			if("resource".equalsIgnoreCase(action)){
				System.out.println("EngineTask��proccessActionѰ����Դ");
				findResource(req, resp);
			} else{
				findServlet(req, resp);
			}
		
	}
	private void findResource(HttpRequest req, HttpResponse resp){
		// --- ������Ĵ��� ----
		Servlet servlet = callback.getServlet("/Res");
		if( servlet!=null){
			try {
				servlet.doGet(req, resp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void findServlet(HttpRequest req, HttpResponse resp){ 
		// --- ������Ĵ��� ----
		String resPath = req.getResPath();
		Servlet servlet = callback.getServlet(resPath);
		if( servlet!=null){
			try {
				String method = req.getMethod();
				if("get".equalsIgnoreCase(method)){
					servlet.doGet(req, resp);
				} else{
					servlet.doPost(req, resp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
