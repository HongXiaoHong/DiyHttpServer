package com.hong.two;

import java.io.IOException;

import com.hong.one.HttpRequest;

public class ResServlet implements Servlet {

	@Override
	public void doGet(HttpRequest request, HttpResponse response)
			throws IOException {
		// TODO ����
		String resPath = request.getResPath();
		System.out.println("------------------------------");
		System.out.println("resPath:"+resPath);
		System.out.println("---------------����������������---------------");
		response.setContentType(getContentType(resPath));
		ResponseWriter writer = response.getWriter();
		writer.writeFromFile(Container.docBase+resPath);
		response.flush();
		System.out.println("�ɹ����");
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response)
			throws IOException {
		// TODO ����

	}

	private String getContentType(String path){
		String postfix = null;
		int index = path.lastIndexOf(".");
		if(index!=-1){
			postfix = path.substring(index);
			switch(postfix){
			case "html":
			case "htm":
			case "css":
			case "js":
				return "text/html";
			case "mp3":
				return "mpeg/mp3";
			case "mp4":
				return "mpeg/mp4";
			case "ico":
			case "jpg":
			case "png":
				return "image/ico";
			}
		}
		
		return "text/html";
	}

	
}
