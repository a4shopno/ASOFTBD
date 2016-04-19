package com.shojib.asoftbd.adust;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION;

/**
 * Created by Shopno-Shomu on 20-02-16.
 */

public class FontFactory {
    private static Typeface t1;
    private static Typeface t2;
    private static Typeface t3;

    public static Typeface getMallu(Context c) {
        if (t1 == null) {
            t1 = Typeface.createFromAsset(c.getAssets(), "font/karthika.TTF");
        }
        return t1;
    }

    public static Typeface getmalluonlyjellybean(Context c) {
        if (t2 == null) {
            if (VERSION.SDK_INT >= 16) {
                System.out.println("haijellybananjali");
                t2 = Typeface.createFromAsset(c.getAssets(), "font/AnjaliOldLipi.ttf");
            } else {
                System.out.println("haikarthika");
                t2 = Typeface.createFromAsset(c.getAssets(), "font/karthika.TTF");
            }
        }
        return t2;
    }

    public static String vivaram() {
        if (VERSION.SDK_INT >= 16) {
            return "\u0d35\u0d3f\u0d35\u0d30\u0d02 \u0d32\u0d2d\u0d4d\u0d2f\u0d2e\u0d32\u0d4d\u0d32";
        }
        return "hnhcw e`ya\u00c3";
    }
}
