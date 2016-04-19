package com.shojib.asoftbd.adust;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.CursorAdapter;

/**
 * Created by Shopno-Shomu on 18-02-16.
 */
public class ViewpageAdapter extends FragmentStatePagerAdapter {

    Fragment fragment;

    FragmentManager manager;


    public ViewpageAdapter(FragmentManager fm, FragmentActivity fragmentActivity) {

        super(fm);

        this.manager = fm;

    }


    public Fragment getItem(int arg0) {

        switch (arg0) {

            case DialogFragment.STYLE_NORMAL /*0*/:

                this.fragment = new PhotoFragment();

                break;

            case CursorAdapter.FLAG_AUTO_REQUERY /*1*/:

                this.fragment = new PhotoFragment3();

                break;

            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:

                this.fragment = new PhotoFragment4();

                break;

            case 3/*FragmentManagerImpl.ANIM_STYLE_CLOSE_ENTER /*3*/:

                this.fragment = new PhotoFragment5();

                break;

            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:

                this.fragment = new PhotoFragment6();

                break;

            case 5/*FragmentManagerImpl.ANIM_STYLE_FADE_ENTER /*5*/:

                this.fragment = new PhotoFragment7();

                break;

            default:

                this.fragment = new PhotoFragment();

                break;

        }

        Bundle args = new Bundle();

        args.putInt("group", arg0 + 1);

        this.fragment.setArguments(args);

        return this.fragment;

    }


    public int getCount() {

        return 6;

    }
}
