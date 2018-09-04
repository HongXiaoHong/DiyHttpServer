package com.hong.one;

public interface HttpRequest {

	String getAction();
	void setAction(String action);

	String getMethod();
	void setMethod(String method);

	String getParameter(String name);
	void addParameters(String key,String value);
	void prtParameters();

	void setResPath(String resPath);
	String getResPath();

}
