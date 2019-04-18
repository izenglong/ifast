package com.ifast.wxmp.handler;

import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.pojo.type.Const;
import com.ifast.wxmp.service.MpFansService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * <pre>
 *     取消关注
 * </pre>
 * 
 * <small> 2018年6月13日 | Aron</small>
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    
    @Autowired
    private MpFansService mpFansService;
    
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("用户取消关注 openid: " + openId);
        MpFansDO fans = mpFansService.selectOne(MpFansDO.builder().openid(openId).build());
        fans.setSubscribe(Const.Subscribe.NO);
        mpFansService.updateById(fans);
        return null;
    }

}
