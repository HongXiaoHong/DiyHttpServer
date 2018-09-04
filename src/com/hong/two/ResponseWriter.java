package com.hong.two;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ResponseWriter {

	private final String charset = "UTF-8";
	private byte[] content;
	private int length = 0;
	public void write(String line){
		try {content = line.getBytes(charset);} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		length = content.length;
	}
	public void write(String line, String charset){
		try {
			
			content = line.getBytes(charset);
			length = content.length;
			
		} 
		catch (UnsupportedEncodingException e) {e.printStackTrace();}
	}
	public void write(byte[] buffer){
		content = buffer;
		//这里可用可不用this关键字
		length = content.length;
	}
	public void writeFromFile(String srcFile){
		InputStream in = null ;
		if(content==null){
			content = new byte[8192];
		}
		try {
			//int sum = 0;
			//因为长度没有设置的缘故无法进行传输
			in = new FileInputStream(srcFile);
			length = in.read(content);
			
			//byte[] buff = new byte[1024];
			//int count = -1;
			//while((count=in.read(buff))>0){
			//	copy(content,buff,sum,count);
			//	sum += count;
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void copy(byte[] content2, byte[] buff, int sum, int count) {
		// TODO Auto-generated method stub
		if(sum>content2.length){
			byte[] copy = Arrays.copyOf(content2, content2.length);
			content = new byte[content.length*2];
			for(int i = 0 ; i<copy.length; i++){
				content[i] = copy[i];
			}
		} 
		
		for(int i = 0 ; i<count; i++){
			content2[sum+i] = buff[i];
		}
		
	}
	public int getContentLength(){
		return length;
	}
	public byte[] getContent(){
		return content;
	}
	
}
