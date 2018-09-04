package com.hong.one;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl implements HttpRequest{

	private String method  = null;
	private String resPath = null;
	private String action = null;
	private Map<String,String> params = new HashMap<String,String>();
	
	public HttpRequestImpl() {
		super();
	}
	
	public HttpRequestImpl(String method, String resPath, String action,
			Map<String, String> params) {
		super();
		this.method = method;
		this.resPath = resPath;
		this.action = action;
		this.params = params;
	}

	public Map<String, String> getParams() {return params;}
	public void setParams(Map<String, String> params) {this.params = params;}

	public String getMethod() {return method;}
	public void setMethod(String method) {this.method = method;}

	public String getResPath() {return resPath;}
	public void setResPath(String resPath) {this.resPath = resPath;}

	public String getAction() {return action;}
	public void setAction(String action) {this.action = action;}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return params.get(name);
	}

	@Override
	public void addParameters(String key, String value) {
		// TODO Auto-generated method stub
		params.put(key, value);
	}

	@Override
	public void prtParameters() {
		// TODO Auto-generated method stub
		System.out.print("?");
		for(Map.Entry<String, String> entry:params.entrySet()){
			System.out.print(entry.getKey()+"="+entry.getValue()+"&");
		}
	}

	

}
