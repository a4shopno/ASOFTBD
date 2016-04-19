package com.shojib.asoftbd.adust;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shopno-Shomu on 17-02-16.
 */

    public class HomeFragment extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.home_fragment, container, false);
            TextView tv = (TextView) rootView.findViewById(R.id.marquee);
            tv.setSelected(true);
            tv.setEllipsize(TruncateAt.MARQUEE);
            return rootView;
        }
    }
