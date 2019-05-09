package com.ifast.wxmp.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * <pre>
 * 微信配置表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
@TableName("wx_mp_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpConfigDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * token
     */
    private String token;
    /**
     * APPID
     */
    private String appId;
    /**
     * AppSecret
     */
    private String appSecret;
    /**
     * 1加密 0不加密
     */
    private Integer msgMode;
    /**
     * 秘钥
     */
    private String msgSecret;
    /**
     * 公众号名字
     */
    private String mpName;
    /**
     * 公众号类型： 1.订阅号 2.服务号 3.企业号 4.小程序 5. 测试号
     */
    private Integer appType;
    /**
     * 认证授权：1已认证 0未认证
     */
    private Integer isAuth;
    /**
     * 提示订阅URL
     */
    private String subscribeUrl;
    /**
     * logo
     */
    private String logo;
    /**
     * 创建时间
     */
    private Date createTime;

}
