package com.jinbo.customer.wei.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



/** 
 * 请求校验工具类 
 *  
 */  

public class SignUtil {
private static String token = "gzjbwmjtds2013";
/** 
 * 验证签名 
 *  
 * @param signature 
 * @param timestamp 
 * @param nonce 
 * @return 
 */  

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String []arr = new String []{token,timestamp,nonce};
		Arrays.sort(arr);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
			
		}
		MessageDigest md = null;
		String tmpStr = null;
		
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
		}
		
		content = null;
	
		return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
	}
	  /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */ 
private static String byteToStr(byte[] byteArray) {
	String strDigest ="";
	for (int i = 0; i < byteArray.length; i++) {
		strDigest +=byteToHexStr(byteArray[i]);
	}
	return strDigest;
}
/** 
 * 将字节转换为十六进制字符串 
 *  
 * @param mByte 
 * @return 
 */  

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
  
        String s = new String(tempArr);  
        return s;  

		
	}

}
