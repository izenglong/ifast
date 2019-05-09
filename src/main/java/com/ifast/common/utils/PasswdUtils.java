package com.ifast.common.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * <pre>
 *
 * </pre>
 * <small> 2019-05-09 09:42 | Aron</small>
 */
public abstract class PasswdUtils {

    public final static String ALGORITHM = Sha256Hash.ALGORITHM_NAME;

    public static String get(String passwd, String salt) {

        return new Sha256Hash(passwd, hash(salt)).toString();
    }

    public static SimpleHash hash(String salt) {
        return new SimpleHash(ALGORITHM, salt);
    }
}
