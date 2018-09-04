package com.hong.one;


public class SplitContent {

	/*
	通过参数传入http请求头信息之后
	通过解析之后返回一个HttpRequest对象
	 */
	public HttpRequest splitContent(String header){
		String head = header.substring(0,header.indexOf("\r\n\r\n"));
		String firstLine = null;
		int index = head.indexOf("\r\n");
		firstLine = head.substring(0, index);
		HttpRequest req = new HttpRequestImpl();
		String uri = HttpRequestParser.filterParameters(firstLine, req);
		System.out.println(uri);
		HttpRequestParser.parseURI(uri, req);
		return req;
	}
	/*public static void main(String[] args) {
		SplitContent split = new SplitContent();
		String header = "GET /path1/hong/xiao/0622.html?name=洪晓鸿&pass=123 Http1.1\r\n"+
		"大地待定\r\n\r\n";
		System.out.println(header);
		String path = "E:\\project\\java_project\\HttpServer\\file\\Note1.txt";
		InputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//HttpRequest req = split.splitContent(header);
		HttpRequest req = split.parse(in);
		System.out.println("action"+req.getAction());
		System.out.println("method"+req.getMethod());
		System.out.println("resPath"+req.getResPath());
	}*/
}
