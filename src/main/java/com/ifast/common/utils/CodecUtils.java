package com.ifast.common.utils;

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

	public static String base64Encode(String str){
		return base64.encodeToString(str.getBytes());
	}
	
	public static String base64Decode(String str){
		return new String(base64.decode(str.getBytes()));
	}

}
