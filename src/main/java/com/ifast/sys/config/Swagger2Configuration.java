package com.ifast.sys.config;

import com.ifast.common.config.IFastProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Configuration {

    @Autowired
    IFastProperties ifastConfig;
    @Autowired
    SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        String projectRootURL = ifastConfig.getProjectRootURL();
        int s = projectRootURL == null ? -1 : projectRootURL.indexOf("//");
        int e = s == -1 ? -1 : projectRootURL.indexOf('/', s + 2);
        String host = s == -1 ? null : projectRootURL.substring(s + 2, e == -1 ? projectRootURL.length() : e);
        return new Docket(DocumentationType.SWAGGER_2).host(host).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).paths(path -> path.startsWith("/api/") || path.startsWith("/demo/") | path.startsWith("/test/")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).build();
    }
}