package com.etc.hospital.util;



import java.util.regex.Pattern;

/**
 * 特殊字符过滤工具
 */
public class SpecialCharUtil {
    // 禁止特殊字符：!@#$%^&*()_+-=[]{}|;:,.<>?`~
    private static final Pattern SPECIAL_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?`~]");

    /**
     * 判断是否包含特殊字符
     */
    public static boolean hasSpecialChar(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return SPECIAL_PATTERN.matcher(str).find();
    }
}