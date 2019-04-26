package com.ifast.wxmp.handler;

import com.ifast.wxmp.builder.ImageBuilder;
import com.ifast.wxmp.builder.NewsBuilder;
import com.ifast.wxmp.builder.TextBuilder;
import com.ifast.wxmp.domain.MpArticleDO;
import com.ifast.wxmp.service.MpArticleService;
import com.ifast.wxmp.service.WeixinService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * </pre>
 *
 * <small> 2018年6月13日 | Aron</small>
 */
@Component
public class MenuHandler extends AbstractHandler {

    private static String DEFAULT_ARTICLE_KEYWORD = "DEFAULT_ARTICLE_KEYWORD";

    @Autowired
    private MpArticleService mpArticleService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        WeixinService weixinService = (WeixinService) wxMpService;

        String key = wxMessage.getEventKey();

        MpArticleDO mpArticleDO = mpArticleService.selectOne(MpArticleDO.builder().keyword(key).build());
        if (Objects.isNull(mpArticleDO)) {
            logger.info("未找到与关键字【{}】匹配的回复消息。默认回复key【{}】", key, DEFAULT_ARTICLE_KEYWORD);
            MpArticleDO defaultArticle = mpArticleService.selectOne(MpArticleDO.builder().keyword(DEFAULT_ARTICLE_KEYWORD).build());
            if (Objects.isNull(defaultArticle)) {
                logger.info("未找到与关键字【{}】匹配的回复消息。回复内容【{}】", DEFAULT_ARTICLE_KEYWORD, DEFAULT_ARTICLE_KEYWORD);
                return WxMpXmlOutMessage.TEXT().content(DEFAULT_ARTICLE_KEYWORD).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
            }
            return WxMpXmlOutMessage.TEXT().content(defaultArticle.getContent()).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
        }

        WxMpXmlOutMessage builder = null;
        switch (mpArticleDO.getMsgtype()) {
            case WxConsts.XmlMsgType.TEXT:
                builder = new TextBuilder().build(mpArticleDO.getContent(), wxMessage, weixinService);
                break;
            case WxConsts.XmlMsgType.IMAGE:
                builder = new ImageBuilder().build(mpArticleDO.getThumbid(), wxMessage, weixinService);
                break;
            case WxConsts.XmlMsgType.VOICE:
                break;
            case WxConsts.XmlMsgType.VIDEO:
                break;
            case WxConsts.XmlMsgType.NEWS:
                builder = new NewsBuilder().build(key, wxMessage, weixinService);
                break;
            default:
                break;
        }

        if (builder != null) {
            try {
                return builder;
            } catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }

        return null;

    }

}
