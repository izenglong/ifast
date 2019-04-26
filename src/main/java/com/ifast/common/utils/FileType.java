package com.ifast.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件类型
 */
@Slf4j
public class FileType {
    public static int fileType(String fileName) {
        if (fileName == null) {
            log.info("fileName is 文件名为空！");
            return 500;

        } else {
            // 获取文件后缀名并转化为写，用于后续比较
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
            // 创建图片类型数组0
            String[] img = {"bmp", "jpg", "jpeg", "png", "fpx", "svg", "psd", "cdr", "pcd", "tiff", "gif", "pcx", "tga", "exif", "dxf", "ufo", "eps", "ai", "raw", "wmf"};
            for (int i = 0; i < img.length; i++) {
                if (img[i].equals(fileType)) {
                    return 0;
                }
            }

            // 创建文档类型数组1
            String[] document = {"txt", "htm", "html", "jsp", "rtf", "doc", "docx", "xls", "wpd", "pdf", "ppt"};
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(fileType)) {
                    return 1;
                }
            }
            // 创建视频类型数组2
            String[] video = {"mp4", "avi", "navi", "3gp", "mkv", "f4v", "mov", "wmv", "asf", "rmvb", "webm"};
            for (int i = 0; i < video.length; i++) {
                if (video[i].equals(fileType)) {
                    return 2;
                }
            }
            // 创建音乐类型数组3
            String[] music = {"mp3", "wma", "wav", "mod", "asf", "aac", "vqf", "ape", "mid", "ogg", "m4a", "ra", "cd", "md", "vqf"};
            for (int i = 0; i < music.length; i++) {
                if (music[i].equals(fileType)) {
                    return 3;
                }
            }

        }
        //4 其他
        return 99;
    }
}