package com.ifast.oss.sdk;

import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private OSSConfig config;


    public QiNiuOSSService(OSSConfig config, Zone zone) {
        cfg = new Configuration(zone);
        uploadManager = new UploadManager(cfg);
        this.config = config;
    }

    // method

    public String upload(byte[] uploadBytes, String fileName) {
        String token = Auth.create(this.config.getQiNiuAccessKey(), this.config.getQiNiuSecretKey())
                .uploadToken(this.config.getQiNiuBucket());
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

}
