package com.ifast.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ifast.api.config.JWTConfig;

/**
 * <pre>
 * </pre>
 * 
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

    /**
     * 演示模式
     */
    private boolean demoMode;
    /**
     * 调试模式
     */
    private boolean devMode;

    private JWTConfig jwt;

    public boolean isDemoMode() {
        return demoMode;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

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


    public JWTConfig getJwt() {
        return jwt;
    }

    public void setJwt(JWTConfig jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "IFastConfig [projectName=" + projectName + ", projectRootURL=" + projectRootURL + ", demoMode="
                + demoMode + ", devMode=" + devMode + ", jwt=" + jwt + "]";
    }

}
