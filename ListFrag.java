package com.shojib.asoftbd.adust;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Shopno-Shomu on 17-02-16.
 */

@SuppressLint({"NewApi"})
public class ListFrag extends Fragment {
    private int drawable;
    private TextView heading;
    private String heading_text;
    private ListView list;
    private String[] list_array;
    private RelativeLayout main_image;
    private ListItemAdapter name_adapter;
    private int position;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (this.position) {
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                this.list_array = getResources().getStringArray(R.array.about_string);
            case 3: /*FragmentManagerImpl.ANIM_STYLE_CLOSE_ENTER **/
                this.list_array = getResources().getStringArray(R.array.academics_string);
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                this.list_array = getResources().getStringArray(R.array.centres_string);
            case 5: /*FragmentManagerImpl.ANIM_STYLE_FADE_ENTER */
                this.list_array = getResources().getStringArray(R.array.affiliated_string);
            default:
        }
    }

    public ListFrag(int pos, int image, String heading) {
        this.drawable = image;
        this.position = pos;
        this.heading_text = heading;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listfraglayout, container, false);
        Log.w("hai", "ONCREATE View" + this.drawable);
        rootView.setTag(Integer.valueOf(this.position));
        this.list = (ListView) rootView.findViewById(R.id.listView1);
        this.main_image = (RelativeLayout) rootView.findViewById(R.id.mainimage);
        this.heading = (TextView) rootView.findViewById(R.id.heading);
        this.name_adapter = new ListItemAdapter(getActivity(), this.list_array);
        this.list.setAdapter(this.name_adapter);
        this.list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                String ss = ListFrag.this.list_array[arg2];
                String data = ((String) Constants.data.get(ss)).trim();
                Intent in = new Intent(ListFrag.this.getActivity(), WebViewActivity.class);
                in.putExtra("pos", Integer.parseInt(data));
                in.putExtra("title", ss);
                ListFrag.this.startActivity(in);
                Log.d("hai", data);
            }
        });
        this.main_image.setBackgroundDrawable(getActivity().getResources().getDrawable(this.drawable));
        this.heading.setText(this.heading_text);
        getActivity().getActionBar().setTitle(this.heading_text);
        return rootView;
    }
}