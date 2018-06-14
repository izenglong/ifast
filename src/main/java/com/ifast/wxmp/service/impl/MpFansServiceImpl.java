package com.ifast.wxmp.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ifast.wxmp.dao.MpFansDao;
import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.service.MpFansService;
import com.ifast.common.base.CoreServiceImpl;

/**
 * 
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
@Service
public class MpFansServiceImpl extends CoreServiceImpl<MpFansDao, MpFansDO> implements MpFansService {

    @Override
    public void sync(MpFansDO fans) {
        MpFansDO one = findOneByKv("openid", fans.getOpenid());
        if(one == null) {
            log.debug("新用户保存信息到db");
            insert(fans);
            return;
        }
        log.debug("老用户更新信息");
        BeanUtils.copyProperties(fans, one);
        updateById(one);
    }

}
