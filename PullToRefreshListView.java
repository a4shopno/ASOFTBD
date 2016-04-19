package com.shojib.asoftbd.adust;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Shopno_Shumo on 30-03-16.
 */
public class PullToRefreshListView extends ListView implements OnScrollListener {

    private static final int PULL_TO_REFRESH = 2;

    protected static final int REFRESHING = 4;

    private static final int RELEASE_TO_REFRESH = 3;

    protected static final String TAG = "PullToRefreshListView";
    private static final int TAP_TO_REFRESH = 1;
    private boolean mBounceHack;
    protected int mCurrentScrollState;
    private RotateAnimation mFlipAnimation;
    protected LayoutInflater mInflater;
    private int mLastMotionY;
    private OnRefreshListener mOnRefreshListener;
    private OnScrollListener mOnScrollListener;
    private int mRefreshOriginalTopPadding;
    protected int mRefreshState;
    private RelativeLayout mRefreshView;
    private int mRefreshViewHeight;
    private ImageView mRefreshViewImage;
    private TextView mRefreshViewLastUpdated;
    private ProgressBar mRefreshViewProgress;
    private TextView mRefreshViewText;
    private RotateAnimation mReverseFlipAnimation;

    private class OnClickRefreshListener implements OnClickListener {
        private OnClickRefreshListener() {
        }

