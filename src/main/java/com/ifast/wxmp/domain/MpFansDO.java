package com.ifast.wxmp.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
 @TableName("wx_mp_fans")
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

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }
    /**
     * 设置：公众号id
     */
    public void setMpId(Long mpId) {
        this.mpId = mpId;
    }
    /**
     * 获取：公众号id
     */
    public Long getMpId() {
        return mpId;
    }
    /**
     * 设置：用户的标识，对当前公众号唯一
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    /**
     * 获取：用户的标识，对当前公众号唯一
     */
    public String getOpenid() {
        return openid;
    }
    /**
     * 设置：昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    /**
     * 获取：昵称
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * 设置：用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。0未关注，1已关注
     */
    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }
    /**
     * 获取：用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。0未关注，1已关注
     */
    public Integer getSubscribe() {
        return subscribe;
    }
    /**
     * 设置：用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
    /**
     * 获取：用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }
    /**
     * 设置：关注来源
     */
    public void setSubscribeKey(String subscribeKey) {
        this.subscribeKey = subscribeKey;
    }
    /**
     * 获取：关注来源
     */
    public String getSubscribeKey() {
        return subscribeKey;
    }
    /**
     * 设置：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    /**
     * 获取：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    public Integer getSex() {
        return sex;
    }
    /**
     * 设置：
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * 获取：
     */
    public String getCity() {
        return city;
    }
    /**
     * 设置：
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * 获取：
     */
    public String getCountry() {
        return country;
    }
    /**
     * 设置：
     */
    public void setProvince(String province) {
        this.province = province;
    }
    /**
     * 获取：
     */
    public String getProvince() {
        return province;
    }
    /**
     * 设置：语言
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    /**
     * 获取：语言
     */
    public String getLanguage() {
        return language;
    }
    /**
     * 设置：用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    /**
     * 获取：用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    public String getHeadimgurl() {
        return headimgurl;
    }
    /**
     * 设置：
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    /**
     * 获取：
     */
    public String getUnionid() {
        return unionid;
    }
    /**
     * 设置：公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置：分组ID
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }
    /**
     * 获取：分组ID
     */
    public Integer getGroupid() {
        return groupid;
    }
    /**
     * 设置：用户状态 1:正常，0：禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：用户状态 1:正常，0：禁用
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 设置：
     */
    public void setTagidList(String tagidList) {
        this.tagidList = tagidList;
    }
    /**
     * 获取：
     */
    public String getTagidList() {
        return tagidList;
    }
}
