package com.ifast.common.utils;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * 
 * @author Aron
 * @date 2017年7月1日
 */
public class CodecUtils extends DigestUtils{

	public final static Base64 base64 = new Base64();
	
	public static String getPasswd(String passwd, String salt) {
		String pwd = sha256Hex(passwd + salt);
		System.err.println(" ------ passwd:" + passwd + ", salt :" + salt + " ==> pwd :" + pwd);
		return pwd;
	}
	
	public static String getSalt(){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
	}
	
	public static String base64Encode(String str){
		return base64.encodeToString(str.getBytes());
	}
	
	public static String base64Decode(String str){
		return new String(base64.decode(str.getBytes()));
	}

	
	public static void main(String[] args) {
		
//		String passwd = getPasswd("4297f44b13955235245b2497399d7a93", "salt");
//		System.out.println(passwd + " - " + passwd.length());
//		System.out.println(md5Hex("123123")); // 4297f44b13955235245b2497399d7a93
	    
	    String str = base64Decode("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b20iLCJyb2xlcyI6WyJ1c2VyIl0sImlhdCI6MTUyNDEwMjk1MH0");
	    System.out.println(str);
	    
	}
	
}
