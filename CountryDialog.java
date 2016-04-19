package com.shojib.asoftbd.adust;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shopno_Shumo on 30-03-16.
 */
public class CountryDialog extends Dialog {

    ImageView im;

    TextView tv;


    public CountryDialog(Context context) {

        super(context);

        requestWindowFeature(1);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        setContentView(R.layout.dialog);
    }
}

