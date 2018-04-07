package com.ifast.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月6日 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast")
public class IFastConfig {
    /**
     * 项目名，末尾不带 "/"
     */
    private String projectName;
    /**
     * 项目根目录，末尾带 "/"
     */
    private String projectRootURL;

    public String getProjectRootURL() {
        return projectRootURL;
    }

    public void setProjectRootURL(String projectRootURL) {
        this.projectRootURL = projectRootURL;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
