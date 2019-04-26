package com.ifast.common.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/12/3 11:14 | Aron</small>
 */
public class ShortUrlUtils {

    private final static RestTemplate restTempate = new RestTemplate();

    private final static String WEIBO_API_SHORT_URL = "http://api.t.sina.com.cn/short_url/shorten.json?source=3271760578&";

    /**
     * <pre>
     * 转微博短连接
     * </pre>
     * <small> 2018/12/3 11:25 | Aron</small>
     *
     * @param urlList 原始链接数组
     * @return
     */
    public static List<UrlObject> convertShortUrl(List<String> urlList) {
        List<String> apiUrlList = new ArrayList<>();
        StringBuilder url_long = new StringBuilder();
        int size = urlList.size();
        for (int i = 0; i < size; i++) {
            String originUrl = urlList.get(i);
            if (0 == (i + 1) % 20) {
                url_long.append("&url_long=").append(originUrl);
                String apiUrl = WEIBO_API_SHORT_URL + url_long;
                apiUrlList.add(apiUrl);
                url_long.delete(0, url_long.length());

            } else {
                if (StringUtils.isBlank(url_long)) {
                    url_long.append("url_long=").append(originUrl);
                } else {
                    url_long.append("&url_long=").append(originUrl);
                }
                // 最后一个
                if (i == size - 1) {
                    String apiUrl = WEIBO_API_SHORT_URL + url_long;
                    apiUrlList.add(apiUrl);
                }
            }
        }

        List<UrlObject> result = new ArrayList<>();
        apiUrlList.forEach(url -> {
            List<UrlObject> urlObjects = JSONUtils.jsonToList(restTempate.getForObject(url, String.class), UrlObject.class);
            urlObjects.forEach(urlObject -> result.add(urlObject));

        });
        return result;
    }

    public static UrlObject convertShortUrl(String url) {
        return convertShortUrl(Arrays.asList(new String[]{url})).get(0);
    }


    public static void main(String[] args) {
        UrlObject urlObject = convertShortUrl("http://ifast.site");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(urlObject);
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - ");
    }
}

@Data
class UrlObject {
    private String url_short;
    private String url_long;
    private int type;
}
