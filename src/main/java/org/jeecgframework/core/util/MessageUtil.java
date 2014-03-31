package org.jeecgframework.core.util;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.jeecgframework.core.util.ADVICESTATUS;
import org.jeecgframework.core.util.AINFOSTATUS;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.web.system.pojo.base.Message;
import org.jeecgframework.web.system.pojo.base.MsgItem;
import org.jeecgframework.web.system.pojo.base.Xmlparsel;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.thoughtworks.xstream.XStream;


/*<?xml version="1.0" encoding="GB2312"?><LANZ_ROOT><ErrorNum>0</ErrorNum><ActiveID>9740570031802801</ActiveID></LANZ_ROOT>

<?xml version="1.0" encoding="GB2312"?><LANZ_ROOT><ErrorNum>0</ErrorNum></LANZ_ROOT>

<?xml version="1.0" encoding="GB2312"?><LANZ_ROOT><ErrorNum>0</ErrorNum><Stock>154</Stock><Points>9416</Points></LANZ_ROOT>

<?xml version="1.0" encoding="GB2312"?><LANZ_ROOT><ErrorNum>0</ErrorNum><rsSMS><MsgItem Phone="13450294721" RecDate="2014-3-27" RecTime="13:28:00" PostFixNum="">小段喜欢娜娜！</MsgItem></rsSMS></LANZ_ROOT>

<?xml version="1.0" encoding="GB2312"?><LANZ_ROOT><ErrorNum>0</ErrorNum></LANZ_ROOT>*/


public class MessageUtil {


	
    private static String sessionId = "";
    private static String activeID = "";

