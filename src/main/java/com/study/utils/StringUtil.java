package com.study.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

public class StringUtil {

    /**
     * 去除空格回车与换行
     */
    public static String cleanParm(String param) {
        String reparam = param.replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");
        return reparam;
    }

    /**
     * 根据原始密码和salt 生成加密密码
     *
     * @param pass
     * @param salt
     */
    public static final String genPassWord(String pass, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(pass) + salt);
    }

    /**
     * 字符串左补0
     *
     * @param str       原字符串
     * @param strLength 待生成字符串长度
     * @return 如果原字符串长度大于待生成字符串长度则直接返回原字符串，否则在原字符串左边补0直到长度达到strLength
     */
    public static final String addZeroForString(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 生成随机字符
     *
     * @param numChars
     */
    public static String randomString(int numChars) {
        StringBuffer s = new StringBuffer("");
        char letters[] = initLetters();
        for (int i = 0; i < numChars; i++) {
            int d1 = (int) (Math.random() * 10D) % 2;
            if (d1 == 0) {
                int d2 = (int) (Math.random() * 100D) % 26;
                s = s.append(letters[d2]);
            } else if (d1 == 1)
                s = s.append((int) (Math.random() * 10D));
        }
        return s.toString();
    }

    private static char[] initLetters() {
        char ca[] = new char[26];
        for (int i = 0; i < 26; i++)
            ca[i] = (char) (65 + i);

        return ca;
    }

    /**
     * 判断字符串是否是json
     *
     * @param json
     */
    public static boolean testJson(String json) {
        boolean result = true;
        try {
            Object parse = JSON.parse(json);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

}
