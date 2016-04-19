package com.shojib.asoftbd.adust;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import com.shojib.asoftbd.adust.PullToRefreshBase.Mode;
import com.shojib.asoftbd.adust.PullToRefreshBase.OnLastItemVisibleListener;

public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements OnScrollListener {
    private View mEmptyView;
    private OnLastItemVisibleListener mOnLastItemVisibleListener;
    private OnScrollListener mOnScrollListener;
    private FrameLayout mRefreshableViewHolder;
    private int mSavedLastVisibleIndex;

    public abstract ContextMenuInfo getContextMenuInfo();

    public PullToRefreshAdapterViewBase(Context context) {
        super(context);
        this.mSavedLastVisibleIndex = -1;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode) {
        super(context, mode);
        this.mSavedLastVisibleIndex = -1;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSavedLastVisibleIndex = -1;
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mOnLastItemVisibleListener != null) {
            int lastVisibleItemIndex = firstVisibleItem + visibleItemCount;
            if (visibleItemCount > 0 && lastVisibleItemIndex + 1 == totalItemCount && lastVisibleItemIndex != this.mSavedLastVisibleIndex) {
                this.mSavedLastVisibleIndex = lastVisibleItemIndex;
                this.mOnLastItemVisibleListener.onLastItemVisible();
            }
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public final void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public final void setEmptyView(View newEmptyView) {
        if (this.mEmptyView != null) {
            this.mRefreshableViewHolder.removeView(this.mEmptyView);
        }
        if (newEmptyView != null) {
            newEmptyView.setClickable(true);
            ViewParent newEmptyViewParent = newEmptyView.getParent();
            if (newEmptyViewParent != null && (newEmptyViewParent instanceof ViewGroup)) {
                ((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
            }
            this.mRefreshableViewHolder.addView(newEmptyView, -1, -1);
            if (this.mRefreshableView instanceof EmptyViewMethodAccessor) {
                ((EmptyViewMethodAccessor) this.mRefreshableView).setEmptyViewInternal(newEmptyView);
            } else {
                ((AbsListView) this.mRefreshableView).setEmptyView(newEmptyView);
            }
        }
    }

    public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        this.mOnLastItemVisibleListener = listener;
    }

    public final void setOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    protected void addRefreshableView(Context context, T refreshableView) {
        this.mRefreshableViewHolder = new FrameLayout(context);
        this.mRefreshableViewHolder.addView(refreshableView, -1, -1);
        addView(this.mRefreshableViewHolder, new LayoutParams(-1, 0, 1.0f));
    }

    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    private boolean isFirstItemVisible() {
        if (((AbsListView) this.mRefreshableView).getCount() <= getNumberInternalViews()) {
            return true;
        }
        if (((AbsListView) this.mRefreshableView).getFirstVisiblePosition() == 0) {
            View firstVisibleChild = ((AbsListView) this.mRefreshableView).getChildAt(0);
            if (firstVisibleChild != null) {
                return firstVisibleChild.getTop() >= ((AbsListView) this.mRefreshableView).getTop();
            }
        }
        return false;
    }

    private boolean isLastItemVisible() {
        int count = ((AbsListView) this.mRefreshableView).getCount();
        int lastVisiblePosition = ((AbsListView) this.mRefreshableView).getLastVisiblePosition();
        if (count <= getNumberInternalViews()) {
            return true;
        }
        if (lastVisiblePosition == count - 1) {
            View lastVisibleChild = ((AbsListView) this.mRefreshableView).getChildAt(lastVisiblePosition - ((AbsListView) this.mRefreshableView).getFirstVisiblePosition());
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= ((AbsListView) this.mRefreshableView).getBottom();
            }
        }
        return false;
    }

    protected int getNumberInternalViews() {
        return getNumberInternalHeaderViews() + getNumberInternalFooterViews();
    }

    protected int getNumberInternalHeaderViews() {
        return 0;
    }

    protected int getNumberInternalFooterViews() {
        return 0;
    }
}
