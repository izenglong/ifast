package com.ifast.common.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.dao.DictDao;
import com.ifast.common.domain.DictDO;
import com.ifast.common.service.DictService;
import com.ifast.sys.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class DictServiceImpl extends CoreServiceImpl<DictDao, DictDO> implements DictService {

    @Override
    public List<DictDO> listType() {
        return baseMapper.listType();
    }

    @Override
    public String getName(String type, String value) {
        DictDO selectOne = DictDO.dao.selectOne("type = {0} and value = {1}", type, value);
        return selectOne == null ? "" : selectOne.getName();
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        List<DictDO> hobbyList = DictDO.dao.selectList("type = {0}", "hobby");

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<DictDO> getSexList() {
        return DictDO.dao.selectList("type = {0}", "sex");
    }

}
