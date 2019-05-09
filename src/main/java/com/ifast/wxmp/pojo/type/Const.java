package com.ifast.wxmp.pojo.type;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/11/6 23:11 | Aron</small>
 */
public class Const {


    public static class Subscribe {
        public final static int YES = 1;
        public final static int NO = 0;
    }

    public static class MenuKey {
        public final static String MAIN = "1";
        public final static String LINK = "2";
        public final static String TEXT = "3";
        public final static String KEY_WORD = "4";
        public final static String SCAN = "5";
        public final static String PIC = "6";
        public final static String LOCATION = "7";
    }

    public static class appType {
        /**
         * 订阅号
         */
        public final static Integer SUBSCRIPTION = 1;
        /**
         * 服务号
         */
        public final static Integer SERIVCE = 2;
        /**
         * 企业号
         */
        public final static Integer ENTERPRISE = 3;
        /**
         * 小程序
         */
        public final static Integer MINI_APP = 4;
        /**
         * 测试号
         */
        public final static Integer TEST = 5;

    }

}
