package com.ifast.wxmp.handler;

import com.ifast.common.utils.JSONUtils;
import com.ifast.wxmp.builder.TextBuilder;
import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.pojo.type.Const;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.wxmp.service.MpFansService;
import com.ifast.wxmp.service.WeixinService;
import com.ifast.wxmp.util.WxMpConfigHolder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 *     关注
 * </pre>
 * <p>
 * <small> 2018年6月13日 | Aron</small>
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private MpFansService mpFansService;
    @Autowired
    private MpConfigService mpConfigService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 openid: " + wxMessage.getFromUser());

        WeixinService weixinService = (WeixinService) wxMpService;

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            if(logger.isDebugEnabled()){
                this.logger.debug("同步微信用户信息数据:{}",JSONUtils.beanToJson(userWxInfo));
            }
            MpFansDO fans = MpFansDO.builder().build();
            convert(userWxInfo, fans);
            mpFansService.sync(fans);

        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    private void convert(WxMpUser wxUser, MpFansDO fans) {
        BeanUtils.copyProperties(wxUser, fans);
//        fans.setCity(wxUser.getCity());
//        fans.setCountry(wxUser.getCountry());
        fans.setGroupid(wxUser.getGroupId());
        fans.setHeadimgurl(wxUser.getHeadImgUrl());
//        fans.setLanguage(wxUser.getLanguage());
//        fans.setNickname(wxUser.getNickname());
        fans.setOpenid(wxUser.getOpenId());
//        fans.setProvince(wxUser.getProvince());
//        fans.setRemark(wxUser.getRemark());
//        fans.setSex(wxUser.getSex());
        fans.setMpId(this.getMpIdByAppId(WxMpConfigHolder.getCurrentAppId()));
        // fans.setStatus(status);
        // fans.setSubscribeKey();
        fans.setSubscribe(Const.Subscribe.YES);
        fans.setSubscribeTime(new Date());
        fans.setTagidList(Arrays.toString(wxUser.getTagIds()));
        fans.setUnionid(wxUser.getUnionId());

        this.logger.debug("convert return :{}", fans);

    }

    public long getMpIdByAppId(String appId) {
        return mpConfigService.findOneByKv("appId", appId).getId();
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
        // TODO
        logger.debug("扫码事件触发... ：", wxMessage);
        return null;
    }

}
