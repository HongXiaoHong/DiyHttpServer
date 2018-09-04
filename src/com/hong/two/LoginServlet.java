package com.hong.two;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.hong.one.HttpRequest;

public class LoginServlet implements Servlet {

	static Map<String, User> students = new HashMap<String, User>();
	class User{
		String name ;
		String pass ;
		public User(String name, String pass){
			this.name = name;
			this.pass = pass;
		}
	}
	{
		students.put("01", new User("ºéÏþºè","123"));
		students.put("02", new User("ÇØºÆ","456"));
		students.put("03", new User("³Â¿¡ºé","789"));
		students.put("04", new User("andy","789"));
	}
	@Override
	public void doGet(HttpRequest request, HttpResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		name = URLDecoder.decode(name, "utf-8");
		System.out.println("-----------------------");
		System.out.println("name"+name);
		System.out.println("-----------------------");
		String pass = request.getParameter("pass");
		for(String key: students.keySet()){
			User temp = students.get(key);
			if(temp.name.equals(name)&&temp.pass.equals(pass)){
				System.out.println("³É¹¦µÇÂ¼");
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpRequest request, HttpResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		name = URLDecoder.decode(name,"utf-8");
		System.out.println("-----------------------");
		System.out.println("name"+name);
		System.out.println("-----------------------");
		String pass = request.getParameter("pass");
		for(String key: students.keySet()){
			User temp = students.get(key);
			if(temp.name.equals(name)&&temp.pass.equals(pass)){
				System.out.println("³É¹¦µÇÂ¼");
				break;
			}
		}
	}

}
