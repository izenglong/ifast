package com.ifast.oss.sdk;

/**
 * <pre>
 * 七牛对象存储配置
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
public class OSSConfig {
    private String qiNiuAccessKey;
    private String qiNiuSecretKey;
    private String qiNiuBucket;
    private String qiNiuAccessURL;

    // toString
    @Override
    public String toString() {
        return "OSSConfig [qiNiuAccessKey=" + qiNiuAccessKey + ", qiNiuSecretKey=" + qiNiuSecretKey + ", qiNiuBucket="
                + qiNiuBucket + ", qiNiuAccessURL=" + qiNiuAccessURL + "]";
    }

    // get set
    public String getQiNiuAccessKey() {
        return qiNiuAccessKey;
    }

    public void setQiNiuAccessKey(String qiNiuAccessKey) {
        this.qiNiuAccessKey = qiNiuAccessKey;
    }

    public String getQiNiuSecretKey() {
        return qiNiuSecretKey;
    }

    public void setQiNiuSecretKey(String qiNiuSecretKey) {
        this.qiNiuSecretKey = qiNiuSecretKey;
    }

    public String getQiNiuBucket() {
        return qiNiuBucket;
    }

    public void setQiNiuBucket(String qiNiuBucket) {
        this.qiNiuBucket = qiNiuBucket;
    }

    public String getQiNiuAccessURL() {
        return qiNiuAccessURL;
    }

    public void setQiNiuAccessURL(String qiNiuAccessURL) {
        this.qiNiuAccessURL = qiNiuAccessURL;
    }

}
