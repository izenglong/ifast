package com.ifast.common.component.sms.support;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.type.EnumErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/10/10 15:10 | Aron</small>
 */
@Slf4j
public class MapSceneRepository implements SceneRepository{

    private Map<String, String> scenes;

    public MapSceneRepository(Map<String, String> scenes){
        this.scenes = scenes;
    }

    @Override
    public String getContent(String scene){
        String content = scenes.get(scene);
        if(StringUtils.isBlank(content)){
            log.warn("【SMS】 - 根据scene查找content，数据不存在");
            throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
        }
        return content;
    }
}
