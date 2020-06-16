package com.andfast.app.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class StringUtils {

    private static final SimpleDateFormat FORMAT_DATE_SECOND_CHINESE = new SimpleDateFormat("yyyy年MM月dd HH时mm分ss秒");

    public static String getChineseFormatTimeString(long time) {
        Date date = new Date(time);
        synchronized (FORMAT_DATE_SECOND_CHINESE) {
            return FORMAT_DATE_SECOND_CHINESE.format(date);
        }
    }

    /**
     * 是否是空串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean b = false;
        if (str == null || str.trim().length() == 0) {
            b = true;
        }
        return b;
    }

    /**
     * 是否是空，包括null字符串也是空
     *
     * @param string 字符串
     * @return true : 空字符串
     */
    public static boolean isNull(String string) {
        return isEmpty(string) || "null".equals(string);
    }

    /**
     * 从字符串中提取所有的数字
     */
    public static String getNumFromStr(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static int getCharLength(char c) {
        int count = 0;
        if (isCharacter(c)) {
            count = 1;
        } else {
            count = 2;
        }
        return count;
    }

    public static boolean isCharacter(char c) {
        char temp = c;
        if ((temp >= 'a' && temp <= 'z') || (temp >= 'A' && temp <= 'Z')
                || (temp >= '0' && temp <= '9') || temp == ' ') {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 中文算2个英文算1个
     */
    public static int getTextLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        char temp = ' ';
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if (isCharacter(temp)) {

                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    /**
     * 中文算2个英文算1个,Emoji算2个
     */
    public static int getTextLengthWithEmoji(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        char temp = ' ';
        int codePointCount = str.codePointCount(0, str.length());
        for (int i = 1; i <= codePointCount; i++) {
            int beforeOffest = str.offsetByCodePoints(0, i - 1);
            int offest = str.offsetByCodePoints(0, i);
            String s = str.substring(beforeOffest, offest);
            if (s.length() >= 2) {
                count += 2;
            } else {
                count += getTextLength(s);
            }
        }
        return count;
    }

    /**
     * 中文算1个英文算1个,Emoji算1个
     */
    public static int getTextLengthAllOne(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        int codePointCount = str.codePointCount(0, str.length());
        for (int i = 1; i <= codePointCount; i++) {
            int beforeOffest = str.offsetByCodePoints(0, i - 1);
            int offest = str.offsetByCodePoints(0, i);
            String s = str.substring(beforeOffest, offest);
            if (s.length() >= 2) {
                count += 1;
            } else {
                count += 1;
            }
        }
        return count;
    }

    /**
     * 截取字符按照长度，其余字符长度都算1
     */
    public static String subStringWithAllOne(String string, int length) {
        int count = string.codePointCount(0, string.length());
        String newString = string;
        for (int i = 1; i <= count; i++) {
            int offest = string.offsetByCodePoints(0, i);
            String s = string.substring(0, offest);
            if (getTextLengthAllOne(s) <= length) {
                newString = s;
            } else {
                break;
            }
        }
        return newString;
    }


    /**
     * 中文算2个英文算1个(去掉末尾的空格)
     */
    public static int getTrimmedTextLength(EditText text) {
        if (text == null) {
            return 0;
        }
        Editable editable = text.getText();
        if (editable == null) {
            return 0;
        }
        String str = editable.toString();
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String s = str.trim();
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        int count = 0;
        char temp = ' ';
        for (int i = 0; i < s.length(); i++) {
            temp = s.charAt(i);
            if (isCharacter(temp)) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    /**
     * 如果首尾字母包含空格返回false否则true
     */
    public static boolean checkText(EditText editText) {
        String text = editText.getText().toString();
        int oldLength = text.length();
        text = text.trim();
        int newLength = text.length();
        if (newLength < oldLength) {
            editText.setText(text);
            editText.setSelection(newLength);
            return false;
        }
        return true;
    }

    public static String getFormatString(int num) {
        if (num >= 100000000) {
            return num / 100000000 + "亿+";
        } else if (num >= 10000) {
            return num / 10000 + "万+";
        }
        return "" + num;
    }

    /**
     * 字符串截取，超过最大长度变成...
     */
    public static String interceptString(String str, int maxLength) {
        String subStr = "";
        if (StringUtils.isEmpty(str)) {
            return subStr;
        }
        int length = getTextLength(str);
        if (length > maxLength) {
            subStr = subString(str, 0, maxLength - 2) + "...";
        } else {
            subStr = str;
        }
        return subStr;

    }

    /**
     * 字符串截取
     */
    public static String interceptStringWithNoEndSign(String str, int maxLength) {
        String subStr = "";
        if (StringUtils.isEmpty(str)) {
            return subStr;
        }
        int length = getTextLength(str);
        if (length > maxLength) {
            subStr = subString(str, 0, maxLength);
        } else {
            subStr = str;
        }
        return subStr;

    }

    public static String subString(String src, int start, int end) {
        StringBuilder des = new StringBuilder();

        if (TextUtils.isEmpty(src) || start > end) {
            return des.toString();
        }
        if (start >= 0 && end >= 0) {
            int count = 0;
            char temp = ' ';
            for (int i = 0; i < src.length(); i++) {
                temp = src.charAt(i);
                if (count >= end) {
                    if (count == end) {
                        return des.toString();
                    } else {
                        return des.deleteCharAt(des.length() - 1).toString();
                    }
                }
                if (count >= start) {
                    des.append(temp);
                }
                if (isCharacter(temp)) {

                    count += 1;
                } else {
                    count += 2;
                }

            }
        }
        return des.toString();
    }

    /**
     * 按最大长度截取字符串，汉字和英文大写算2个，英文小写和数字算1个
     */
    public static String subString(String src, int maxLength) {
        StringBuilder des = new StringBuilder();

        if (TextUtils.isEmpty(src)) {
            return des.toString();
        }
        if (maxLength > 0) {
            int count = 0;
            char temp = ' ';
            for (int i = 0; i < src.length(); i++) {
                temp = src.charAt(i);
                if (count >= maxLength) {
                    if (count == maxLength) {
                        return des.toString();
                    } else {
                        return des.deleteCharAt(des.length() - 1).toString();
                    }
                }
                if (count >= 0) {
                    des.append(temp);
                }
                if (isCharacterExceptCapital(temp)) {

                    count += 1;
                } else {
                    count += 2;
                }

            }
        }
        return des.toString();
    }

    public static boolean endwithChar(String src, int start, int end) {

        boolean isEndwithChar = false;
        if (TextUtils.isEmpty(src) || start > end) {
            return false;
        }
        if (start >= 0 && end >= 0) {
            int count = 0;
            char temp = ' ';
            for (int i = 0; i < src.length(); i++) {
                temp = src.charAt(i);
                if (count >= end) {
                    return isEndwithChar;
                }
                if (isCharacter(temp)) {
                    count += 1;
                    isEndwithChar = true;
                } else {
                    count += 2;
                    isEndwithChar = false;
                }

            }
        }
        return false;
    }

    /**
     * 判断是否为英文小写或数字或空格
     */
    public static boolean isCharacterExceptCapital(char c) {
        char temp = c;
        if ((temp >= 'a' && temp <= 'z') || (temp >= '0' && temp <= '9') || temp == ' ') {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 截取字符串，并防止截断特殊字符串（如emoji表情）
     */
    public static String subStringWithEmoji(String string, int length) {
        int count = string.codePointCount(0, string.length());
        String newString = string;
        for (int i = 1; i <= count; i++) {
            int offest = string.offsetByCodePoints(0, i);
            String s = string.substring(0, offest);
            if (getTextLengthWithEmoji(s) <= length) {
                newString = s;
            } else {
                break;
            }
        }
        return newString;
    }

    /**
     * 获取URLencode编码
     */
    public static String getUrlEncode(String s) {
        if (s == null) {
            return null;
        }
        String result = "";
        try {
            result = URLEncoder.encode(s, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 视频时长的展示
     *
     * @param timeMs 单位是毫秒
     */
    public static String stringForVideoTime(int timeMs) {
        if (timeMs < 1000) {
            timeMs = 1000;
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 视频时长的展示
     *
     * @param timeMs 单位是毫秒
     */
    public static String stringForVideoTimeSeconds(int timeMs) {
        if (timeMs < 1000) {
            timeMs = 1000;
        }
        int totalSeconds = timeMs / 1000;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        return mFormatter.format("%ds", totalSeconds).toString();
    }


    /**
     * 当超过这个字符数时，做裁剪操作，并结尾补齐...
     */
    public static String getSubStringWhenSurpassLength(String str, int length) {
        String result = "";
        if (StringUtils.isEmpty(str) || length <= 0) {
            return result;
        }
        if (str.length() > length) {
            result = str.substring(0, length);
            result += "...";
        } else {
            result = str;
        }

        return result;
    }

    /**
     * 判断字符串中是否含有emoji表情
     */
    public static boolean isContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 获取字符串的宽度
     *
     * @param str 字符串
     * @param textSize 字体大小
     * @return 字符串宽度
     */
    public static int getStringWidth(String str, float textSize) {
        if (isEmpty(str)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }


    /***
     * 将字符串中的中文转为URL编码
     *
     * @param str
     * @return
     */
    public static String encodeUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        String tstr = "";
        try {
            tstr = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            tstr = str;
        }

        return tstr;
    }
}
