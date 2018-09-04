package com.hong.one;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class HttpRequestParser {

	
	public HttpRequest parse(InputStream in){
		HttpRequest req = null;
		StringBuffer sb= null;
		SplitContent split = new SplitContent();
		try {
			int count = 0;
			byte[] buffer = new byte[1024];
			sb= new StringBuffer();
			while((count = in.read(buffer))>0){
				
					sb.append(new String(buffer,0,count));
				
			}
			
		}catch(SocketTimeoutException s){
			//����ʱ�������õģ�������в���������
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			String header = sb.toString();
			System.out.println(header);
			req = split.splitContent(header);
			if(req.getMethod().equalsIgnoreCase("post")){
				String body = header.substring(header.indexOf("\r\n\r\n")+4);
				parseParamers(body, req);
			}
		}
		return req;
	}
	public static void parseURI(String uri, HttpRequest req){
		//ͨ���ж��Ƿ���.�ַ��ж�����Դ��������
		//���õ�req������
		//�������Դ����/�ַ��滻Ϊ\,�����ҵ���Դ
		//���������Ļ���������������������õ�������
		if(uri.contains(".")){
			req.setAction("resource");
			req.setResPath(uri.replaceAll("/", "\\\\"));
		} else{
			req.setAction("servlet");
			req.setResPath(uri.substring(uri.lastIndexOf("/")));
		}
	}
	public  static String filterParameters(String line, HttpRequest req){
		String[] arr = new String[3];
		arr = line.split(" ");
		System.out.println(line);
		System.out.println(arr[0]+arr[1]+arr[2]);
		req.setMethod(arr[0]);
		String uri = arr[1];
		String temp = null;
		/*
		contains�в�������������ʽ
		split�Ĳ���ĳЩ�ַ�����������ʽ�д������⺬��
		������Ҫ����ת��
		 */
		if(uri.contains("?")){
			temp = uri.split("\\?")[0];
			//temp = temp.replaceAll("/", "\\\\");
			parseParamers(uri.split("\\?")[1], req);
			System.out.println(uri.split("\\?")[1]);
		} else{
			temp = arr[1];
			//temp = temp.replaceAll("/", "\\\\");
		}
		System.out.println(temp);
		return temp;
	}
	/*
	����URL�еĲ���
	 */
	public static void  parseParamers(String line, HttpRequest req){
		/*
		ͨ��������ʽ�����õ���Ӧ��ֵ��
		���뵽req������
		 */
		System.out.println(line);
		String reg = "([^=]+)=([^&]+)&?";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);
		String key = null;
		String value = null;
		while(matcher.find()){
			System.out.println(matcher.group(1));
			key = matcher.group(1);
			System.out.println(matcher.group(2));
			value = matcher.group(2);
			req.addParameters(key, value);
			
		}
	}
	@Test
	public void test(){
		//fileterParameters("Get /hongxiaohogn/wangye?name=hong&pass=123&nickname=����սʿ HTTP1.1", new HttpRequestImpl());
		HttpRequest req = new HttpRequestImpl();
		parseURI("/hongxiaohogn/wangye.html", 
				req);
		System.out.println(req.getResPath()+req.getAction());
		
	}
}
