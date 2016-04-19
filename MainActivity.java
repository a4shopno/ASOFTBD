package com.shojib.asoftbd.adust;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import java.util.ArrayList;
import java.util.HashMap;



public class MainActivity extends Activity {

    Boolean FromDrawer;

    private NavDrawerListAdapter adapter;

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private CharSequence mDrawerTitle;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;

    private ArrayList<NavDrawerItem> navDrawerItems;

    private TypedArray navMenuIcons;

    private String[] navMenuTitles;


    private class SlideMenuClickListener implements OnItemClickListener {

        private SlideMenuClickListener() {

        }


        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            MainActivity.this.displayView(position);

        }

    }




    class C00651 extends ActionBarDrawerToggle {

        C00651(Activity $anonymous0, DrawerLayout $anonymous1, int $anonymous2, int $anonymous3, int $anonymous4) {

            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4);

        }


        public void onDrawerClosed(View view) {

            MainActivity.this.getActionBar().setTitle(MainActivity.this.mDrawerTitle);

            MainActivity.this.invalidateOptionsMenu();

        }


        public void onDrawerOpened(View drawerView) {

            MainActivity.this.getActionBar().setTitle(MainActivity.this.mDrawerTitle);

            MainActivity.this.invalidateOptionsMenu();

        }

    }


    public MainActivity() {

        this.FromDrawer = Boolean.valueOf(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        adddata();

        CharSequence title = getTitle();

        this.mDrawerTitle = title;

        this.mTitle = title;

        this.navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        this.navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        this.mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        this.navDrawerItems = new ArrayList();

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[0], this.navMenuIcons.getResourceId(0, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[1], this.navMenuIcons.getResourceId(1, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[2], this.navMenuIcons.getResourceId(2, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[3], this.navMenuIcons.getResourceId(3, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[4], this.navMenuIcons.getResourceId(4, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[5], this.navMenuIcons.getResourceId(5, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[6], this.navMenuIcons.getResourceId(6, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[7], this.navMenuIcons.getResourceId(7, -1)));

        this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[8], this.navMenuIcons.getResourceId(8, -1)));

        this.navMenuIcons.recycle();

        this.mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        this.adapter = new NavDrawerListAdapter(getApplicationContext(), this.navDrawerItems);

        this.mDrawerList.setAdapter(this.adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setHomeButtonEnabled(true);

        getActionBar().setDisplayShowTitleEnabled(true);

        this.mDrawerToggle = new C00651(this, this.mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);

        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);

        if (savedInstanceState == null) {

            displayView(0);

            this.FromDrawer = Boolean.valueOf(false);

        }

    }


    public void addTransition(Fragment mFirstFragment, Fragment replace_fragment) {

        if (getFragmentManager().getBackStackEntryCount() == 0) {

            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, getFragmentManager().beginTransaction(), mFirstFragment, replace_fragment, R.id.frame_container);

            fragmentTransactionExtended.addTransition(17);

            fragmentTransactionExtended.commit();

            return;

        }

        getFragmentManager().popBackStack();

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (this.mDrawerToggle.onOptionsItemSelected(item)) {

            return true;

        }

        switch (item.getItemId()) {

            case R.id.action_settings:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean drawerOpen = this.mDrawerLayout.isDrawerOpen(this.mDrawerList);

        return super.onPrepareOptionsMenu(menu);

    }


    private void displayView(int position) {

        Fragment fragment = null;

        this.FromDrawer = Boolean.valueOf(true);

        switch (position) {

            case DialogFragment.STYLE_NORMAL /*0*/:

                fragment = new MainFragment();

                break;

            case CursorAdapter.FLAG_AUTO_REQUERY /*1*/:

                fragment = new ListFrag(2, R.drawable.about_us, "About Us");

                break;

            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:

                fragment = new ListFrag(3, R.drawable.acadamics_bg, "Academics");

                break;

            case 3 /*FragmentManagerImpl.ANIM_STYLE_CLOSE_ENTER /*3*/:

                fragment = new ListFrag(4, R.drawable.centres_bg, "Centres");

                break;

            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:

                fragment = new ListFrag(5, R.drawable.affli_bg, "Affiliated Colleges");

                break;

            case 5 /*FragmentManagerImpl.ANIM_STYLE_FADE_ENTER /*5*/:

                fragment = new WebviewFragment(30, "Library");

                break;

            case 6 /*FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:

                fragment = new GalleryFragment(this);

                break;

            case FragmentTransactionExtended.SLIDE_HORIZONTAL /*7*/:

                fragment = new PROFragment();

                break;

            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:

                fragment = new WebviewFragment(31, "Contact Us");

                break;

        }

        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();

            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {

                fragmentManager.popBackStack();

            }

            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            this.mDrawerList.setItemChecked(position, true);

            this.mDrawerList.setSelection(position);

            setTitle(this.navMenuTitles[position]);

            this.mDrawerLayout.closeDrawer(this.mDrawerList);

            return;

        }

        Log.e("MainActivity", "Error in creating fragment");

    }


    public void setTitle(CharSequence title) {

        this.mTitle = title;

        getActionBar().setTitle(this.mTitle);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();

    }


    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        this.mDrawerToggle.onConfigurationChanged(newConfig);

    }


    public void adddata() {

        Constants.data = new HashMap();

        Constants.data.put("Vision", "1");

        Constants.data.put("The Chancellor and Officers of University", "2");

        Constants.data.put("Authorities of University", "3");

        Constants.data.put("Factsheet", "4");

        Constants.data.put("School of Behavioural Sciences", "5");

        Constants.data.put("School of Biosciences", "6");

        Constants.data.put("School of Chemical Sciences", "7");

        Constants.data.put("School of Computer Science", "8");

        Constants.data.put("School of Distance Education", "9");

        Constants.data.put("School of Environmental Sciences", "10");

        Constants.data.put("School of Gandhian Thought and Development Studies", "11");

        Constants.data.put("School of Indian Legal Thought", "12");

        Constants.data.put("School of International Relations and Politics", "13");

        Constants.data.put("School of Letters", "14");

        Constants.data.put("School of Pedagogical Sciences", "15");

        Constants.data.put("School of Physical Education and Sports Sciences", "16");

        Constants.data.put("School of Pure and Applied Physics", "17");

        Constants.data.put("School of Management and Business Studies", "18");

        Constants.data.put("School of Social Sciences", "19");

        Constants.data.put("Department of Life Long Learning and Extension", "20");

        Constants.data.put("Centres", "21");

        Constants.data.put("Chairs", "22");

        Constants.data.put("Constituent Colleges", "23");

        Constants.data.put("Self Financing Teaching Departments", "24");

        Constants.data.put("Kottayam", "25");

        Constants.data.put("Ernakulam", "26");

        Constants.data.put("Pathanamthitta", "27");

        Constants.data.put("Idukki", "28");

        Constants.data.put("Alappuzha", "29");

        Constants.data.put("Library", "30");

        Constants.data.put("Conact Us", "31");

    }


    public void onBackPressed() {

        if (this.FromDrawer.booleanValue()) {

            if (getFragmentManager().findFragmentById(R.id.frame_container) instanceof MainFragment) {

                super.onBackPressed();

            } else {

                MainFragment fragment = new MainFragment();

                FragmentManager fragmentManager = getFragmentManager();

                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {

                    fragmentManager.popBackStack();

                }

                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            }

            this.FromDrawer = Boolean.valueOf(false);

            return;

        }

        super.onBackPressed();

    }
}
