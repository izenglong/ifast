package com.ifast.oss.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * <pre>
 * 七牛对象存储服务
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
public class QiNiuOSSService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadManager uploadManager;
    private Configuration cfg;
    private String token;
    private OSSConfig config;

    public QiNiuOSSService(OSSConfig config, Zone zone, String bucket, String accessKey, String secretKey) {
        cfg = new Configuration(zone);
        uploadManager = new UploadManager(cfg);
        this.config = config;

        token = Auth.create(this.config.getQiNiuAccessKey(), this.config.getQiNiuSecretKey())
                .uploadToken(this.config.getQiNiuBucket());
    }

    // method

    public String upload(byte[] uploadBytes, String fileName) {
        try {
            uploadManager.put(uploadBytes, fileName, token);
            String fileURL = this.config.getQiNiuAccessURL() + fileName;
            log.info("上传成功，url:{}", fileURL);
            return fileURL;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            throw new IFastException(EnumErrorCode.FileUploadError.getCodeStr());
        }
    }

    // get set

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public Configuration getCfg() {
        return cfg;
    }

    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
