package com.shojib.asoftbd.adust;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Created by Shopno-Shomu on 17-02-16.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    RelativeLayout about;
    RelativeLayout academics;
    RelativeLayout affiliated;
    RelativeLayout centres;
    RelativeLayout contact;
    Fragment fragment;
    private RelativeLayout gallery;
    RelativeLayout home;
    RelativeLayout library;
    RelativeLayout pro;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        this.home = (RelativeLayout) rootView.findViewById(R.id.home_rl);
        this.about = (RelativeLayout) rootView.findViewById(R.id.aboutus_rl);
        this.academics = (RelativeLayout) rootView.findViewById(R.id.academics_rl);
        this.centres = (RelativeLayout) rootView.findViewById(R.id.centres_rl);
        this.affiliated = (RelativeLayout) rootView.findViewById(R.id.affiliated_rl);
        this.library = (RelativeLayout) rootView.findViewById(R.id.library_rl);
        this.contact = (RelativeLayout) rootView.findViewById(R.id.contact_rl);
        this.pro = (RelativeLayout) rootView.findViewById(R.id.pro_rl);
        this.gallery = (RelativeLayout) rootView.findViewById(R.id.gallery_rl);
        this.about.setOnClickListener(this);
        this.academics.setOnClickListener(this);
        this.centres.setOnClickListener(this);
        this.affiliated.setOnClickListener(this);
        this.library.setOnClickListener(this);
        this.pro.setOnClickListener(this);
        this.contact.setOnClickListener(this);
        this.home.setOnClickListener(this);
        this.gallery.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft= getActivity().getFragmentManager().beginTransaction();
        switch (v.getId()){


            case R.id.home_rl:
                this.fragment = new HomeFragment();
                break;
            case R.id.aboutus_rl:
                this.fragment = new ListFrag(2, R.drawable.about_us,"About Us");
                break;
            case R.id.academics_rl:
                this.fragment = new ListFrag(3, R.drawable.acadamics_bg,"Academics");
                break;
            case R.id.centres_rl:
                this.fragment = new ListFrag(4, R.drawable.centres_bg, "Centers");
                break;
            case R.id.affiliated_rl:
                this.fragment = new ListFrag(5, R.drawable.affli_bg,"Affiliated");
                break;
            case R.id.library_rl:
                this.fragment =new WebviewFragment(30, "Library");
                break;
            case R.id.gallery_rl:
                this.fragment = new GalleryFragment(getActivity());
                break;
            case R.id.pro_rl:
                this.fragment= new PROFragment();
                break;
            case R.id.contact_rl:
                this.fragment = new WebviewFragment(31, "Contuct Us");
                break;
        }
        ((MainActivity) getActivity()).addTransition(this, this.fragment);
    }
}
