package com.ifast.generator.type;

import lombok.Getter;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年5月30日 | Aron</small>
 */
public class EnumGen {

    @Getter
    public enum KvType {
        mapping(4400, "java与db字段映射关系")
        , base(4401, "生成注释基本配置");

        private int value;
        private String desc;

        KvType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
        public static String getMsgByCode(int value) {
            KvType[] values = KvType.values();
            for (KvType ec : values) {
                if (ec.value == value) {
                    return ec.desc;
                }
            }
            return "";
        }
    }

}
