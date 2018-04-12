package com.ifast.wxmp.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 微信配置表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
 @TableName("wx_mp_config")
public class MpConfigDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 主键ID */
    @TableId
    private Integer id;
    /** token */
    private String token;
    /** APPID */
    private String appId;
    /** AppSecret */
    private String appSecret;
    /** 1加密 0不加密 */
    private Integer msgMode;
    /** 秘钥 */
    private String msgSecret;
    /** 公众号名字 */
    private String mpName;
    /** 公众号类型： 1.订阅号 2.服务号 3.企业号 4.小程序 5. 测试号 */
    private Integer appType;
    /** 认证授权：1已认证 0未认证 */
    private Integer isAuth;
    /** 提示订阅URL */
    private String subscribeUrl;
    /** logo */
    private String logo;
    /** 创建时间 */
    private Date createTime;

    /**
     * 设置：主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：主键ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：token
     */
    public void setToken(String token) {
        this.token = token;
    }
    /**
     * 获取：token
     */
    public String getToken() {
        return token;
    }
    /**
     * 设置：APPID
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }
    /**
     * 获取：APPID
     */
    public String getAppId() {
        return appId;
    }
    /**
     * 设置：AppSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    /**
     * 获取：AppSecret
     */
    public String getAppSecret() {
        return appSecret;
    }
    /**
     * 设置：1加密 0不加密
     */
    public void setMsgMode(Integer msgMode) {
        this.msgMode = msgMode;
    }
    /**
     * 获取：1加密 0不加密
     */
    public Integer getMsgMode() {
        return msgMode;
    }
    /**
     * 设置：秘钥
     */
    public void setMsgSecret(String msgSecret) {
        this.msgSecret = msgSecret;
    }
    /**
     * 获取：秘钥
     */
    public String getMsgSecret() {
        return msgSecret;
    }
    /**
     * 设置：公众号名字
     */
    public void setMpName(String mpName) {
        this.mpName = mpName;
    }
    /**
     * 获取：公众号名字
     */
    public String getMpName() {
        return mpName;
    }
    /**
     * 设置：公众号类型： 1.订阅号 2.服务号 3.企业号 4.小程序 5. 测试号
     */
    public void setAppType(Integer appType) {
        this.appType = appType;
    }
    /**
     * 获取：公众号类型： 1.订阅号 2.服务号 3.企业号 4.小程序 5. 测试号
     */
    public Integer getAppType() {
        return appType;
    }
    /**
     * 设置：认证授权：1已认证 0未认证
     */
    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }
    /**
     * 获取：认证授权：1已认证 0未认证
     */
    public Integer getIsAuth() {
        return isAuth;
    }
    /**
     * 设置：提示订阅URL
     */
    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }
    /**
     * 获取：提示订阅URL
     */
    public String getSubscribeUrl() {
        return subscribeUrl;
    }
    /**
     * 设置：logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
    /**
     * 获取：logo
     */
    public String getLogo() {
        return logo;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
}
