package com.shojib.asoftbd.adust;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Shopno-Shomu on 25-02-16.
 */
class GalleryFragment extends Fragment {
    private int columnWidth;
    Context con;
    GridView gridView;
    ImageView imageView;



    public class ImageAdapter extends BaseAdapter {
        private Context ctx;
        int imageBackground;

        class OnImageClickListener implements View.OnClickListener {
            int _postion;

            public OnImageClickListener(int position) {
                this._postion = position;
            }

            public void onClick(View v) {
                Intent i = new Intent(GalleryFragment.this.getActivity(), GalleryActivity.class);
                i.putExtra("position", this._postion);
                GalleryFragment.this.getActivity().startActivity(i);
            }
        }

        public ImageAdapter(Context c) {
            this.ctx = c;
            TypedArray ta = GalleryFragment.this.getActivity().obtainStyledAttributes(R.styleable.Gallery1);
            this.imageBackground = ta.getResourceId(0, 1);
            ta.recycle();
        }

        public int getCount() {
            return Constants.pics.length;
        }

        public Object getItem(int arg0) {
            return Integer.valueOf(arg0);
        }

        public long getItemId(int arg0) {
            return (long) arg0;
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(this.ctx);
            Bitmap image = Constants.decodeSampledBitmapFromResource(GalleryFragment.this.getResources(), Constants.pics[arg0].intValue(), 200, 200);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(GalleryFragment.this.columnWidth, GalleryFragment.this.columnWidth));
            iv.setImageBitmap(image);
            iv.setOnClickListener(new OnImageClickListener(arg0));
            return iv;
        }
    }

    public GalleryFragment(Context con) {
        this.con = con;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_layout, container, false);
        this.gridView = (GridView) rootView.findViewById(R.id.grid_view);
        InitilizeGridLayout();
        this.gridView.setAdapter(new ImageAdapter(getActivity()));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {

            }
        });
        return rootView;
    }

    private void InitilizeGridLayout() {
        float padding = TypedValue.applyDimension(1, 8.0f, getResources().getDisplayMetrics());
        this.columnWidth = (int) ((((float) getScreenWidth()) - (4.0f * padding)) / 3.0f);
        this.gridView.setNumColumns(3);
        this.gridView.setColumnWidth(this.columnWidth);
        this.gridView.setStretchMode(GridView.NO_STRETCH);
        this.gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        this.gridView.setHorizontalSpacing((int) padding);
        this.gridView.setVerticalSpacing((int) padding);
    }

    public int getScreenWidth() {
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError e) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point.x;
    }
}
