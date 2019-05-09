package com.ifast.wxmp.controller.mp;

import com.ifast.common.base.BaseController;
import com.ifast.common.config.IFastProperties;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.JSONUtils;
import com.ifast.common.utils.Result;
import com.ifast.wxmp.domain.MpArticleDO;
import com.ifast.wxmp.service.MpArticleService;
import com.ifast.wxmp.service.MpFansService;
import com.ifast.wxmp.service.WeixinService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <pre>
 * </pre>
 *
 * @Author Aron
 * @Date 2018/5/3
 */
@RestController
@RequestMapping("/wx/mp/api/media")
@Slf4j
public class WxMediaController extends BaseController {

    @Autowired
    private WeixinService wxService;
    @Autowired
    private MpArticleService mpArticleService;
    @Autowired
    private MpFansService mpFansService;
    @Autowired
    private IFastProperties properties;

    ///////////////
    // 图片
    ///////////////

    @RequestMapping("/image/sync/{articleId}")
    public Result imageSync(String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        log.info("素材同步");
        WxMpMaterialUploadResult wxMpMaterialUploadResult = null;
        try {
            File file = new File(properties.getTempDir() + System.currentTimeMillis() + ".jpg");
            FileUtils.copyURLToFile(new URL(article.getImgurl()), file);
            WxMpMaterial material = new WxMpMaterial();
            material.setName(file.getName());
            material.setFile(file);
            wxMpMaterialUploadResult = wxService.init().getMaterialService().materialFileUpload(WxConsts.MediaFileType.IMAGE, material);
        } catch (WxErrorException | IOException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaImageSyncError.getCodeStr());
        }
        log.debug(JSONUtils.beanToJson(wxMpMaterialUploadResult));
        article.setTid(wxMpMaterialUploadResult.getMediaId());
        mpArticleService.updateById(article);
        return Result.ok();
    }

    @RequestMapping("/image/groupsend/{articleId}")
    public Result imageGroupsend(String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
        // TODO 群发对象
        message.addUser(mpFansService.selectById(1).getOpenid());
        message.addUser(mpFansService.selectById(1).getOpenid());
        message.setMsgType(article.getMsgtype());
        message.setMediaId(article.getTid());
        try {
            wxService.init().getMassMessageService().massOpenIdsMessageSend(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaGroupSendSyncError.getCodeStr());
        }
        return Result.ok();
    }

    ///////////////
    // 图文
    ///////////////


    @RequestMapping("/news/sync/{articleId}")
    public Result newsSync(String appId, @PathVariable Long articleId) throws IOException, WxErrorException {
        MpArticleDO article = mpArticleService.selectById(articleId);

        WeixinService weixinService = wxService.init();
        // 封面上传
        File file = new File(properties.getTempDir() + System.currentTimeMillis() + ".jpg");
        FileUtils.copyURLToFile(new URL(article.getImgurl()), file);
        WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        WxMediaUploadResult wxMediaImgUploadResult = weixinService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, file);
        String mediaId = wxMediaImgUploadResult.getMediaId();

        log.info("素材同步");
        WxMpMassUploadResult wxMediaUploadResult;
        WxMpMassNews news = new WxMpMassNews();
        WxMpMassNews.WxMpMassNewsArticle art = new WxMpMassNews.WxMpMassNewsArticle();
        art.setTitle(article.getTitle());
        art.setContent(article.getContent());
        art.setDigest(article.getIntroduct());
        // TODO 作者
        art.setAuthor("Aron");
        // 原文链接
        art.setContentSourceUrl(article.getUrl());
        // 图片封面
        art.setThumbMediaId(mediaId);
        // TODO 显示封面
        art.setShowCoverPic(true);
        news.addArticle(art);
        try {
            wxMediaUploadResult = weixinService.getMassMessageService().massNewsUpload(news);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaImageSyncError.getCodeStr());
        }
        log.debug(JSONUtils.beanToJson(wxMediaUploadResult));
        article.setTid(wxMediaUploadResult.getMediaId());
        article.setThumbid(mediaId);
        mpArticleService.updateById(article);
        return Result.ok();
    }

    @RequestMapping("/news/groupsend/{articleId}")
    public Result newsGroupsend(String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
        // TODO 群发对象
        message.addUser(mpFansService.selectById(1L).getOpenid());
        message.addUser(mpFansService.selectById(1L).getOpenid());
        message.setMsgType(convertMsgtype(article.getMsgtype()));
        message.setMediaId(article.getTid());
        try {
            wxService.init().getMassMessageService().massOpenIdsMessageSend(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaGroupSendSyncError.getCodeStr());
        }
        return Result.ok();
    }

    private String convertMsgtype(String msgType) {
        if (WxConsts.MaterialType.NEWS.equals(msgType)) {
            return WxConsts.MassMsgType.MPNEWS;
        }
        return msgType;
    }

}
