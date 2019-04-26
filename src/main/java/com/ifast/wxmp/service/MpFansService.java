package com.ifast.wxmp.service;

import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.common.base.CoreService;

import java.util.List;

/**
 * 
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
public interface MpFansService extends CoreService<MpFansDO> {

    /**
     * <pre>
     * 根据openid查表是否存在，如果存在则更新fans数据
     * </pre>
     * 
     * <small> 2018年6月13日 | Aron</small>
     * 
     * @param fans
     */
    void sync(MpFansDO fans);
    void sync(List<Long> ids);

    void syncWxMp(String appId);
}
