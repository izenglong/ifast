package com.ifast.wxmp.controller.mp;

import com.ifast.common.base.AdminBaseController;
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
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

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
public class WxMediaController extends AdminBaseController {

    @Autowired
    private WeixinService wxService;
    @Autowired
    private MpArticleService mpArticleService;
    @Autowired
    private MpFansService mpFansService;

    ///////////////
    // 图片
    ///////////////

    @RequestMapping("/image/sync/{appId}/{articleId}")
    public Result imageSync(@PathVariable String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        log.info("素材同步");
        WxMediaUploadResult wxMediaUploadResult = null;
        try {
            File file = new File("/Users/Aron/dev_projects/idea/ifast/temp/" + System.currentTimeMillis() + ".jpg");
            FileUtils.copyURLToFile(new URL(article.getImgurl()), file);
            wxMediaUploadResult = wxService.init().getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, file);
        } catch (WxErrorException | IOException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaImageSyncError.getCodeStr());
        }
        log.debug(JSONUtils.beanToJson(wxMediaUploadResult));
        article.setTid(wxMediaUploadResult.getMediaId());
        mpArticleService.updateById(article);
        return Result.ok();
    }

    @RequestMapping("/image/groupsend/{appId}/{articleId}")
    public Result imageGroupsend(@PathVariable String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
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


    @RequestMapping("/news/sync/{appId}/{articleId}")
    public Result newsSync(@PathVariable String appId, @PathVariable Long articleId) throws IOException, WxErrorException {
        MpArticleDO article = mpArticleService.selectById(articleId);

        WeixinService weixinService = wxService.init();
        // 封面上传
        File file = new File("/Users/Aron/dev_projects/idea/ifast/temp/" + System.currentTimeMillis() + ".jpg");
        FileUtils.copyURLToFile(new URL(article.getImgurl()), file);
        WxMediaImgUploadResult wxMediaImgUploadResult = weixinService.getMaterialService().mediaImgUpload(file);
        wxMediaImgUploadResult.getUrl();

        log.info("素材同步");
        WxMpMaterialUploadResult wxMediaUploadResult = null;
        try {
            WxMpMaterialNews news = new WxMpMaterialNews();
            WxMpMaterialNews.WxMpMaterialNewsArticle art = new WxMpMaterialNews.WxMpMaterialNewsArticle();
            art.setTitle(article.getTitle());
            art.setContent(article.getContent());
            art.setDigest(article.getIntroduct());
            art.setUrl(article.getUrl());
            art.setThumbMediaId(article.getThumbid());
            news.addArticle(art);
            news.setCreatedTime(new Date());
            news.setUpdatedTime(new Date());
            wxMediaUploadResult = weixinService.getMaterialService().materialNewsUpload(news);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new IFastException(EnumErrorCode.wxmpMediaImageSyncError.getCodeStr());
        }
        log.debug(JSONUtils.beanToJson(wxMediaUploadResult));
        article.setTid(wxMediaUploadResult.getMediaId());
        mpArticleService.updateById(article);
        return Result.ok();
    }

    @RequestMapping("/news/groupsend/{appId}/{articleId}")
    public Result newsGroupsend(@PathVariable String appId, @PathVariable Long articleId) {
        MpArticleDO article = mpArticleService.selectById(articleId);
        WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
        message.addUser("oZQD8wvtRERdOzISxpBccQoJ8Hrs");
        message.addUser("oZQD8wlJnnU1ByTAEh3AKKw235oI");
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

}
