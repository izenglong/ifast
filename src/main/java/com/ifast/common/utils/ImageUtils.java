package com.ifast.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author gaoyuzhe
 * @date 2017/12/18.
 */
public class ImageUtils {
    /***
     * 剪裁图片
     *
     * @param file
     *            图片
     * @param x
     *            起点横坐标
     * @param y
     *            纵坐标
     * @param w
     *            长
     * @param h
     *            高
     * @throws IOException
     * @date
     */
    public static BufferedImage cutImage(MultipartFile file, int x, int y, int w, int h, String prefix) {

        Iterator<?> iterator = ImageIO.getImageReadersByFormatName(prefix);
        try {
            ImageReader reader = (ImageReader) iterator.next();
            // 转换成输入流
            InputStream in = file.getInputStream();
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, w, h);
            param.setSourceRegion(rect);
            BufferedImage bufferedImage = reader.read(0, param);
            return bufferedImage;
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    /***
     * 图片旋转指定角度
     *
     * @param bufferedimage
     *            图像
     * @param degree
     *            角度
     */
    public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage bufferedImage;
        Graphics2D graphics2d;
        (graphics2d = (bufferedImage = new BufferedImage(width, height, type)).createGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.setPaint(Color.WHITE);
        graphics2d.fillRect(0, 0, width, height);
        graphics2d.rotate(Math.toRadians(degree), width / 2, height / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, Color.WHITE, null);
        graphics2d.dispose();
        return bufferedImage;
    }
}
