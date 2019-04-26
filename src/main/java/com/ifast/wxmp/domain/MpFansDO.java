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
 * 
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
 @TableName("wx_mp_fans")
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class MpFansDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @TableId
    private Long id;
    /** 公众号id */
    private Long mpId;
    /** 用户的标识，对当前公众号唯一 */
    private String openid;
    /** 昵称 */
    private String nickname;
    /** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。0未关注，1已关注 */
    private Integer subscribe;
    /** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
    private Date subscribeTime;
    /** 关注来源 */
    private String subscribeKey;
    /** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
    private Integer sex;
    /**  */
    private String city;
    /**  */
    private String country;
    /**  */
    private String province;
    /** 语言 */
    private String language;
    /** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。 */
    private String headimgurl;
    /**  */
    private String unionid;
    /** 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注 */
    private String remark;
    /** 分组ID */
    private Integer groupid;
    /** 用户状态 1:正常，0：禁用 */
    private Integer status;
    /**  */
    private String tagidList;

}
