package com.traffic.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dengyuan on 2017/1/16.
 */

public class ToastUtil {
    public static void ToastLong(Context context,String mag){
        Toast.makeText(context,mag,Toast.LENGTH_LONG).show();
    }
    public static void ToastShort(Context context,String mag){
        Toast.makeText(context,mag,Toast.LENGTH_SHORT).show();
    }
}
