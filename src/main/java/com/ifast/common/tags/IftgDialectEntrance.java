package com.ifast.common.tags;

import com.ifast.common.tags.processor.IftgSelectProcessor;
import org.springframework.stereotype.Component;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;

import java.util.HashSet;
import java.util.Set;


/**
 * 自定注解处理入口
 *
 * @author: zet
 * @date:2018/8/22
 */
@Component
public class IftgDialectEntrance extends SpringStandardDialect {
    public String getPrefix() {
        return "iftg";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        //<iftg:select/> 注解
        processors.add(new IftgSelectProcessor());
        return processors;
    }
}
