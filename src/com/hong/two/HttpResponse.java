package com.hong.two;

public interface HttpResponse {
	
	public ResponseWriter getWriter();
	
	public void setContentType(String mime);
	
	public void flush();

}
