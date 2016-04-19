package com.shojib.asoftbd.adust;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;




/**
 * Created by Shopno-Shomu on 17-02-16.
 */

 public class WebViewActivity extends Activity {

    private static String title;

    int pos;

    ProgressBar progressbar;

    WebView wv;



    public class myWebClient extends WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }


        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }


        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

            WebViewActivity.this.progressbar.setVisibility(View.GONE);

        }

    }


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        this.wv = (WebView) findViewById(R.id.webView1);

        this.progressbar = (ProgressBar) findViewById(R.id.progressBar1);

        this.wv.setWebChromeClient(new WebChromeClient());

        this.wv.clearCache(true);

        this.wv.clearHistory();

        this.wv.setWebViewClient(new myWebClient());

        this.wv.getSettings().setJavaScriptEnabled(true);

        this.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        Intent mIntent = getIntent();

        this.pos = mIntent.getIntExtra("pos", 0);

        title = mIntent.getStringExtra("title");

        getActionBar().setTitle(title);

        loadfactsheet(this.pos);

    }


    public void loadfactsheet(int n) {

        switch (n) {

            case CursorAdapter.FLAG_AUTO_REQUERY /*1*/:

                this.wv.loadUrl("file:///android_asset/vision.html");

            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:

                this.wv.loadUrl("file:///android_asset/The_chancellor_&_officers_of_university.html");

            case /*FragmentManagerImpl.ANIM_STYLE_CLOSE_ENTER */ 3:

                this.wv.loadUrl("file:///android_asset/authorities_of_universities.html");

            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:

                this.wv.loadUrl("file:///android_asset/factsheet.html");

            case /*FragmentManagerImpl.ANIM_STYLE_FADE_ENTER */5:

                this.wv.loadUrl("file:///android_asset/schoolofbehaviourscience.html");

            case 6/*FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:

                this.wv.loadUrl("file:///android_asset/schoolofbioscience.html");

            case FragmentTransactionExtended.SLIDE_HORIZONTAL /*7*/:

                this.wv.loadUrl("file:///android_asset/schoolofchemicalsciences.html");

            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:

                this.wv.loadUrl("file:///android_asset/schoolofcomputersciences.html");

            case FragmentTransactionExtended.SLIDE_VERTICAL_PUSH_LEFT /*9*/:

                this.wv.loadUrl("file:///android_asset/schoolofdistanceeducation.html");

            case FragmentTransactionExtended.GLIDE /*10*/:

                this.wv.loadUrl("file:///android_asset/schoolofenvironmentalsciences.html");

            case FragmentTransactionExtended.SLIDING /*11*/:

                this.wv.loadUrl("file:///android_asset/schoolofgandhianthought.html");

            case FragmentTransactionExtended.STACK /*12*/:

                this.wv.loadUrl("file:///android_asset/schoolofindianlegalthought.html");
            case FragmentTransactionExtended.CUBE /*13*/:
                this.wv.loadUrl("file:///android_asset/internationalrelationsandpolitics.html");
            case FragmentTransactionExtended.ROTATE_DOWN /*14*/:
                this.wv.loadUrl("file:///android_asset/schoolofletters.html");
            case FragmentTransactionExtended.ROTATE_UP /*15*/:
                this.wv.loadUrl("file:///android_asset/pedagogicalsciences.html");
            case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                this.wv.loadUrl("file:///android_asset/physicaleducation.html");
            case FragmentTransactionExtended.TABLE_HORIZONTAL /*17*/:
                this.wv.loadUrl("file:///android_asset/SCHOOLOFPUREANDAPPLIEDPHYSICS.html");
            case FragmentTransactionExtended.TABLE_VERTICAL /*18*/:
                this.wv.loadUrl("file:///android_asset/SCHOOLOFMANAGEMENTANDBUSINESSSTUDIES.html");
            case FragmentTransactionExtended.ZOOM_FROM_LEFT_CORNER /*19*/:

                this.wv.loadUrl("file:///android_asset/schoolofsocialsciences.html");

            case FragmentTransactionExtended.ZOOM_FROM_RIGHT_CORNER /*20*/:

                this.wv.loadUrl("file:///android_asset/DEPARTMENTOFLIFELONGLEARNINGANDEXTENSION.html");

            case FragmentTransactionExtended.ZOOM_SLIDE_HORIZONTAL /*21*/:

                this.wv.loadUrl("file:///android_asset/centres.html");

            case FragmentTransactionExtended.ZOOM_SLIDE_VERTICAL /*22*/:

                this.wv.loadUrl("file:///android_asset/Chairs.html");

            case 23:

                this.wv.loadUrl("file:///android_asset/constituentcollege.html");

            case 24:

                this.wv.loadUrl("file:///android_asset/SelffinancingTeachingDepartments.html");

            case 25:

                this.wv.loadUrl("file:///android_asset/affiliatedkottayam.html");

            case 26:

                this.wv.loadUrl("file:///android_asset/affiliatedernakulam.html");

            case 27:

                this.wv.loadUrl("file:///android_asset/affiliatedpathanamthitta.html");

            case 28:

                this.wv.loadUrl("file:///android_asset/affiliatedidukki.html");

            case 29:

                this.wv.loadUrl("file:///android_asset/affiliatedalapuzha.html");

            case 30:

                this.wv.loadUrl("file:///android_asset/library.html");

            case 31:

                this.wv.loadUrl("file:///android_asset/contactus.html");

            default:
        }
    }
}