	public static String SendMessage(Message m) throws IOException
	{
		URL url = new URL("http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
		connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");              
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
		out.write("UserID=817279&Account=gongzi&Password=7C4A8D09CA3762AF61E59520943DC26494F8941B&SMSType=1&Content="+m.getContent()+"&Phones="+m.getPhone()+"&SendDate=&Sendtime=");
		out.flush();
		out.close();
		
		//接收--------------
		InputStream input = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String sCurrentLine = "";
		String sTotalString = "";
		while ((sCurrentLine = reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		int i=sTotalString.indexOf("<ErrorNum>");
		//System.out.println(i);
		int ii=sTotalString.indexOf("</ErrorNum>");
		//System.out.println(ii);
		String ss=sTotalString.substring(i+10, ii);
		System.out.println(sTotalString);
		return ss;
	}
	
    public static String send(Message msg) throws IOException{
    	
	      URL url1 = new URL("http://www.lanz.net.cn/LANZGateway/SendSMS.asp");   
	      HttpURLConnection connection1 = (HttpURLConnection)url1.openConnection();	    
	      connection1.setRequestProperty( "Cookie", sessionId);   //再次连接的时候设置 session相同	     
	      String sTotalString =  TestPost(url1,connection1,"ActiveID="+activeID+"&SMSType=1&Phone="+msg.getPhone()+"&Content="+msg.getContent() );
			int i=sTotalString.indexOf("<ErrorNum>");
			//System.out.println(i);
			int ii=sTotalString.indexOf("</ErrorNum>");
			//System.out.println(ii);
			String ss=sTotalString.substring(i+10, ii);
			System.out.println(sTotalString);
			return ss;
    }
	public static  Message fentchMessage() throws IOException, DocumentException
	{ 
		String ss = null;
        if(activeID!=null&&activeID.length()>0&&sessionId!=null&&sessionId.length()>0){

	     URL url5 = new URL("http://www.lanz.net.cn/LANZGateway/FetchSMS.asp");        // 上行短信
	      HttpURLConnection connection5 = (HttpURLConnection)url5.openConnection();	    
	      connection5.setRequestProperty( "Cookie", sessionId);                       //再次连接的时候设置 session相同	     
	      ss = TestPost(url5,connection5,"ActiveID="+activeID );
			int i=ss.indexOf("<ErrorNum>");
			//System.out.println(i);
			int ii=ss.indexOf("</ErrorNum>");
			String num=ss.substring(i+10, ii);
			if(num!=null&&num.equalsIgnoreCase("0")){

		       SAXReader sx = new SAXReader();
		      Document doc = sx.read(new ByteArrayInputStream(ss.getBytes("GB2312")));
		      List<Element> lists = doc.selectNodes("/LANZ_ROOT/ErrorNum");
		     if(lists!=null&&lists.size()==1){
		    	 Message msg = new Message();
		    	 msg.setErrorNum(lists.get(0).getText());
		    	 System.out.println("error="+lists.get(0).getText());
		    	 List<Element> list = doc.selectNodes("/LANZ_ROOT/rsSMS/MsgItem");
		    	 List<MsgItem> msgs = new ArrayList<MsgItem>();
		    	 for(Element ele : list){
		    		 MsgItem mi = new MsgItem();
		    		 mi.setPhone(ele.attributeValue("Phone"));
		    		 mi.setPostFixNum(ele.attributeValue("PostFixNum"));
		    		 mi.setRecDate(ele.attributeValue("RecDate"));
		    		 mi.setRecTime(ele.attributeValue("RecTime"));
		    		 mi.setMsgItem(ele.getText());
		    		 msgs.add(mi);
		    	 }
		    	 msg.setMsgItem(msgs);
		    	 System.out.println("错误代码是"+msg.getErrorNum());
		    	 System.out.println("共有"+msg.getMsgItem().size());
		    	 for(MsgItem ms : msg.getMsgItem()){
		    		 System.out.println("手机号是"+ms.getPhone());
		    		 System.out.println("内容是"+ms.getMsgItem());
		    		 System.out.println("时间是"+ms.getRecTime());
		    	 }
		           return msg;
		    	 
		     }
	          
	          
			}
        }
	      return null;
	}
	
    public static String getBody(String xml){
        int i=xml.indexOf("<body>");
   		  int ii=xml.indexOf("</body>");
   		  String content = xml.substring(i+6, ii);
    	return content;
    }

	public static String login() throws IOException
	{
		   URL url = new URL("http://www.lanz.net.cn/LANZGateway/Login.asp");
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	      String sTotalString = TestPost(url,connection,"UserID=817279&Account=gongzi&Password=7C4A8D09CA3762AF61E59520943DC26494F8941B");
	      activeID = sTotalString.substring(82,98);
	      String session_value = connection.getHeaderField("Set-Cookie" );
          String[] sessionIds = session_value.split(";");
          sessionId = sessionIds[0];
          int i=sTotalString.indexOf("<ErrorNum>");
		  int ii=sTotalString.indexOf("</ErrorNum>");
		  String ss=sTotalString.substring(i+10, ii);
          return ss;
	}
	
	public static String heart() throws IOException
	{
		String ss = null;
        if(activeID!=null&&activeID.length()>0&&sessionId!=null&&sessionId.length()>0){
        	
        	URL url2 = new URL("http://www.lanz.net.cn/LANZGateway/HeartBeat.asp");        // 心跳 每分钟做一次 需要做一个心跳线程
        	HttpURLConnection connection2 = (HttpURLConnection)url2.openConnection();
        	connection2.setRequestProperty( "Cookie", sessionId);                       //再次连接的时候设置 session相同
        	String sTotalString = TestPost(url2,connection2,"ActiveID="+activeID );
        	int i=sTotalString.indexOf("<ErrorNum>");
        	int ii=sTotalString.indexOf("</ErrorNum>");
        	ss=sTotalString.substring(i+10, ii);
        }
      return ss;
	}
	
	
	
	public static void accountBalance() throws IOException
	{

	      URL url3 = new URL("http://www.lanz.net.cn/LANZGateway/GetSMSStock.asp");      // 查询余额
	      HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();  
	      connection3.setRequestProperty( "Cookie", sessionId);                       //再次连接的时候设置 session相同	     
	      TestPost(url3,connection3,"ActiveID="+activeID );
	}
	
	public static String logOut() throws IOException
	{
          if(activeID!=null&&activeID.length()>0&&sessionId!=null&&sessionId.length()>0){
        	  
        	  URL url4 = new URL("http://www.lanz.net.cn/LANZGateway/Logoff.asp");           // 注销
        	  HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();	    
        	  connection4.setRequestProperty( "Cookie", sessionId);                       //再次连接的时候设置 session相同	     
        	 String sTotalString =  TestPost(url4,connection4,"ActiveID="+activeID );
        	 	int i=sTotalString.indexOf("<ErrorNum>");
            	int ii=sTotalString.indexOf("</ErrorNum>");
            	return sTotalString.substring(i+10, ii);
          }else{
        	  return null;
          }
	}
	
	public static String TestPost(URL url,HttpURLConnection connection,String SMSContent) throws IOException 
	{
	    connection.setRequestMethod("POST");
			connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");              
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
			out.write(SMSContent);
			out.flush();
			out.close();
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream,"GB2312"));
			while ((sCurrentLine = l_reader.readLine()) != null) 
			{
				sTotalString += sCurrentLine + "\r\n";
			}
			System.out.println(sTotalString);
			return sTotalString;	          
		}
	


}

