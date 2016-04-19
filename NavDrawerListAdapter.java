package com.shojib.asoftbd.adust;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shopno-Shomu on 15-02-16.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public int getCount() {
        return this.navDrawerItems.size();
    }


    public Object getItem(int position)  {
        return this.navDrawerItems.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        ImageView imgView= (ImageView)convertView.findViewById(R.id.icon);

                imgView.setImageResource(((NavDrawerItem) this.navDrawerItems.get(position)).getIcon());

        txtTitle.setText(((NavDrawerItem) this.navDrawerItems.get(position)).getTitle());
        return convertView;

    }
}
