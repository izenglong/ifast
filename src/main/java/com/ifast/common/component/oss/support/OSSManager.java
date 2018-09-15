package com.ifast.common.component.oss.support;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/9/11 11:45 | Aron</small>
 */
@Data
public class OSSManager implements UploadServer {

    private UploadServer uploader;

    public OSSManager() {}

    public OSSManager(UploadServer uploader) {
        this.uploader = uploader;
    }

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        return uploader.upload(uploadBytes, fileName);
    }
}
