package com.hn.chat.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：常用工具类
 * 创建人：Administrator
 * 创建时间：2017/6/28 13:10
 * 修改人：Administrator
 * 修改时间：2017/6/28 13:10
 * 修改备注：
 * Version:  1.0.0
 */
public class Utils {

    /**
     * 手动隐藏键盘
     * @param context
     * @param view
     */
    public static void  hideSoftInputFromWindow(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

    }
    /**
     * 手动显示键盘
     * @param context
     * @param view
     */
    public static void  showSoftInputFromWindow(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);

    }

    /**
     * 清除焦点
     * @param context
     * @param etText
     */
    public static void  cleanFouce(Context context,EditText etText){
        //去除焦点，隐藏键盘
        Utils.hideSoftInputFromWindow(context,etText);
        etText.setVisibility(View.INVISIBLE);
        etText.setText("");
        etText.clearFocus();
    }

    public static void replaceAitForeground(String value, SpannableString mSpannableString) {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(mSpannableString)) {
            return;
        }
        Pattern pattern = Pattern.compile("(\\[有人@你\\])");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            int start = matcher.start();
            if (start != 0) {
                continue;
            }
            int end = matcher.end();
            mSpannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
    }

    /**
     * 获取当前时间毫秒值
     */
    public static long getNowDate() {
        Date  date=new Date();
        return  date.getTime();
    }
}
