package com.hong.two;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseImpl implements HttpResponse {

	private OutputStream out = null;
	private String contentType = "text/html";
	private ResponseWriter rw = null;
	public HttpResponseImpl() {
		// 
		super();
	}
	public HttpResponseImpl(OutputStream out) {
		//初始化输出流，
		//从socket的getOutputStream方法中获取得到的outputstream
		super();
		this.out = out;
		this.rw = new ResponseWriter();
	}

	public String getResponseHeader( long size, String contentType){
		return "HTTP/1.1 200 OK\r\n"+
				"Server: Tengine\r\n"+
				"Content-Type: " +contentType+"\r\n"+
				"Content-Length: "+ size +"\r\n"+
				"Connection: keep-alive\r\n"+
				"Cache-Control: max-age=86400\r\n"+
				"Last-Modified: Sat, 28 Oct 2017 06:08:41 GMT\r\n"+
				"Accept-Ranges: bytes\r\n"+
				"Age: 69030\r\n\r\n";
	}
	@Override
	public ResponseWriter getWriter() {
		return rw;
	}

	@Override
	public void setContentType(String mime) {
		this.contentType = mime;
	}

	@Override
	public void flush() {
		// 待定
		String header = getResponseHeader(rw.getContentLength(), contentType);
		try {
			out.write(header.getBytes());
			int contentLength = rw.getContentLength();
			if(contentLength>0){
				out.write(rw.getContent());
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {out.close();} catch (IOException e) {e.printStackTrace();}
		}
	}

}
