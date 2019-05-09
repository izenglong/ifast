package com.ifast.oss.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.component.oss.support.FileNameUtils;
import com.ifast.common.component.oss.support.UploadServer;
import com.ifast.common.config.IFastProperties;
import com.ifast.common.utils.FileType;
import com.ifast.oss.dao.FileDao;
import com.ifast.oss.domain.FileDO;
import com.ifast.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class FileServiceImpl extends CoreServiceImpl<FileDao, FileDO> implements FileService {

    @Autowired
    private IFastProperties ifastConfig;
    @Autowired
    private UploadServer uploader;

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        //处理浏览器文件名获取兼容问题

        String url = uploader.upload(uploadBytes, FileNameUtils.getFileName(fileName, ifastConfig));
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, new Date());
        super.insert(sysFile);
        return url;
    }
}
