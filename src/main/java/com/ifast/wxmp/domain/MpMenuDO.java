package com.ifast.wxmp.domain;

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
 * 微信菜单表
 * </pre>
 * <small> 2018-10-19 15:47:16 | Aron</small>
 */
@Data
@TableName("wx_mp_menu")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpMenuDO implements Serializable {
	@TableId
	private Long id;

    /**  */
    private Long parentidx;

    /** 菜单类型：1主菜单，2链接，3文本，4关键字，5扫码，6发图，7发送位置 */
    private String menutype;

    /**  */
    private String menuname;

    /**  */
    private String menukey;

    /** 菜单链接 */
    private String menuurl;

    /**  */
    private String replycontent;

    /** 扫码类型：1扫码带事件，2扫码带提示 */
    private Integer scantype;

    /** 发图类型：1，系统拍照发图；2,，拍照或者相册发图；3，微信相册发图 */
    private Integer picturetype;

    /** 发送位置 */
    private String location;

    /** 菜单排序 */
    private Integer sort;

    /** 菜单状态 */
    private Integer status;

    /** 创建时间 */
    private Date createtime;

    /** 更新时间 */
    private Date updatetime;

    /** 微信配置ID */
    private Long mpid;

    /**  */
    private Long idx;

}
