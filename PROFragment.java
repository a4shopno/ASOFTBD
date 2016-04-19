package com.shojib.asoftbd.adust;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.shojib.asoftbd.adust.PullToRefreshBase.Mode;
import com.shojib.asoftbd.adust.PullToRefreshBase.OnRefreshListener2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class PROFragment extends Fragment {

    String Date;

    String Description;

    String Title;

    private ListView actualListView;

    int count;

    int end;

    List_proAdapter liproadapter;

    private PullToRefreshView mPullRefreshListView;

    ProgressBar progress;
    Fragment frag;

    int start;


    class C00501 implements OnClickListener {
        C00501() {
        }



        public void onClick(View v) {


            PROFragment.this.mPullRefreshListView.setVisibility(View.GONE);

            PROFragment.this.progress.setVisibility(View.VISIBLE);

            new loaddata().execute(new Integer[]{Integer.valueOf(0)});

        }
    }

    /* renamed from: com.dewneot.mgu.fragments.PROFragment.2 */


    class C00512 implements OnItemClickListener {
        C00512() {
        }



        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {

            Notifications not = (Notifications) Constants.arr.get(arg2 - 1);

            Log.d("hai", "click" + arg2 + "gfhg" + not.getTitle());

            DetailFragment fragment = new DetailFragment(not, PROFragment.this.splitDateString(not.getDate()));

            FragmentTransaction ft = PROFragment.this.getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, frag, "tag2");


            ft.addToBackStack(null);

            ft.commit();
        }
    }



    class loaddata extends AsyncTask<Integer, Void, String> {

        ProgressDialog pd;

        loaddata() {

            this.pd = null;
        }



        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(Integer... params) {

            String result = null;

            try {


                PROFragment.this.start = params[0].intValue() * 5;

                PROFragment.this.end = (params[0].intValue() + 1) * 5;

                result = HTTP.initiateRequest(Constants.BaseURL + Constants.url, "start=" + PROFragment.this.start + "&" + "end=" + PROFragment.this.end);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }



        protected void onPostExecute(String result) {

            if (result != null) {

                try {

                    JSONArray jnews = new JSONObject(result).getJSONArray("searchdata");
                    for (int i = 0; i < jnews.length(); i++) {


                        Constants.arr.add(new Notifications(jnews.getJSONObject(i).getString("id"),
                                jnews.getJSONObject(i).getString("title"), jnews.getJSONObject(i).getString("description"),
                                jnews.getJSONObject(i).getString("date")));

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }


            PROFragment.this.liproadapter = new List_proAdapter(PROFragment.this, Constants.arr);

            PROFragment.this.actualListView.setAdapter(PROFragment.this.liproadapter);

            PROFragment.this.mPullRefreshListView.setVisibility(View.VISIBLE);

            PROFragment.this.progress.setVisibility(View.GONE);

            PROFragment.this.mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /* renamed from: com.dewneot.mgu.fragments.PROFragment.3 */


    class C00643 implements OnRefreshListener2 {
        C00643() {
        }



        public void onPullDownToRefresh() {
        }


        public void onPullUpToRefresh() {


            if (Constants.isInternetAvailable(PROFragment.this.getActivity())) {


                PROFragment pROFragment = PROFragment.this;

                pROFragment.count++;

                new loaddata().execute(new Integer[]{Integer.valueOf(PROFragment.this.count)});

                PROFragment.this.mPullRefreshListView.onRefreshComplete();

            }
        }
    }



    public PROFragment() {
        this.start = 0;
        this.end = 0;
        this.count = 0;
    }






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pro_fragment, container, false);

        this.mPullRefreshListView = (PullToRefreshView) rootView.findViewById(R.id.listmain);

        View empty = rootView.findViewById(R.id.emptyView);

        Button btn_retry = (Button) empty.findViewById(R.id.btn_retry);

        this.progress = (ProgressBar) rootView.findViewById(R.id.progressBar1);

        btn_retry.setOnClickListener(new C00501());


        this.mPullRefreshListView.setEmptyView(empty);

        this.mPullRefreshListView.setVisibility(View.GONE);

        this.progress.setVisibility(View.VISIBLE);

        this.actualListView = (ListView) this.mPullRefreshListView.getRefreshableView();

        this.mPullRefreshListView.setMode(Mode.PULL_UP_TO_REFRESH);

        this.actualListView.setEmptyView(empty);

        this.actualListView.setOnItemClickListener(new C00512());


        if (Constants.arr.size() > 0) {


            this.liproadapter = new List_proAdapter(this, Constants.arr);

            this.actualListView.setAdapter(this.liproadapter);

            this.mPullRefreshListView.setVisibility(View.VISIBLE);

            this.progress.setVisibility(View.GONE);

            this.mPullRefreshListView.onRefreshComplete();

        } else if (Constants.isInternetAvailable(getActivity())) {

            new loaddata().execute(new Integer[]{Integer.valueOf(0)});

        } else {
            this.progress.setVisibility(View.GONE);

        }


        this.mPullRefreshListView.setOnRefreshListener(new C00643());
        return rootView;
    }



    public String splitDateString(String ss) {

        Date date = null;

        try {

            date = new SimpleDateFormat("yyyy-MM-dd").parse(ss);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return new SimpleDateFormat("yyyy-MMM-dd").format(date);
    }
}
