package com.shojib.asoftbd.adust;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shopno_Shumo on 17-03-16.
 */
public class DetailFragment extends Fragment {
    String date;
    String description;
    Notifications not;
    String title;

    public DetailFragment(Notifications not, String date) {
        this.not = not;
        this.date = date;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.detail_fragment, container, false);
        TextView title= (TextView) rootView.findViewById(R.id.list_textView_titledetail);
        TextView description= (TextView) rootView.findViewById(R.id.list_textView_description);
        TextView monthDetail= (TextView) rootView.findViewById(R.id.textView_month_detail);
        monthDetail.setText(this.date);
        title.setText(Translate.description(this.not.getTitle()));
        title.setTypeface(FontFactory.getmalluonlyjellybean(getActivity()));
        description.setText(Translate.description(this.not.getDescription()));
        description.setTypeface(FontFactory.getmalluonlyjellybean(getActivity()));
        return rootView;
    }
}
