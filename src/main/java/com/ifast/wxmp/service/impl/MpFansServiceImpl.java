package com.ifast.wxmp.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.wxmp.dao.MpFansDao;
import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.service.MpFansService;
import com.ifast.wxmp.service.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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


    @Override
    public void sync(MpFansDO fans) {
        MpFansDO one = findOneByKv("openid", fans.getOpenid());
        if (one == null) {
            log.debug("新用户保存信息到db");
            insert(fans);
            return;
        }
        log.debug("老用户更新信息");
        BeanUtils.copyProperties(fans, one);
        updateById(one);
    }

    private void convert(WxMpUser wxUser, MpFansDO fans) {
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
        fans.setSubscribe(wxUser.getSubscribe() ? 1 : 0);
        fans.setSubscribeTime(new Date());
        fans.setTagidList(Arrays.toString(wxUser.getTagIds()));
        fans.setUnionid(wxUser.getUnionId());

        log.debug("convert return :{}", fans);

    }


    @Override
    public void sync(List<Long> ids) {
        ids.stream().forEach(id -> {
            MpFansDO mpFansDO = selectById(id);
            try {
                WxMpUser wxMpUser = weixinService.init().getUserService().userInfo(mpFansDO.getOpenid());
                convert(wxMpUser, mpFansDO);
                updateById(mpFansDO);
            } catch (WxErrorException e) {
                e.printStackTrace();
                throw  new IFastException(EnumErrorCode.wxmpFansSyncError.getCodeStr());
            }
        });
    }

}
