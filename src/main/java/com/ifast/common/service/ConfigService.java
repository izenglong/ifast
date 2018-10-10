package com.ifast.common.service;

import com.ifast.common.base.CoreService;
import com.ifast.common.domain.ConfigDO;
import com.ifast.common.utils.JSONUtils;
import com.ifast.common.component.oss.support.qiniu.QiNiuOSSProperties;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
public interface ConfigService extends CoreService<ConfigDO> {
    ConfigDO getByKey(String k);

    String getValueByKey(String k);
    
    void updateKV(Map<String, String> kv);
    
    List<ConfigDO> findListByKvType(int kvType);

    /**
     * 七牛OSS
     */
    default QiNiuOSSProperties getQiNiuOssConfigProperties() {
        ConfigDO configDO = getByKey("oss_qiniu");
        String v = configDO.getV();
        Map<String, Object> json = JSONUtils.jsonToMap(v);
        String ak = (String) json.get("AccessKey");
        String sk = (String) json.get("SecretKey");
        String bucket = (String) json.get("bucket");
        String accessUrl = (String) json.get("AccessUrl");

        QiNiuOSSProperties ossConfig = new QiNiuOSSProperties();
        ossConfig.setAccessKey(ak);
        ossConfig.setSecretKey(sk);
        ossConfig.setBucket(bucket);
        ossConfig.setAccessURL(accessUrl);
        return ossConfig;
    }
}
