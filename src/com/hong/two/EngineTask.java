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
 	EnginTask 引擎任务
	ps: 实现 Runnable 接口
*/
public class EngineTask implements Runnable {

	private Socket socket = null;
	private ContinerCallback callback = null;

	public EngineTask(Socket socket, ContinerCallback callback){
		// 成员变量初始化
		this.socket = socket;
		this.callback = callback;
	}
	public void run(){ 
		// --- 加入你的代码 ----
		try {
			System.out.println("接受该客户端的数据");
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
		// --- 加入你的代码 ----
		if(action != null)
			if("resource".equalsIgnoreCase(action)){
				System.out.println("EngineTask的proccessAction寻找资源");
				findResource(req, resp);
			} else{
				findServlet(req, resp);
			}
		
	}
	private void findResource(HttpRequest req, HttpResponse resp){
		// --- 加入你的代码 ----
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
		// --- 加入你的代码 ----
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
