package com.unis.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Description: 拼音工具类
 * @author: 杨祖榕
 * @Date: 2019-01-20 11:21
 * @Version v1.0
 **/
public class PinyinUtil {

    /**
     * 获取每个汉字的拼音首字母(小写)
     * @param chsLng
     * @return
     */
    public static String getEveryFirstPinyinOfWords(String chsLng) {
        //
        char[] wordChars = chsLng.trim().toCharArray();
        String hanyupinyin = "";
        //
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        //
        try {
            for (int i = 0; i < wordChars.length; i++) {
                String str = String.valueOf(wordChars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {//如果字符是中文,则将中文转为汉语拼音
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(wordChars[i], defaultFormat)[0].substring(0, 1);
                } else if (str.matches("[0-9]+")) {//如果字符是数字，取数字
                    hanyupinyin += wordChars[i];
                } else if (str.matches("[a-zA-Z]+")) {//如果字符是字母，取字母
                    hanyupinyin += wordChars[i];
                } else {//否则不转换

                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    public static void main(String[] args) {
        String chsLng = "多发dRRR的发独守空房阿道夫打发第三方";
        String result = getEveryFirstPinyinOfWords(chsLng);
        System.out.println(result);
    }

}
