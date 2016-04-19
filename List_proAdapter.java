package com.shojib.asoftbd.adust;

import android.app.Notification;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shojib.asoftbd.adust.FontFactory;
import com.shojib.asoftbd.adust.Translate;
import com.shojib.asoftbd.adust.PROFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shopno-Shomu on 20-02-16.
 */
public class List_proAdapter extends BaseAdapter {
    public ArrayList<Notifications> contents;
    private String description;
    PROFragment f2f;
    public LayoutInflater inflate;
    private String title;

    static class ViewHolder {
        public TextView date;
        public TextView month;
        public TextView title;

        ViewHolder() {
        }
    }

    public List_proAdapter(PROFragment proFragment, ArrayList<Notifications> contents) {
        this.contents = contents;
        this.f2f = proFragment;
        this.inflate = LayoutInflater.from(this.f2f.getActivity());
    }

    public int getCount() {
        return this.contents.size();
    }

    public Notifications getItem(int position) {
        return (Notifications) this.contents.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflate.inflate(R.layout.listview_li_ad, null);
            holder.title = (TextView) convertView.findViewById(R.id.list_textView_title);
            holder.month = (TextView) convertView.findViewById(R.id.textView_month);
            holder.date = (TextView) convertView.findViewById(R.id.textView_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(Translate.description(getItem(position).getTitle()));
        holder.title.setTypeface(FontFactory.getmalluonlyjellybean(this.f2f.getActivity()));
        String[] splitted = splitDate(getItem(position).getDate());
        holder.date.setText(splitted[2]);
        holder.month.setText(splitted[1]);
        return convertView;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public String[] splitDate(String ss) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(ss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MMM-dd").format(date).split("-");
    }
}