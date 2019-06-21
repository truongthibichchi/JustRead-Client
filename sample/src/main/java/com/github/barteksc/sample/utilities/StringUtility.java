package com.github.barteksc.sample.utilities;

import com.github.barteksc.sample.constant.ConstString;

public class StringUtility {
    public static String replaceNull(String text) {
        if (text == null || text == "") {
            return ConstString.NA;
        }
        return text;
    }
}
