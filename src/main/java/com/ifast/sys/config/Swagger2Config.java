package com.ifast.sys.config;

import com.ifast.common.config.IFastConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Autowired
    IFastConfig ifastConfig;

    @Bean
    public Docket createRestApi() {
        String projectRootURL = ifastConfig.getProjectRootURL();
        int s = projectRootURL == null ? -1 : projectRootURL.indexOf("//");
        int e = s == -1 ? -1 : projectRootURL.indexOf('/', s + 2);
        String host = s == -1 ? null : projectRootURL.substring(s + 2, e == -1 ? projectRootURL.length() : e);
        return new Docket(DocumentationType.SWAGGER_2).host(host).apiInfo(apiInfo()).select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    // 构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("功能测试")
                // 创建人
                .contact(new Contact("Aron", "izenglong@163.com", "izenglong@163.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述").build();
    }
}