        public void onClick(View v) {
            if (PullToRefreshListView.this.mRefreshState != PullToRefreshListView.REFRESHING) {
                PullToRefreshListView.this.prepareForRefresh();
                PullToRefreshListView.this.onRefresh();
            }
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public PullToRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void init(Context context) {
        this.mFlipAnimation = new RotateAnimation(0.0f, -180.0f, TAP_TO_REFRESH, 0.5f, TAP_TO_REFRESH, 0.5f);
        this.mFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mFlipAnimation.setDuration(250);
        this.mFlipAnimation.setFillAfter(true);
        this.mReverseFlipAnimation = new RotateAnimation(-180.0f, 0.0f, TAP_TO_REFRESH, 0.5f, TAP_TO_REFRESH, 0.5f);
        this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mReverseFlipAnimation.setDuration(250);
        this.mReverseFlipAnimation.setFillAfter(true);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mRefreshView = (RelativeLayout) this.mInflater.inflate(R.layout.pull_to_refresh_header, this, false);
        this.mRefreshViewText = (TextView) this.mRefreshView.findViewById(R.id.pull_to_refresh_text);
        this.mRefreshViewImage = (ImageView) this.mRefreshView.findViewById(R.id.pull_to_refresh_image);
        this.mRefreshViewProgress = (ProgressBar) this.mRefreshView.findViewById(R.id.pull_to_refresh_progress);
        this.mRefreshViewLastUpdated = (TextView) this.mRefreshView.findViewById(R.id.pull_to_refresh_updated_at);
        this.mRefreshViewImage.setMinimumHeight(50);
        this.mRefreshView.setOnClickListener(new OnClickRefreshListener());
        this.mRefreshOriginalTopPadding = this.mRefreshView.getPaddingTop();
        this.mRefreshState = TAP_TO_REFRESH;
        addHeaderView(this.mRefreshView);
        super.setOnScrollListener(this);
        measureView(this.mRefreshView);
        this.mRefreshViewHeight = this.mRefreshView.getMeasuredHeight();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelection(TAP_TO_REFRESH);
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        setSelection(TAP_TO_REFRESH);
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            this.mRefreshViewLastUpdated.setVisibility(VISIBLE);
            this.mRefreshViewLastUpdated.setText(lastUpdated);
            return;
        }
        this.mRefreshViewLastUpdated.setVisibility(GONE);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        this.mBounceHack = false;
        switch (event.getAction()) {
            case DialogFragment.STYLE_NORMAL /*0*/:
                this.mLastMotionY = y;
                break;
            case TAP_TO_REFRESH /*1*/:
                if (!isVerticalScrollBarEnabled()) {
                    setVerticalScrollBarEnabled(true);
                }
                if (getFirstVisiblePosition() == 0 && this.mRefreshState != REFRESHING) {
                    if ((this.mRefreshView.getBottom() < this.mRefreshViewHeight && this.mRefreshView.getTop() < 0) || this.mRefreshState != RELEASE_TO_REFRESH) {
                        if (this.mRefreshView.getBottom() < this.mRefreshViewHeight || this.mRefreshView.getTop() <= 0) {
                            resetHeader();
                            setSelection(TAP_TO_REFRESH);
                            break;
                        }
                    }
                    this.mRefreshState = REFRESHING;
                    prepareForRefresh();
                    onRefresh();
                    break;
                }
                break;
            case PULL_TO_REFRESH /*2*/:
                applyHeaderPadding(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void applyHeaderPadding(MotionEvent ev) {
        int pointerCount = ev.getHistorySize();
        for (int p = 0; p < pointerCount; p += TAP_TO_REFRESH) {
            if (this.mRefreshState == RELEASE_TO_REFRESH) {
                if (isVerticalFadingEdgeEnabled()) {
                    setVerticalScrollBarEnabled(false);
                }
                this.mRefreshView.setPadding(this.mRefreshView.getPaddingLeft(), (int) (((double) ((((int) ev.getHistoricalY(p)) - this.mLastMotionY) - this.mRefreshViewHeight)) / 1.7d), this.mRefreshView.getPaddingRight(), this.mRefreshView.getPaddingBottom());
            }
        }
    }

    private void resetHeaderPadding() {
        this.mRefreshView.setPadding(this.mRefreshView.getPaddingLeft(), this.mRefreshOriginalTopPadding, this.mRefreshView.getPaddingRight(), this.mRefreshView.getPaddingBottom());
    }

    private void resetHeader() {
        if (this.mRefreshState != TAP_TO_REFRESH) {
            this.mRefreshState = TAP_TO_REFRESH;
            resetHeaderPadding();
            this.mRefreshViewText.setText(R.string.pull_to_refresh_tap_label);
            this.mRefreshViewImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
            this.mRefreshViewImage.clearAnimation();
            this.mRefreshViewImage.setVisibility(GONE);
            this.mRefreshViewProgress.setVisibility(GONE);
        }
    }

    private void measureView(View child) {
        int childHeightSpec;
        LayoutParams p = (LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.AT_MOST);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mCurrentScrollState != TAP_TO_REFRESH || this.mRefreshState == REFRESHING) {
            if (this.mCurrentScrollState == PULL_TO_REFRESH && firstVisibleItem == 0 && this.mRefreshState != REFRESHING) {
                setSelection(TAP_TO_REFRESH);
                this.mBounceHack = true;
            } else if (this.mBounceHack && this.mCurrentScrollState == PULL_TO_REFRESH) {
                setSelection(TAP_TO_REFRESH);
            }
        } else if (firstVisibleItem == 0) {
            this.mRefreshViewImage.setVisibility(VISIBLE);
            if ((this.mRefreshView.getBottom() >= this.mRefreshViewHeight + 20 || this.mRefreshView.getTop() >= 0) && this.mRefreshState != RELEASE_TO_REFRESH) {
                this.mRefreshViewText.setText(R.string.pull_to_refresh_release_label);
                this.mRefreshViewImage.clearAnimation();
                this.mRefreshViewImage.startAnimation(this.mFlipAnimation);
                this.mRefreshState = RELEASE_TO_REFRESH;
            } else if (this.mRefreshView.getBottom() < this.mRefreshViewHeight + 20 && this.mRefreshState != PULL_TO_REFRESH) {
                this.mRefreshViewText.setText(R.string.pull_to_refresh_pull_label);
                if (this.mRefreshState != TAP_TO_REFRESH) {
                    this.mRefreshViewImage.clearAnimation();
                    this.mRefreshViewImage.startAnimation(this.mReverseFlipAnimation);
                }
                this.mRefreshState = PULL_TO_REFRESH;
            }
        } else {
            this.mRefreshViewImage.setVisibility(GONE);
            resetHeader();
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.mCurrentScrollState = scrollState;
        if (this.mCurrentScrollState == 0) {
            this.mBounceHack = false;
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void prepareForRefresh() {
        resetHeaderPadding();
        this.mRefreshViewImage.setVisibility(GONE);
        this.mRefreshViewImage.setImageDrawable(null);
        this.mRefreshViewProgress.setVisibility(VISIBLE);
        this.mRefreshViewText.setText(R.string.pull_to_refresh_refreshing_label);
        this.mRefreshState = REFRESHING;
    }

    public void onRefresh() {
        if (this.mOnRefreshListener != null) {
            this.mOnRefreshListener.onRefresh();
        }
    }

    public void onRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);
        onRefreshComplete();
    }

    public void onRefreshComplete() {
        resetHeader();
        if (this.mRefreshView.getBottom() > 0) {
            invalidateViews();
            setSelection(TAP_TO_REFRESH);
        }
    }
}

