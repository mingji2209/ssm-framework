package com.jsoft.framework.ssm.util;

public  class CaseUtils {

    // 数字转成字符串后
    private static String int2String(int value){
        return String.valueOf( value );
    }

    // 字符串转成数字
    private static Integer string2Int(String value){
        return Integer.parseInt( value );
    }
}
