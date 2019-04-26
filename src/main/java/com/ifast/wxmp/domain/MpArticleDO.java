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
 * 
 * </pre>
 * <small> 2018-10-18 21:21:36 | Aron</small>
 */
@Data
@TableName("wx_mp_article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpArticleDO implements Serializable {
	@TableId
	private Long id;

    /** 微信ID */
    private Long mpId;

    /** 文章标题 */
    private String title;

    /** 文章关键字，以空格隔开 */
    private String keyword;

    /** 图片链接 */
    private String imgurl;

    /** 图文简介 */
    private String introduct;

    /** 文章内容 */
    private String content;

    /** 文章状态：1启用 0停用 */
    private String status;

    /** 排序 */
    private Integer sort;

    /** 创建事件 */
    private Date createdate;

    /** 更新时间 */
    private Date updatedate;

    /** 图文类型，参考静态字段：WX_ARTCLE_TYPE */
    private String msgtype;

    /** 微信反馈图文ID */
    private String tid;

    /** 图文地址 */
    private String url;

    /** 缩略图的mediaID */
    private String thumbid;

}
