package com.hong.two;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Container implements ContinerCallback {

	public static final String docBase = "F:\\html\\0620";
	private final int port = 8080;
	//通过二维数组存放请求与对应的类
	//在Javaee中使用xml进行配置，这里使用数组进行模拟
	private String[][] webXml = {
		{"/Res","com.hong.two.ResServlet"},
		{"/Login","com.hong.two.LoginServlet"},
		{"/Register","com.hong.two.RegisterServlet"}
	};
	//如果是第一次创建servlet对象，会将对象放置到map映射，也就是键值对
	//如果是第二次请求的时候，容器会帮助我们从该对象中索取一个servlet进行处理
	private Map<String,Servlet> servlets = new HashMap<String,Servlet>();
	
	public void initServer(){
		//初识化服务器
		//开启端口，并且通过死循环不断监听接入端口的客户端
		//如果有新的客户端连接成功，开启新的线程进行处理
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			prt("服务器成功创建----");
			//通过在主线程中创建一个死循环等待客户端的连接
			while(true){
				Socket socket = server.accept();
				socket.setSoTimeout(2000);
				prt("服务器：正在运行----接收到一个客户端的请求");
				//每一个请求创建一个线程进行处理
				//如果不这么做，则同一个时刻只能有一个用户进行连接
				//这里使用接口回调
				//接口回调一般都会使用到this对象
				EngineTask task = new EngineTask(socket, this);
				//runnable无法独自开启线程
				//只能借助Thread线程进行开启
				Thread th = new Thread(task);
				//开启线程，start只是使得线程进入就绪状态，并
				th.start();
			}
		} catch (IOException e) {
			// io异常
			e.printStackTrace();
		}
	}
	
	private void prt(Object obj) {
		// TODO Auto-generated method stub
		System.out.println(obj);
	}

	@Override
	public Servlet getServlet(String urlPattern) {
		Servlet servlet = null;
		try {
			servlet = servlets.get(urlPattern);
			//如果没有从原来的映射中找到servlet
			//我们就要进行判断是否有这个servlet的映射路径
			//如果有创建新的servlet并将其加入到map中
			if(servlet == null)
			{
				for(int i = 0; i<webXml.length; i++){
					if(webXml[i][0].equals(urlPattern)){
						//通过Class加载字节类对象，传入参数为类路径
						Class<?> resClass = Class.forName(webXml[i][1]);
						//通过默认构造进行初始化
						//向下转型需要进行强制类型转换
						System.out.println("创建一个新的servlet");
						servlet = (Servlet) resClass.newInstance();
						//创建后将新的servlet加入到map映射中
						servlets.put(urlPattern, servlet);
						break;
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			// 类没有找到
			e.printStackTrace();
		} catch (InstantiationException e) {
			// 初始化错误
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// 不合法的准入权限
			e.printStackTrace();
		}
		return servlet;
	}

}
