package com.shojib.asoftbd.adust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by Shopno_Shumo on 30-03-16.
 */
public class SplashActivity extends Activity {
    private Animation animFadein;

    private LinearLayout button_layout;

    private ImageView logo_view;


    /* renamed from: com.dewneot.mgu.SplashActivity.1 */

    class C00461 implements AnimationListener {

        C00461() {

        }


        public void onAnimationStart(Animation animation) {

        }


        public void onAnimationRepeat(Animation animation) {

        }


        public void onAnimationEnd(Animation animation) {

            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));

            SplashActivity.this.finish();

        }

    }


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(1);

        Constants.arr.clear();

        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);

        setContentView(R.layout.layout_fragment_splash_login_1);

        this.logo_view = (ImageView) findViewById(R.id.logo_image);

        this.animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        this.animFadein.setAnimationListener(new C00461());

        this.logo_view.startAnimation(this.animFadein);

    }
}

