package com.ifast.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gaoyuzhe
 * @date 2017/12/14.
 */
@Configuration
public class DateConverConfiguration {
    @Bean
    public Converter<String, Date> stringDateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
            	if(source==null || (source=source.trim()).length()<10) return null;
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
                } catch (Exception e) {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd").parse((String) source);
                    } catch (ParseException ignore) {
                    	return null;
                    }
                }
            }
        };
    }

}
