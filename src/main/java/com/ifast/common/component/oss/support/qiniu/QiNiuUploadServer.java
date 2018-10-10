package com.ifast.common.component.oss.support.qiniu;

import com.ifast.common.component.oss.support.UploadServer;
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
public class QiNiuUploadServer implements UploadServer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private UploadManager uploadManager;
    private Configuration cfg;
    private QiNiuOSSProperties config;


    public QiNiuUploadServer(QiNiuOSSProperties config, Zone zone) {
        cfg = new Configuration(zone);
        uploadManager = new UploadManager(cfg);
        this.config = config;
    }

    // method
    @Override
    public String upload(byte[] bytes, String fileName) {
        String token = Auth.create(this.config.getAccessKey(), this.config.getSecretKey())
                .uploadToken(this.config.getBucket());
        try {
            uploadManager.put(bytes, fileName, token);
            String fileURL = this.config.getAccessURL() + fileName;
            log.info("上传成功，url:{}", fileURL);
            return fileURL;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            throw new IFastException(EnumErrorCode.FileUploadError.getCodeStr());
        }
    }

}
