package com.ifast.sms.sender;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.CodecUtils;
import com.ifast.common.utils.DateUtils;
import com.ifast.sms.config.ZhuTongProperties;
import com.ifast.sms.support.SmsSender;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * <pre>
 * 助通短信
 * 文档地址请参考 http://www.ztinfo.cn/page/help
 * </pre>
 * <small> 2018/8/31 19:02 | Aron</small>
 */
public class ZhuTongSender implements SmsSender {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate = new RestTemplate();

    public ZhuTongSender() {}

    public ZhuTongSender(ZhuTongProperties properties) {
        this.properties = properties;
    }

    @Setter
    private ZhuTongProperties properties;


    @Override
    public void send(String mobile, String code, String scene) {

        String url = buildURL(mobile, code, scene);

        String res = restTemplate.getForObject(url, String.class);
        if(log.isDebugEnabled()){
            log.debug("【SMS】请求结果:{}", res);
        }
        String[] split = null;
        if (res != null) {
            split = res.split(",");
        }
        if (split != null && split.length == 2) {
            res = split[0];
        }
        if(!"1".equals(res)){
            log.warn("【SMS】发送失败,手机号码：{}, 请求结果：{}", mobile, res);
            throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
        }
        log.info("【SMS】发送短信成功， mobile:{},code:{}", mobile, code);
    }

    private String buildURL(String mobile, String code, String scene) {
        String tKey = DateUtils.format(new Date(), "yyyyMMddHHmmss");
        String password = properties.getPasswd();
        password = CodecUtils.md5Hex(CodecUtils.md5Hex(password) + tKey);

        String content = getContentByScene(scene);
        content = content.replace("{code}", code);
//        try {
//            content = URLEncoder.encode(content, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            log.error("【SMS】编码转换异常");
//        }

        StringBuilder sb = new StringBuilder();
        sb.append(properties.getUrl())
                .append("?username=" + properties.getUname())
                .append("&tkey=" + tKey)
                .append("&password=" + password)
                .append("&content=" + content)
                .append("&mobile=" + mobile)
                .append("&productid=" + properties.getProductId())
                .append("&xh=");

        if(log.isDebugEnabled()){
            log.debug("【SMS】请求参数: {}",sb);
        }
        return sb.toString();
    }

    /**
     * 根据scene查找content,默认是存map，也可以存db
     * @param scene
     * @return
     */
    private String getContentByScene(String scene) {
        String content = properties.getScenes().get(scene);
        if(StringUtils.isBlank(content)){
            log.warn("【SMS】 - 根据scene查找content，数据不存在");
            throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
        }
        return content;
    }
}
