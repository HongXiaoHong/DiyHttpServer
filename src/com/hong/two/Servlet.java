package com.hong.two;

import java.io.IOException;

import com.hong.one.HttpRequest;

public interface Servlet {
	
	public void doGet( HttpRequest request, HttpResponse response )
			throws IOException;
	
	public void doPost( HttpRequest request, HttpResponse response )
			throws IOException;

}