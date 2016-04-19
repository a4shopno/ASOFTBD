package com.shojib.asoftbd.adust;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Shopno-Shomu on 17-02-16.
 */
public class ListItemAdapter extends BaseAdapter {
    Activity activity;
    public String[] data;
    private LayoutInflater inflator;
    private int lastPosition;
    private String team_name;

    public ListItemAdapter(Activity activity, String[] string) {
        this.team_name = null;
        this.lastPosition = -1;
        this.data = string;
        this.activity = activity;
        this.inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.data.length;
    }

    public Object getItem(int arg0) {
        return Integer.valueOf(arg0);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = this.inflator.inflate(R.layout.list_item, null);
        }
        vi.setTag(Integer.valueOf(position));
        ((TextView) vi.findViewById(R.id.team_name)).setText(this.data[position]);
        vi.startAnimation(AnimationUtils.loadAnimation(this.activity, position > this.lastPosition ? R.anim.up_from_bottom : R.anim.down_from_top));
        this.lastPosition = position;
        return vi;
    }
}
