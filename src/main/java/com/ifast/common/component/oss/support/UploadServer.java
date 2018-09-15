package com.ifast.common.component.oss.support;

/**
 * <pre>
 * 上传服务
 * </pre>
 * <small> 2018/9/11 11:46 | Aron</small>
 */
public interface UploadServer {

    String upload(byte[] uploadBytes, String fileName);

}
