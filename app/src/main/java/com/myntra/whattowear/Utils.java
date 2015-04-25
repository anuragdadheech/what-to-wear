package com.myntra.whatToWear;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 8302 on 4/13/2015.
 */
public class Utils {
    public static String getDayName(Context context, Date today) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        String dayString = dayFormat.format(today);
        return dayString;
    }
}
