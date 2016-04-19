package com.shojib.asoftbd.adust;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shopno_Shumo on 17-03-16.
 */
public class PhotoFragment2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        TouchImageView touchImageView = (TouchImageView) rootView.findViewById(R.id.ImageView_touch);
        touchImageView.setImageResource(Constants.pics[2].intValue());
        return rootView;
    }
}
