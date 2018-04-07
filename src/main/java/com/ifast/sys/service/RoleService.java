package com.ifast.sys.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.IService;
import com.ifast.sys.domain.RoleDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface RoleService extends IService<RoleDO> {
    List<RoleDO> findAll();
    List<RoleDO> findListByUserId(Serializable id);
}
