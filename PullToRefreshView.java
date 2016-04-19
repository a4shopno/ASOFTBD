package com.shojib.asoftbd.adust;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.widget.CursorAdapter;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.shojib.asoftbd.adust.R;
import com.shojib.asoftbd.adust.PullToRefreshBase.Mode;

public class PullToRefreshView extends PullToRefreshAdapterViewBase<ListView> {
    private static /* synthetic */ int[] f5x936774ff;
    private LoadLayout mFooterLoadingView;
    private LoadLayout mHeaderLoadingView;
    private FrameLayout mLvFooterLoadingFrame;

    class InternalListView extends ListView implements EmptyViewMethodAccessor {
        private boolean mAddedLvFooter;

        public InternalListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mAddedLvFooter = false;
        }

        public void setAdapter(ListAdapter adapter) {
            if (!this.mAddedLvFooter) {
                addFooterView(PullToRefreshView.this.mLvFooterLoadingFrame, null, false);
                this.mAddedLvFooter = true;
            }
            super.setAdapter(adapter);
        }

        public void setEmptyView(View emptyView) {
            PullToRefreshView.this.setEmptyView(emptyView);
        }

        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }

        public ContextMenuInfo getContextMenuInfo() {
            return super.getContextMenuInfo();
        }

        public void draw(Canvas canvas) {
            try {
                super.draw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static /* synthetic */ int[] m2x936774ff() {
        int[] iArr = f5x936774ff;
        if (iArr == null) {
            iArr = new int[Mode.values().length];
            try {
                iArr[Mode.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Mode.PULL_DOWN_TO_REFRESH.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Mode.PULL_UP_TO_REFRESH.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            f5x936774ff = iArr;
        }
        return iArr;
    }

    public PullToRefreshView(Context context) {
        super(context);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshView(Context context, Mode mode) {
        super(context, mode);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDisableScrollingWhileRefreshing(false);
    }

    public ContextMenuInfo getContextMenuInfo() {
        return ((InternalListView) getRefreshableView()).getContextMenuInfo();
    }

    public void setReleaseLabel(String releaseLabel, Mode mode) {
        super.setReleaseLabel(releaseLabel, mode);
        if (this.mHeaderLoadingView != null && mode.canPullDown()) {
            this.mHeaderLoadingView.setReleaseLabel(releaseLabel);
        }
        if (this.mFooterLoadingView != null && mode.canPullUp()) {
            this.mFooterLoadingView.setReleaseLabel(releaseLabel);
        }
    }

    public void setPullLabel(String pullLabel, Mode mode) {
        super.setPullLabel(pullLabel, mode);
        if (this.mHeaderLoadingView != null && mode.canPullDown()) {
            this.mHeaderLoadingView.setPullLabel(pullLabel);
        }
        if (this.mFooterLoadingView != null && mode.canPullUp()) {
            this.mFooterLoadingView.setPullLabel(pullLabel);
        }
    }

    public void setRefreshingLabel(String refreshingLabel, Mode mode) {
        super.setRefreshingLabel(refreshingLabel, mode);
        if (this.mHeaderLoadingView != null && mode.canPullDown()) {
            this.mHeaderLoadingView.setRefreshingLabel(refreshingLabel);
        }
        if (this.mFooterLoadingView != null && mode.canPullUp()) {
            this.mFooterLoadingView.setRefreshingLabel(refreshingLabel);
        }
    }

    protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView lv = new InternalListView(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        FrameLayout frame = new FrameLayout(context);
        this.mHeaderLoadingView = new LoadLayout(context, Mode.PULL_DOWN_TO_REFRESH, a);
        frame.addView(this.mHeaderLoadingView, -1, -2);
        this.mHeaderLoadingView.setVisibility(GONE);
        lv.addHeaderView(frame, null, false);
        this.mLvFooterLoadingFrame = new FrameLayout(context);
        this.mFooterLoadingView = new LoadLayout(context, Mode.PULL_UP_TO_REFRESH, a);
        this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, -1, -2);
        this.mFooterLoadingView.setVisibility(GONE);
        a.recycle();
        lv.setId(16908298);
        return lv;
    }

    protected void setRefreshingInternal(boolean doScroll) {
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.setRefreshingInternal(doScroll);
            return;
        }
        LoadLayout originalLoadingLayout;
        LoadLayout listViewLoadingLayout;
        int selection;
        int scrollToY;
        super.setRefreshingInternal(false);
        switch (m2x936774ff()[getCurrentMode().ordinal()]) {
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = this.mFooterLoadingView;
                selection = ((ListView) this.mRefreshableView).getCount() - 1;
                scrollToY = getScrollY() - getHeaderHeight();
                break;
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = this.mHeaderLoadingView;
                selection = 0;
                scrollToY = getScrollY() + getHeaderHeight();
                break;
        }
        if (doScroll) {
            setHeaderScroll(scrollToY);
        }
        originalLoadingLayout.setVisibility(GONE);
        listViewLoadingLayout.setVisibility(VISIBLE);
        listViewLoadingLayout.refreshing();
        if (doScroll) {
            ((ListView) this.mRefreshableView).setSelection(selection);
            smoothScrollTo(0);
        }
    }

    protected void resetHeader() {
        boolean scroll = true;
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.resetHeader();
            return;
        }
        LoadLayout originalLoadingLayout;
        LoadLayout listViewLoadingLayout;
        int selection;
        int scrollToHeight = getHeaderHeight();
        switch (m2x936774ff()[getCurrentMode().ordinal()]) {
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = this.mFooterLoadingView;
                selection = ((ListView) this.mRefreshableView).getCount() - 1;
                if (((ListView) this.mRefreshableView).getLastVisiblePosition() != selection) {
                    scroll = false;
                }
                break;
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = this.mHeaderLoadingView;
                scrollToHeight *= -1;
                selection = 0;
                if (((ListView) this.mRefreshableView).getFirstVisiblePosition() != 0) {
                    scroll = false;
                    break;
                }
                break;
        }
        originalLoadingLayout.setVisibility(VISIBLE);
        if (scroll && getState() != 3) {
            ((ListView) this.mRefreshableView).setSelection(selection);
            setHeaderScroll(scrollToHeight);
        }
        listViewLoadingLayout.setVisibility(GONE);
        super.resetHeader();
    }

    protected int getNumberInternalHeaderViews() {
        return this.mHeaderLoadingView != null ? 1 : 0;
    }

    protected int getNumberInternalFooterViews() {
        return this.mFooterLoadingView != null ? 1 : 0;
    }
}
