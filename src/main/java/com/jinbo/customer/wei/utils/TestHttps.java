package com.jinbo.customer.wei.utils;

import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class TestHttps {
	private static class TrustAnyTrustManager implements X509TrustManager {
		   public void checkClientTrusted(X509Certificate[] chain, String authType)
		     throws CertificateException {
		   }
		   public void checkServerTrusted(X509Certificate[] chain, String authType)
		     throws CertificateException {
		   }
		   public X509Certificate[] getAcceptedIssuers() {
		    return new X509Certificate[] {};
		   }
		}
		private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		   public boolean verify(String hostname, SSLSession session) {
		    return true;
		   }
		}

	
		public static String request(String url,String content) throws Exception {	
			
			    SSLContext sc = SSLContext.getInstance("SSL");
			    sc.init(null, new TrustManager[] { (TrustManager) new TrustAnyTrustManager() },
			      new java.security.SecureRandom());
			    URL console = new URL(url);
			    HttpsURLConnection conn = (HttpsURLConnection) console
			      .openConnection();
			    conn.setRequestMethod("POST");
			    conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
			    conn.setSSLSocketFactory(sc.getSocketFactory());
			    conn.setHostnameVerifier((HostnameVerifier) new TrustAnyHostnameVerifier());	    
			    OutputStreamWriter osw = null;
				try {
					conn.connect();
					
					osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
					osw.write(content);
				} catch (Exception e) {					
					e.printStackTrace();
					System.out.println("连接被拒绝");
				}finally{					
					osw.flush();
					osw.close();
				}
			    
			    
				StringBuffer buffe = null;
				try {
					buffe = new StringBuffer();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					String temp;
					while ((temp = br.readLine()) != null) {
					buffe.append(temp);
					buffe.append("\n");
					
					
					System.out.println(buffe.toString());
                       }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{					
					conn.disconnect();					
				}
				return buffe.toString();	
		}
		   
		 
		  

}
		   
