package com.ifast.wxmp.controller.mp;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ifast.common.base.BaseController;
import com.ifast.wxmp.service.WeixinService;
import com.ifast.wxmp.util.WxMpConfigHolder;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * <pre>
 * </pre>
 *
 * @Author Aron
 * @Date 2018/5/3
 */
@RestController
@RequestMapping("/wx/mp/msg")
public class WxMsgController extends BaseController {
    
    @Autowired
    private WeixinService wxService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ResponseBody
    @GetMapping(value= "/{currentAppId}", produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable("currentAppId") String currentAppId, @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        this.logger.debug("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        WxMpConfigHolder.setCurrentAppId(currentAppId);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        
        if (this.getWxService().checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        
        return "非法请求";
    }
    
    @ResponseBody
    @PostMapping(value= "/{currentAppId}", produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable("currentAppId") String currentAppId, @RequestBody String requestBody, @RequestParam("signature") String signature,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
        WxMpConfigHolder.setCurrentAppId(currentAppId);
        this.logger.debug(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);
        
        if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        
        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.getWxService().route(inMessage);
            if (outMessage == null) {
                return "";
            }
            
            out = outMessage.toXml();
        } else if ("aes".equals(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                    this.getWxService().getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.getWxService().route(inMessage);
            if (outMessage == null) {
                return "";
            }
            
            out = outMessage.toEncryptedXml(this.getWxService().getWxMpConfigStorage());
        }
        
        this.logger.debug("\n组装回复信息：{}", out);
        
        return out;
    }
    
    protected WeixinService getWxService() {
        if(wxService.getRequestHttpClient() == null) {
            wxService.initHttp();
        }
        return this.wxService;
    }
    
}
