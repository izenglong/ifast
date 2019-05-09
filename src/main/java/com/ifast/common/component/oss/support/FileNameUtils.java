package com.ifast.common.component.oss.support;

import com.ifast.common.config.IFastProperties;
import com.ifast.common.utils.DateUtils;

import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 * <small> 2019-04-26 18:24 | Aron</small>
 */
public class FileNameUtils {
    public static String getFileName(String originalFileName, IFastProperties ifastConfig){
        if(originalFileName == null) {
            originalFileName =  "";
        } else {
            int unixSep = originalFileName.lastIndexOf("/");
            int winSep = originalFileName.lastIndexOf("\\");
            int pos = winSep > unixSep?winSep:unixSep;
            originalFileName= pos != -1?originalFileName.substring(pos + 1):originalFileName;
        }
        originalFileName = originalFileName.substring(0, originalFileName.indexOf(".")) + "-" + System.currentTimeMillis() + originalFileName.substring(originalFileName.indexOf("."));
        originalFileName = ifastConfig.getProjectName() + "/" + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_8)
                + "/" + originalFileName;
        return originalFileName;
    }
}
