package com.hong.two;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Container implements ContinerCallback {

	public static final String docBase = "F:\\html\\0620";
	private final int port = 8080;
	//ͨ����ά�������������Ӧ����
	//��Javaee��ʹ��xml�������ã�����ʹ���������ģ��
	private String[][] webXml = {
		{"/Res","com.hong.two.ResServlet"},
		{"/Login","com.hong.two.LoginServlet"},
		{"/Register","com.hong.two.RegisterServlet"}
	};
	//����ǵ�һ�δ���servlet���󣬻Ὣ������õ�mapӳ�䣬Ҳ���Ǽ�ֵ��
	//����ǵڶ��������ʱ��������������ǴӸö�������ȡһ��servlet���д���
	private Map<String,Servlet> servlets = new HashMap<String,Servlet>();
	
	public void initServer(){
		//��ʶ��������
		//�����˿ڣ�����ͨ����ѭ�����ϼ�������˿ڵĿͻ���
		//������µĿͻ������ӳɹ��������µ��߳̽��д���
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			prt("�������ɹ�����----");
			//ͨ�������߳��д���һ����ѭ���ȴ��ͻ��˵�����
			while(true){
				Socket socket = server.accept();
				socket.setSoTimeout(2000);
				prt("����������������----���յ�һ���ͻ��˵�����");
				//ÿһ�����󴴽�һ���߳̽��д���
				//�������ô������ͬһ��ʱ��ֻ����һ���û���������
				//����ʹ�ýӿڻص�
				//�ӿڻص�һ�㶼��ʹ�õ�this����
				EngineTask task = new EngineTask(socket, this);
				//runnable�޷����Կ����߳�
				//ֻ�ܽ���Thread�߳̽��п���
				Thread th = new Thread(task);
				//�����̣߳�startֻ��ʹ���߳̽������״̬����
				th.start();
			}
		} catch (IOException e) {
			// io�쳣
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
			//���û�д�ԭ����ӳ�����ҵ�servlet
			//���Ǿ�Ҫ�����ж��Ƿ������servlet��ӳ��·��
			//����д����µ�servlet��������뵽map��
			if(servlet == null)
			{
				for(int i = 0; i<webXml.length; i++){
					if(webXml[i][0].equals(urlPattern)){
						//ͨ��Class�����ֽ�����󣬴������Ϊ��·��
						Class<?> resClass = Class.forName(webXml[i][1]);
						//ͨ��Ĭ�Ϲ�����г�ʼ��
						//����ת����Ҫ����ǿ������ת��
						System.out.println("����һ���µ�servlet");
						servlet = (Servlet) resClass.newInstance();
						//�������µ�servlet���뵽mapӳ����
						servlets.put(urlPattern, servlet);
						break;
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			// ��û���ҵ�
			e.printStackTrace();
		} catch (InstantiationException e) {
			// ��ʼ������
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// ���Ϸ���׼��Ȩ��
			e.printStackTrace();
		}
		return servlet;
	}

}
