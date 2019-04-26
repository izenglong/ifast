package com.ifast.wxmp.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.wxmp.dao.MpFansDao;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.pojo.type.Const;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.wxmp.service.MpFansService;
import com.ifast.wxmp.service.WeixinService;
import com.ifast.wxmp.util.WxMpConfigHolder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
@Service
public class MpFansServiceImpl extends CoreServiceImpl<MpFansDao, MpFansDO> implements MpFansService {


    @Autowired
    private WeixinService weixinService;
    @Autowired
    private MpConfigService mpConfigService;


    @Override
    public void sync(MpFansDO fans) {
        MpFansDO one = baseMapper.selectOne(MpFansDO.builder().openid(fans.getOpenid()).build());
        if (one == null) {
            log.debug("新用户保存信息到db");
            insert(fans);
            return;
        }
        log.debug("老用户更新信息");
        fans.setId(one.getId());
        BeanUtils.copyProperties(fans, one);
        updateById(one);
    }

    private void convert(WxMpUser wxUser, MpFansDO fans, String appId) {
        BeanUtils.copyProperties(wxUser, fans);
        fans.setCity(wxUser.getCity());
        fans.setCountry(wxUser.getCountry());
        fans.setGroupid(wxUser.getGroupId());
        fans.setHeadimgurl(wxUser.getHeadImgUrl());
        fans.setLanguage(wxUser.getLanguage());
        fans.setNickname(wxUser.getNickname());
        fans.setOpenid(wxUser.getOpenId());
        // fans.setProvince(wxUser.getProvince());
        // fans.setRemark(wxUser.getRemark());
        // fans.setSex(wxUser.getSex());
        // fans.setStatus(status);
        // fans.setSubscribeKey();
        fans.setSubscribe(wxUser.getSubscribe() ? Const.Subscribe.YES : Const.Subscribe.NO);
        fans.setSubscribeTime(new Date());
        fans.setTagidList(Arrays.toString(wxUser.getTagIds()));
        fans.setUnionid(wxUser.getUnionId());
        if (StringUtils.isBlank(appId)) {
            appId = WxMpConfigHolder.getCurrentAppId();
        }
        fans.setMpId(mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId());

        log.debug("convert return :{}", fans);

    }


    @Override
    public void sync(List<Long> ids) {
        ids.stream().forEach(id -> {
            MpFansDO mpFansDO = selectById(id);
            try {
                WxMpUser wxMpUser = weixinService.init().getUserService().userInfo(mpFansDO.getOpenid());
                // appId
                convert(wxMpUser, mpFansDO, null);
                updateById(mpFansDO);
            } catch (WxErrorException e) {
                e.printStackTrace();
                throw new IFastException(EnumErrorCode.wxmpFansSyncError.getCodeStr());
            }
        });
    }

    @Override
    public void syncWxMp(final String appId) {
        WeixinService wx = weixinService.init();
        WxMpUserService userService = wx.getUserService();
        try {
            WxMpUserList wxMpUserList = userService.userList(null);
            long total = wxMpUserList.getTotal();
            int count = wxMpUserList.getCount();
            String nextOpenid = wxMpUserList.getNextOpenid();
            syncToDb(appId, wxMpUserList);
            while (count < total) {
                WxMpUserList wxMpUserList2 = userService.userList(nextOpenid);
                count += wxMpUserList2.getCount();
                nextOpenid = wxMpUserList2.getNextOpenid();

                syncToDb(appId, wxMpUserList2);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    private void syncToDb(final String appId, WxMpUserList wxMpUserList2) {
        wxMpUserList2.getOpenids().stream().filter(openid -> Objects.isNull(baseMapper.selectOne(MpFansDO.builder().openid(openid).build()))).forEach(openid -> {
            if(log.isDebugEnabled()){
                log.debug("sync openid {}", openid);
            }
            // 获取微信用户基本信息
            WxMpUser userWxInfo = null;
            try {
                userWxInfo = weixinService.getUserService().userInfo(openid, null);
            } catch (WxErrorException e1) {
                e1.printStackTrace();
            }

            if (userWxInfo != null) {
                this.log.debug("同步微信用户信息数据 from 微信服务器");
                MpFansDO fans = MpFansDO.builder().build();
                convert(userWxInfo, fans, appId);
                insert(fans);

            }
        });
    }

}
