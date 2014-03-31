package com.jinbo.customer.wei.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {
	public static String http(String url, Map<String, String> params,String content) {
		URL u = null;
		String param = null;
		HttpURLConnection con = null;
		//构建请求参数
		StringBuffer sb = new StringBuffer();
		if(content==null){
			if(params!=null){
			for (Entry<String, String> e : params.entrySet()) {
			sb.append(e.getKey());
			sb.append("=");
			sb.append(e.getValue());
			sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
			  }
			param = sb.toString();
			System.out.println("send_url:"+url);
			System.out.println("send_data:"+sb.toString());
			
		}else{
			param = content;
			
		}
		//尝试发送请求
		try {
		u = new URL(url);
		
		con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
		osw.write(param);
		System.out.println("参数是:"+param);
		osw.flush();
		osw.close();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		if (con != null) {
		con.disconnect();
		}
		}
		//读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
		buffer.append(temp);
		buffer.append("\n");
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		 
		return buffer.toString();
		}
	  

	
	
	
	
	
	
	
}
