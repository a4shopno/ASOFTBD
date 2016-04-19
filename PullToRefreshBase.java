package com.shojib.asoftbd.adust;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.shojib.asoftbd.adust.R;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout {
    private static /* synthetic */ int[] f4x936774ff = null;
    static final boolean DEBUG = false;
    static final Mode DEFAULT_MODE;
    static final float FRICTION = 2.0f;
    static final String LOG_TAG = "PullToRefresh";
    static final int MANUAL_REFRESHING = 3;
    static final int PULL_TO_REFRESH = 0;
    static final int REFRESHING = 2;
    static final int RELEASE_TO_REFRESH = 1;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_DISABLE_SCROLLING_REFRESHING = "ptr_disable_scrolling";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    private Mode mCurrentMode;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mDisableScrollingWhileRefreshing;
    private boolean mFilterTouchEvents;
    private LoadLayout mFooterLayout;
    private final Handler mHandler;
    private int mHeaderHeight;
    private LoadLayout mHeaderLayout;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private float mLastMotionX;
    private float mLastMotionY;
    private Mode mMode;
    private OnRefreshListener mOnRefreshListener;
    private OnRefreshListener2 mOnRefreshListener2;
    private boolean mPullToRefreshEnabled;
    T mRefreshableView;
    private boolean mShowViewWhileRefreshing;
    private int mState;
    private int mTouchSlop;

    public enum Mode {
        PULL_DOWN_TO_REFRESH(PullToRefreshBase.RELEASE_TO_REFRESH),
        PULL_UP_TO_REFRESH(PullToRefreshBase.REFRESHING),
        BOTH(PullToRefreshBase.MANUAL_REFRESHING);

        private int mIntValue;

        private Mode(int modeInt) {
            this.mIntValue = modeInt;
        }

        int getIntValue() {
            return this.mIntValue;
        }

        boolean canPullDown() {
            return (this == PULL_DOWN_TO_REFRESH || this == BOTH) ? true : PullToRefreshBase.DEBUG;
        }

        boolean canPullUp() {
            return (this == PULL_UP_TO_REFRESH || this == BOTH) ? true : PullToRefreshBase.DEBUG;
        }

        public static Mode mapIntToMode(int modeInt) {
            switch (modeInt) {
                case PullToRefreshBase.REFRESHING /*2*/:
                    return PULL_UP_TO_REFRESH;
                case PullToRefreshBase.MANUAL_REFRESHING /*3*/:
                    return BOTH;
                default:
                    return PULL_DOWN_TO_REFRESH;
            }
        }
    }

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    public interface OnRefreshListener2 {
        void onPullDownToRefresh();

        void onPullUpToRefresh();
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    final class SmoothScrollRunnable implements Runnable {
        static final int ANIMATION_DURATION_MS = 190;
        static final int ANIMATION_FPS = 16;
        private boolean mContinueRunning;
        private int mCurrentY;
        private final Handler mHandler;
        private final Interpolator mInterpolator;
        private final int mScrollFromY;
        private final int mScrollToY;
        private long mStartTime;

        public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
            this.mContinueRunning = true;
            this.mStartTime = -1;
            this.mCurrentY = -1;
            this.mHandler = handler;
            this.mScrollFromY = fromY;
            this.mScrollToY = toY;
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        }

        public void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / 190, 1000), 0)) / 1000.0f));
                PullToRefreshBase.this.setHeaderScroll(this.mCurrentY);
            }
            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                this.mHandler.postDelayed(this, 16);
            }
        }

        public void stop() {
            this.mContinueRunning = PullToRefreshBase.DEBUG;
            this.mHandler.removeCallbacks(this);
        }
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    protected abstract boolean isReadyForPullDown();

    protected abstract boolean isReadyForPullUp();

    static /* synthetic */ int[] m1x936774ff() {
        int[] iArr = f4x936774ff;
        if (iArr == null) {
            iArr = new int[Mode.values().length];
            try {
                iArr[Mode.BOTH.ordinal()] = MANUAL_REFRESHING;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Mode.PULL_DOWN_TO_REFRESH.ordinal()] = RELEASE_TO_REFRESH;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Mode.PULL_UP_TO_REFRESH.ordinal()] = REFRESHING;
            } catch (NoSuchFieldError e3) {
            }
            f4x936774ff = iArr;
        }
        return iArr;
    }

    static {
        DEFAULT_MODE = Mode.PULL_DOWN_TO_REFRESH;
    }

    public PullToRefreshBase(Context context) {
        super(context);
        this.mIsBeingDragged = DEBUG;
        this.mState = PULL_TO_REFRESH;
        this.mMode = DEFAULT_MODE;
        this.mPullToRefreshEnabled = true;
        this.mShowViewWhileRefreshing = true;
        this.mDisableScrollingWhileRefreshing = true;
        this.mFilterTouchEvents = true;
        this.mHandler = new Handler();
        init(context, null);
    }

    public PullToRefreshBase(Context context, Mode mode) {
        super(context);
        this.mIsBeingDragged = DEBUG;
        this.mState = PULL_TO_REFRESH;
        this.mMode = DEFAULT_MODE;
        this.mPullToRefreshEnabled = true;
        this.mShowViewWhileRefreshing = true;
        this.mDisableScrollingWhileRefreshing = true;
        this.mFilterTouchEvents = true;
        this.mHandler = new Handler();
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsBeingDragged = DEBUG;
        this.mState = PULL_TO_REFRESH;
        this.mMode = DEFAULT_MODE;
        this.mPullToRefreshEnabled = true;
        this.mShowViewWhileRefreshing = true;
        this.mDisableScrollingWhileRefreshing = true;
        this.mFilterTouchEvents = true;
        this.mHandler = new Handler();
        init(context, attrs);
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mPullToRefreshEnabled;
    }

    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final Mode getMode() {
        return this.mMode;
    }

    public final void setMode(Mode mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            updateUIForMode();
        }
    }

    public final boolean isDisableScrollingWhileRefreshing() {
        return this.mDisableScrollingWhileRefreshing;
    }

    public final boolean isRefreshing() {
        return (this.mState == REFRESHING || this.mState == MANUAL_REFRESHING) ? true : DEBUG;
    }

    public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        this.mDisableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
    }

    public final void onRefreshComplete() {
        if (this.mState != 0) {
            resetHeader();
        }
    }

    public final void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public final void setOnRefreshListener(OnRefreshListener2 listener) {
        this.mOnRefreshListener2 = listener;
    }

    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    public final void setFilterTouchEvents(boolean filterEvents) {
        this.mFilterTouchEvents = filterEvents;
    }

    public final void setPullToRefreshEnabled(boolean enable) {
        this.mPullToRefreshEnabled = enable;
    }

    public final void setShowViewWhileRefreshing(boolean showView) {
        this.mShowViewWhileRefreshing = showView;
    }

    public void setReleaseLabel(String releaseLabel) {
        setReleaseLabel(releaseLabel, Mode.BOTH);
    }

    public void setPullLabel(String pullLabel) {
        setPullLabel(pullLabel, Mode.BOTH);
    }

    public void setRefreshingLabel(String refreshingLabel) {
        setRefreshingLabel(refreshingLabel, Mode.BOTH);
    }

    public void setReleaseLabel(String releaseLabel, Mode mode) {
        if (this.mHeaderLayout != null && this.mMode.canPullDown()) {
            this.mHeaderLayout.setReleaseLabel(releaseLabel);
        }
        if (this.mFooterLayout != null && this.mMode.canPullUp()) {
            this.mFooterLayout.setReleaseLabel(releaseLabel);
        }
    }

    public void setPullLabel(String pullLabel, Mode mode) {
        if (this.mHeaderLayout != null && this.mMode.canPullDown()) {
            this.mHeaderLayout.setPullLabel(pullLabel);
        }
        if (this.mFooterLayout != null && this.mMode.canPullUp()) {
            this.mFooterLayout.setPullLabel(pullLabel);
        }
    }

    public void setRefreshingLabel(String refreshingLabel, Mode mode) {
        if (this.mHeaderLayout != null && this.mMode.canPullDown()) {
            this.mHeaderLayout.setRefreshingLabel(refreshingLabel);
        }
        if (this.mFooterLayout != null && this.mMode.canPullUp()) {
            this.mFooterLayout.setRefreshingLabel(refreshingLabel);
        }
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setRefreshingInternal(doScroll);
            this.mState = MANUAL_REFRESHING;
        }
    }

    public void setLastUpdatedLabel(CharSequence label) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setSubHeaderText(label);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setSubHeaderText(label);
        }
    }

    public final boolean hasPullFromTop() {
        return this.mCurrentMode == Mode.PULL_DOWN_TO_REFRESH ? true : DEBUG;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        if (!this.mPullToRefreshEnabled) {
            return DEBUG;
        }
        if (isRefreshing() && this.mDisableScrollingWhileRefreshing) {
            return true;
        }
        if (event.getAction() == 0 && event.getEdgeFlags() != 0) {
            return DEBUG;
        }
        switch (event.getAction()) {
            case PULL_TO_REFRESH /*0*/:
                if (!isReadyForPull()) {
                    return DEBUG;
                }
                float y = event.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                return true;
            case RELEASE_TO_REFRESH /*1*/:
            case MANUAL_REFRESHING /*3*/:
                if (!this.mIsBeingDragged) {
                    return DEBUG;
                }
                this.mIsBeingDragged = DEBUG;
                if (this.mState != RELEASE_TO_REFRESH) {
                    smoothScrollTo(PULL_TO_REFRESH);
                    return true;
                } else if (this.mOnRefreshListener != null) {
                    setRefreshingInternal(true);
                    this.mOnRefreshListener.onRefresh();
                    return true;
                } else if (this.mOnRefreshListener2 == null) {
                    return true;
                } else {
                    setRefreshingInternal(true);
                    if (this.mCurrentMode == Mode.PULL_DOWN_TO_REFRESH) {
                        this.mOnRefreshListener2.onPullDownToRefresh();
                    } else if (this.mCurrentMode == Mode.PULL_UP_TO_REFRESH) {
                        this.mOnRefreshListener2.onPullUpToRefresh();
                    }
                    return true;
                }
            case REFRESHING /*2*/:
                if (!this.mIsBeingDragged) {
                    return DEBUG;
                }
                this.mLastMotionY = event.getY();
                pullEvent();
                return true;
            default:
                return DEBUG;
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mPullToRefreshEnabled) {
            return DEBUG;
        }
        if (isRefreshing() && this.mDisableScrollingWhileRefreshing) {
            return true;
        }
        int action = event.getAction();
        if (action == MANUAL_REFRESHING || action == RELEASE_TO_REFRESH) {
            this.mIsBeingDragged = DEBUG;
            return DEBUG;
        } else if (action != 0 && this.mIsBeingDragged) {
            return true;
        } else {
            switch (action) {
                case PULL_TO_REFRESH /*0*/:
                    if (isReadyForPull()) {
                        float y = event.getY();
                        this.mInitialMotionY = y;
                        this.mLastMotionY = y;
                        this.mLastMotionX = event.getX();
                        this.mIsBeingDragged = DEBUG;
                        break;
                    }
                    break;
                case REFRESHING /*2*/:
                    if (isReadyForPull()) {
                        float y2 = event.getY();
                        float dy = y2 - this.mLastMotionY;
                        float yDiff = Math.abs(dy);
                        float xDiff = Math.abs(event.getX() - this.mLastMotionX);
                        if (yDiff > ((float) this.mTouchSlop) && (!this.mFilterTouchEvents || yDiff > xDiff)) {
                            if (!this.mMode.canPullDown() || dy < 1.0f || !isReadyForPullDown()) {
                                if (this.mMode.canPullUp() && dy <= -1.0f && isReadyForPullUp()) {
                                    this.mLastMotionY = y2;
                                    this.mIsBeingDragged = true;
                                    if (this.mMode == Mode.BOTH) {
                                        this.mCurrentMode = Mode.PULL_UP_TO_REFRESH;
                                        break;
                                    }
                                }
                            }
                            this.mLastMotionY = y2;
                            this.mIsBeingDragged = true;
                            if (this.mMode == Mode.BOTH) {
                                this.mCurrentMode = Mode.PULL_DOWN_TO_REFRESH;
                                break;
                            }
                        }
                    }
                    break;
            }
            return this.mIsBeingDragged;
        }
    }

    protected void addRefreshableView(Context context, T refreshableView) {
        addView(refreshableView, new LayoutParams(-1, PULL_TO_REFRESH, 1.0f));
    }

    protected final LoadLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final LoadLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderHeight() {
        return this.mHeaderHeight;
    }

    protected final int getState() {
        return this.mState;
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_STATE, this.mState);
        bundle.putInt(STATE_MODE, this.mMode.getIntValue());
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.getIntValue());
        bundle.putBoolean(STATE_DISABLE_SCROLLING_REFRESHING, this.mDisableScrollingWhileRefreshing);
        bundle.putBoolean(STATE_SHOW_REFRESHING_VIEW, this.mShowViewWhileRefreshing);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mMode = Mode.mapIntToMode(bundle.getInt(STATE_MODE, PULL_TO_REFRESH));
            this.mCurrentMode = Mode.mapIntToMode(bundle.getInt(STATE_CURRENT_MODE, PULL_TO_REFRESH));
            this.mDisableScrollingWhileRefreshing = bundle.getBoolean(STATE_DISABLE_SCROLLING_REFRESHING, true);
            this.mShowViewWhileRefreshing = bundle.getBoolean(STATE_SHOW_REFRESHING_VIEW, true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            int viewState = bundle.getInt(STATE_STATE, PULL_TO_REFRESH);
            if (viewState == REFRESHING) {
                setRefreshingInternal(true);
                this.mState = viewState;
                return;
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected void resetHeader() {
        this.mState = PULL_TO_REFRESH;
        this.mIsBeingDragged = DEBUG;
        if (this.mMode.canPullDown()) {
            this.mHeaderLayout.reset();
        }
        if (this.mMode.canPullUp()) {
            this.mFooterLayout.reset();
        }
        smoothScrollTo(PULL_TO_REFRESH);
    }

    protected void setRefreshingInternal(boolean doScroll) {
        this.mState = REFRESHING;
        if (this.mMode.canPullDown()) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mMode.canPullUp()) {
            this.mFooterLayout.refreshing();
        }
        if (!doScroll) {
            return;
        }
        if (this.mShowViewWhileRefreshing) {
            int i;
            if (this.mCurrentMode == Mode.PULL_DOWN_TO_REFRESH) {
                i = -this.mHeaderHeight;
            } else {
                i = this.mHeaderHeight;
            }
            smoothScrollTo(i);
            return;
        }
        smoothScrollTo(PULL_TO_REFRESH);
    }

    protected final void setHeaderScroll(int y) {
        scrollTo(PULL_TO_REFRESH, y);
    }

    protected final void smoothScrollTo(int y) {
        if (this.mCurrentSmoothScrollRunnable != null) {
            this.mCurrentSmoothScrollRunnable.stop();
        }
        if (getScrollY() != y) {
            this.mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(this.mHandler, getScrollY(), y);
            this.mHandler.post(this.mCurrentSmoothScrollRunnable);
        }
    }

    protected void updateUIForMode() {
        if (this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
        if (this.mMode.canPullDown()) {
            addView(this.mHeaderLayout, PULL_TO_REFRESH, new LayoutParams(-1, -2));
            measureView(this.mHeaderLayout);
            this.mHeaderHeight = this.mHeaderLayout.getMeasuredHeight();
        }
        if (this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
        if (this.mMode.canPullUp()) {
            addView(this.mFooterLayout, new LayoutParams(-1, -2));
            measureView(this.mFooterLayout);
            this.mHeaderHeight = this.mFooterLayout.getMeasuredHeight();
        }
        switch (m1x936774ff()[this.mMode.ordinal()]) {
            case REFRESHING /*2*/:
                setPadding(PULL_TO_REFRESH, PULL_TO_REFRESH, PULL_TO_REFRESH, -this.mHeaderHeight);
                break;
            case MANUAL_REFRESHING /*3*/:
                setPadding(PULL_TO_REFRESH, -this.mHeaderHeight, PULL_TO_REFRESH, -this.mHeaderHeight);
                break;
            default:
                setPadding(PULL_TO_REFRESH, -this.mHeaderHeight, PULL_TO_REFRESH, PULL_TO_REFRESH);
                break;
        }
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : Mode.PULL_DOWN_TO_REFRESH;
    }

    private void init(Context context, AttributeSet attrs) {
        Drawable background;
        setOrientation(LinearLayout.VERTICAL);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        if (a.hasValue(4)) {
            this.mMode = Mode.mapIntToMode(a.getInteger(4, PULL_TO_REFRESH));
        }
        this.mRefreshableView = createRefreshableView(context, attrs);
        addRefreshableView(context, this.mRefreshableView);
        this.mHeaderLayout = new LoadLayout(context, Mode.PULL_DOWN_TO_REFRESH, a);
        this.mFooterLayout = new LoadLayout(context, Mode.PULL_UP_TO_REFRESH, a);
        updateUIForMode();
        if (a.hasValue(RELEASE_TO_REFRESH)) {
            background = a.getDrawable(RELEASE_TO_REFRESH);
            if (background != null) {
                setBackgroundDrawable(background);
            }
        }
        if (a.hasValue(PULL_TO_REFRESH)) {
            background = a.getDrawable(PULL_TO_REFRESH);
            if (background != null) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        }
        a.recycle();
    }

    private void measureView(View child) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(PULL_TO_REFRESH, PULL_TO_REFRESH, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.AT_MOST);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(PULL_TO_REFRESH, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private boolean pullEvent() {
        int newHeight;
        int oldHeight = getScrollY();
        switch (m1x936774ff()[this.mCurrentMode.ordinal()]) {
            case REFRESHING /*2*/:
                newHeight = Math.round(Math.max(this.mInitialMotionY - this.mLastMotionY, 0.0f) / FRICTION);
                break;
            default:
                newHeight = Math.round(Math.min(this.mInitialMotionY - this.mLastMotionY, 0.0f) / FRICTION);
                break;
        }
        setHeaderScroll(newHeight);
        if (newHeight != 0) {
            if (this.mState == 0 && this.mHeaderHeight < Math.abs(newHeight)) {
                this.mState = RELEASE_TO_REFRESH;
                switch (m1x936774ff()[this.mCurrentMode.ordinal()]) {
                    case RELEASE_TO_REFRESH /*1*/:
                        this.mHeaderLayout.releaseToRefresh();
                        return true;
                    case REFRESHING /*2*/:
                        this.mFooterLayout.releaseToRefresh();
                        return true;
                    default:
                        return true;
                }
            } else if (this.mState == RELEASE_TO_REFRESH && this.mHeaderHeight >= Math.abs(newHeight)) {
                this.mState = PULL_TO_REFRESH;
                switch (m1x936774ff()[this.mCurrentMode.ordinal()]) {
                    case RELEASE_TO_REFRESH /*1*/:
                        this.mHeaderLayout.pullToRefresh();
                        return true;
                    case REFRESHING /*2*/:
                        this.mFooterLayout.pullToRefresh();
                        return true;
                    default:
                        return true;
                }
            }
        }
        if (oldHeight == newHeight) {
            return DEBUG;
        }
        return true;
    }

    private boolean isReadyForPull() {
        switch (m1x936774ff()[this.mMode.ordinal()]) {
            case RELEASE_TO_REFRESH /*1*/:
                return isReadyForPullDown();
            case REFRESHING /*2*/:
                return isReadyForPullUp();
            case MANUAL_REFRESHING /*3*/:
                return (isReadyForPullUp() || isReadyForPullDown()) ? true : DEBUG;
            default:
                return DEBUG;
        }
    }

    public void setLongClickable(boolean longClickable) {
        getRefreshableView().setLongClickable(longClickable);
    }
}