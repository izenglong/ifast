package com.ifast.common.service.impl;

import org.springframework.stereotype.Service;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.dao.ConfigDao;
import com.ifast.common.domain.ConfigDO;
import com.ifast.common.service.ConfigService;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月6日 | Aron</small>
 */
@Service
public class ConfigServiceImpl extends CoreServiceImpl<ConfigDao, ConfigDO> implements ConfigService {

    @Override
    public ConfigDO getByKey(String k) {
        ConfigDO entity = new ConfigDO();
        entity.setK(k);
        return baseMapper.selectOne(entity);
    }

    @Override
    public String getValuByKey(String k) {
        ConfigDO bean = this.getByKey(k);
        return bean == null ? "" : bean.getV();
    }

}
