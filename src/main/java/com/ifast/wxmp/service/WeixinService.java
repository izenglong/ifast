package com.ifast.wxmp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.ifast.wxmp.handler.AbstractHandler;
import com.ifast.wxmp.handler.KfSessionHandler;
import com.ifast.wxmp.handler.LocationHandler;
import com.ifast.wxmp.handler.LogHandler;
import com.ifast.wxmp.handler.MenuHandler;
import com.ifast.wxmp.handler.MsgHandler;
import com.ifast.wxmp.handler.NullHandler;
import com.ifast.wxmp.handler.StoreCheckNotifyHandler;
import com.ifast.wxmp.handler.SubscribeHandler;
import com.ifast.wxmp.handler.UnsubscribeHandler;
import com.ifast.wxmp.util.WxMpConfigHolder;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <small> 2018年6月13日 | Aron</small>
 */
@Service
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class WeixinService extends WxMpServiceImpl implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected LogHandler logHandler;

    @Autowired
    protected NullHandler nullHandler;

    @Autowired
    protected KfSessionHandler kfSessionHandler;

    @Autowired
    protected StoreCheckNotifyHandler storeCheckNotifyHandler;

    @Autowired
    private LocationHandler locationHandler;

    @Autowired
    private MenuHandler menuHandler;

    @Autowired
    private MsgHandler msgHandler;

    @Autowired
    private UnsubscribeHandler unsubscribeHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;

    private WxMpMessageRouter router;

    @Override
    public WxMpConfigStorage getWxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = WxMpConfigHolder.getWxMpInMemoryConfigStorage();

        log.debug("当前公众号配置：" + wxMpInMemoryConfigStorage);
        return wxMpInMemoryConfigStorage;
    }

    private void refreshRouter() {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);

        // 记录所有事件的日志
        newRouter.rule().handler(this.logHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.CLICK)
                .handler(this.getMenuHandler()).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.VIEW)
                .handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
                .handler(this.getSubscribeHandler()).end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(this.getUnsubscribeHandler()).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).event(WxConsts.EventType.LOCATION)
                .handler(this.getLocationHandler()).end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.getLocationHandler()).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN)
                .handler(this.getScanHandler()).end();

        // 默认
        newRouter.rule().async(false).handler(this.getMsgHandler()).end();

        this.router = newRouter;
    }

    public WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    public boolean hasKefuOnline() {
        try {
            WxMpKfOnlineList kfOnlineList = this.getKefuService().kfOnlineList();
            return kfOnlineList != null && kfOnlineList.getKfOnlineList().size() > 0;
        } catch (Exception e) {
            this.logger.error("获取客服在线状态异常: " + e.getMessage(), e);
        }

        return false;
    }

    protected MenuHandler getMenuHandler() {
        return this.menuHandler;
    }

    protected SubscribeHandler getSubscribeHandler() {
        return this.subscribeHandler;
    }

    protected UnsubscribeHandler getUnsubscribeHandler() {
        return this.unsubscribeHandler;
    }

    protected AbstractHandler getLocationHandler() {
        return this.locationHandler;
    }

    protected MsgHandler getMsgHandler() {
        return this.msgHandler;
    }

    protected AbstractHandler getScanHandler() {
        return null;
    }

    @Override
    public void afterPropertiesSet(){
        this.refreshRouter();
    }

    public WeixinService init(){
        this.initHttp();
        return this;
    }

}
