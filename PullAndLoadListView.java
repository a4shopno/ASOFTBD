package com.shojib.asoftbd.adust;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Shopno_Shumo on 30-03-16.
 */
public class PullAndLoadListView extends PullToRefreshListView {

    private RelativeLayout mFooterView;

    private boolean mIsLoadingMore;

    private OnLoadMoreListener mOnLoadMoreListener;

    private ProgressBar mProgressBarLoadMore;


    public interface OnLoadMoreListener {

        void onLoadMore();

    }


    public PullAndLoadListView(Context context, AttributeSet attrs) {

        super(context, attrs);

        this.mIsLoadingMore = false;

        initComponent(context);

    }


    public PullAndLoadListView(Context context) {

        super(context);

        this.mIsLoadingMore = false;

        initComponent(context);

    }


    public PullAndLoadListView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        this.mIsLoadingMore = false;

        initComponent(context);

    }


    public void initComponent(Context context) {

        this.mFooterView = (RelativeLayout) this.mInflater.inflate(R.layout.load_more_footer, this, false);

        this.mProgressBarLoadMore = (ProgressBar) this.mFooterView.findViewById(R.id.load_more_progressBar);

        addFooterView(this.mFooterView);

    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {

        this.mOnLoadMoreListener = onLoadMoreListener;

    }


    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

        if (this.mOnLoadMoreListener == null) {

            return;

        }

        if (visibleItemCount == totalItemCount) {

            this.mProgressBarLoadMore.setVisibility(GONE);

            return;

        }

        boolean loadMore;

        if (firstVisibleItem + visibleItemCount >= totalItemCount) {

            loadMore = true;

        } else {

            loadMore = false;

        }

        if (!this.mIsLoadingMore && loadMore && this.mRefreshState != 4 && this.mCurrentScrollState != 0) {

            this.mProgressBarLoadMore.setVisibility(VISIBLE);

            this.mIsLoadingMore = true;

            onLoadMore();

        }

    }


    public void onLoadMore() {

        if (this.mOnLoadMoreListener != null) {

            this.mOnLoadMoreListener.onLoadMore();

        }

    }


    public void onLoadMoreComplete() {

        this.mIsLoadingMore = false;

    }
}

