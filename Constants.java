package com.shojib.asoftbd.adust;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shopno-Shomu on 17-02-16.
 */
public class Constants extends Application{
    public static String BaseURL;
    public static ArrayList<Notifications> arr;
    public static HashMap<String, String> data=null;
    public static Integer[] pics;
    public static String url;

    static {
        BaseURL= "http://distance.mgu.ac.in/pro/";
        url = "index.php/welcome/newsjson";
        arr= new ArrayList();
        Integer[] arrayOfInteger= new Integer[6];
        arrayOfInteger[0]=Integer.valueOf(R.drawable.img_1);
        arrayOfInteger[1]=Integer.valueOf(R.drawable.img_3);
        arrayOfInteger[2]=Integer.valueOf(R.drawable.img_4);
        arrayOfInteger[3]=Integer.valueOf(R.drawable.img_5);
        arrayOfInteger[4]=Integer.valueOf(R.drawable.img_6);
        arrayOfInteger[5]=Integer.valueOf(R.drawable.img_7);
        pics=arrayOfInteger;
    }

    public static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
    {
        int i= paramOptions.outHeight;
        int j=paramOptions.outWidth;
        int k=1;
        int n;

        if((i > paramInt2) || (j > paramInt1)){
            int m = Math.round(i/paramInt2);
            n = Math.round(j/paramInt1);
            if(m<n){
                k=m;
            }

            }
        else {
            return k;
        }
      return n;

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId,options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public static boolean isInternetAvailable(Context paramContext)
    {
        String str = Constants.class.getSimpleName();
        NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(localNetworkInfo == null)
        {
            Log.d(str, "No internet connection");
            Toast.makeText(paramContext,"No internet connection", Toast.LENGTH_SHORT).show();
           return false;
        }
        if(localNetworkInfo.isConnected())
        {
            Log.d(str, "internet connection available....");
            return true;
        }
        Log.d(str, "internet connection");
        return true;
    }

}
