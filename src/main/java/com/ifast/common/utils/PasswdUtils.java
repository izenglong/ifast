package com.ifast.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * <pre>
 *
 * </pre>
 * <small> 2019-05-09 09:42 | Aron</small>
 */
@Slf4j
public abstract class PasswdUtils {

    public final static String ALGORITHM = Sha256Hash.ALGORITHM_NAME;

    private final static String DEFAULT_PASSWD = "123456";

    public static String get(String passwd, String salt) {
        if(StringUtils.isBlank(passwd)){
            log.info("密码为null，使用默认密码：{}", DEFAULT_PASSWD);
            passwd = DEFAULT_PASSWD;
        }
        return new Sha256Hash(passwd, hash(salt)).toString();
    }

    public static SimpleHash hash(String salt) {
        return new SimpleHash(ALGORITHM, salt);
    }
}
