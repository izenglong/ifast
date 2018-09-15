package com.ifast.common.component.oss.support.local;

import com.ifast.common.component.oss.support.UploadServer;
import com.ifast.common.utils.DateUtils;
import com.ifast.common.utils.HttpContextUtils;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/9/11 15:50 | Aron</small>
 */
public class LocalUploadServer implements UploadServer {

    @Setter
    private LocalUploadProperties uploadProperties;

    public LocalUploadServer(LocalUploadProperties uploadProperties) {
        this.uploadProperties = uploadProperties;
    }

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        String localDir = getLocalDir();
        String userDir = getUserDir(fileName);
        String dir = localDir + userDir;
        ensureDirExsit(dir);
        String filePath = dir + File.separator + fileName;
        bytesToFile(uploadBytes, filePath);
        return uploadProperties.getRootURL() + "/" + userDir + File.separator + fileName;
    }

    private String getLocalDir() {
        String localPath = uploadProperties.getLocalPath();
        if (StringUtils.isBlank(localPath)) {
            localPath = HttpContextUtils.getHttpServletRequest().getServletContext().getRealPath("/") + "upload";
        }
        boolean endsWith = !localPath.endsWith(File.separator);
        if (endsWith) {
            localPath += File.separator;
        }
        return localPath;
    }

    private String getUserDir(String fileName) {
        String format = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_8);
        int hashCode = fileName.hashCode();
        String dir1 = Integer.toHexString(hashCode & 0XF);
        String dir2 = Integer.toHexString((hashCode >> 4) & 0XF);

        String path = format + File.separator + dir1 + File.separator + dir2;
        return path;
    }

    private void ensureDirExsit(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private File bytesToFile(byte[] bytes, final String filePath) {
        File file = new File(filePath);
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);
            bos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;

    }
}
