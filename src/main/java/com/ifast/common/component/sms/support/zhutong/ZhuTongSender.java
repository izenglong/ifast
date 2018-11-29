package com.ifast.common.component.sms.support.zhutong;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.component.sms.support.SmsSender;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.CodecUtils;
import com.ifast.common.utils.DateUtils;
import lombok.Setter;
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

    private final static String SENDER_SERVER_URL = "http://www.ztsms.cn/sendNSms.do";

    private final static String SUCCESS_CODE = "1";

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate = new RestTemplate();

    public ZhuTongSender() {}

    public ZhuTongSender(ZhuTongProperties properties) {
        this.properties = properties;
    }

    @Setter
    private ZhuTongProperties properties;


    @Override
    public void send(String mobile, String code, String content) {

        String url = buildURL(mobile, code, content);

        String res = restTemplate.getForObject(url, String.class);
        if (log.isDebugEnabled()) {
            log.debug("【SMS】请求结果:{}", res);
        }
        String[] split = null;
        if (res != null) {
            split = res.split(",");
        }
        if (split != null && split.length == 2) {
            res = split[0];
        }

        if (!SUCCESS_CODE.equals(res)) {
            log.warn("【SMS】发送失败,手机号码：{}, 请求结果：{}", mobile, res);
            throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
        }
        log.info("【SMS】发送短信成功， mobile:{},code:{}", mobile, code);
    }

    private String buildURL(String mobile, String code, String content) {
        String tKey = DateUtils.format(new Date(), "yyyyMMddHHmmss");
        String password = properties.getPasswd();
        password = CodecUtils.md5Hex(CodecUtils.md5Hex(password) + tKey);

        content = content.replace("{code}", code);
        //        try {
        //            content = URLEncoder.encode(content, "UTF-8");
        //        } catch (UnsupportedEncodingException e) {
        //            log.error("【SMS】编码转换异常");
        //        }

        StringBuilder sb = new StringBuilder();
        sb.append(SENDER_SERVER_URL).append("?username=" + properties.getUname()).append("&tkey=" + tKey).append("&password=" + password).append("&content=" + content).append("&mobile=" + mobile).append("&productid=" + properties.getProductId()).append("&xh=");

        if (log.isDebugEnabled()) {
            log.debug("【SMS】请求参数: {}", sb);
        }
        return sb.toString();
    }
}
