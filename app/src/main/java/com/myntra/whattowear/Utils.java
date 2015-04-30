package com.myntra.whattowear;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static String[] getColorFromWeatherCode(int code) {
        String[] colors = {"#212121","#424242"};
        if(code >= 200 && code <300 ){
            colors[0]="#FF6D00";
            colors[1]="#FF9100";
        }
        if(code >= 300 && code <600 ){
            colors[0]="#01579B";
            colors[1]="#0277BD";
        }
        if(code >= 600 && code <700 ){
            colors[0]="#18FFFF";
            colors[1]="#84FFFF";
        }
        if(code >= 700 && code <800 ){
            colors[0]="#424242";
            colors[1]="#616161";
        }
        if(code >= 800 && code <900 ){
            colors[0]="#0288D1";
            colors[1]="#039BE5";
        }
        if(code >= 900 && code <950 ){
            colors[0]="B71C1C";
            colors[1]="#EF5350";
        }
        if(code >= 950 && code <1000 ){
            colors[0]="#004D40";
            colors[1]="#009688";
        }
        return colors;
    }
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
    public static void counter(int count, final TextView view){
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, count);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round((endValue - startValue) * fraction);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static boolean random(){
        int a =  2 + (int)(Math.random() * (10));

        if((a%2)==0)
            return true;
        else
            return false;
        // odd
    }
}
