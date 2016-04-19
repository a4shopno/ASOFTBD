package com.shojib.asoftbd.adust;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shopno-Shomu on 20-02-16.
 */
public class PhotoFragment7 extends android.support.v4.app.Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        TouchImageView touchImageView = (TouchImageView) rootView.findViewById(R.id.ImageView_touch);
        touchImageView.setImageResource(Constants.pics[5].intValue());
        return rootView;
    }
}
