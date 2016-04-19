package com.shojib.asoftbd.adust;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * Created by Shopno-Shomu on 18-02-16.
 */
public class GalleryActivity extends FragmentActivity {
    private ViewpageAdapter adapter;
    private int columnWidth;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.gallery_activity);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        int position = getIntent().getIntExtra("position", 0);
        this.adapter = new ViewpageAdapter(getSupportFragmentManager(), this);
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(position);
    }
}
