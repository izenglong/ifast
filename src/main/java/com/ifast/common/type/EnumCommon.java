package com.ifast.common.type;

/**
 * <pre>
 * 通用枚举类
 * <pre/>
 * @author Aron
 * @version v1.0
 * @Date 2019/5/7.
 */
public class EnumCommon {

    /**
     * <pre>
     * 用于表示【只有】 是/否 的场景
     * 例如:
     *   deleted 是否删除
     *   status  是否禁用
     *   enable  是否启用
     *   isUse  是否使用中
     *   isHidden 是否隐藏
     * </pre>
     */
    public enum YesOrNo{
        YES(1, "是"),
        NO(0, "否");

        private int value;
        private String desc;

        public int getValue() {
            return value;
        }
        public String getDesc() {
            return desc;
        }

        YesOrNo(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }



}
