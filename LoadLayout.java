package com.shojib.asoftbd.adust;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.shojib.asoftbd.adust.R;
import com.shojib.asoftbd.adust.PullToRefreshBase.Mode;

/**
 * Created by Shopno-Shomu on 23-02-16.
 */
public class LoadLayout extends FrameLayout {
    private static /* synthetic */ int[] f3x936774ff = null;
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
    private final ImageView mHeaderImage;
    private final ProgressBar mHeaderProgress;
    private final TextView mHeaderText;
    private String mPullLabel;
    private String mRefreshingLabel;
    private String mReleaseLabel;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;
    private final TextView mSubHeaderText;

    static /* synthetic */ int[] m0x936774ff() {
        int[] iArr = f3x936774ff;
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
            f3x936774ff = iArr;
        }
        return iArr;
    }

    public LoadLayout(Context context, Mode mode, TypedArray attrs) {
        super(context);
        ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.refreshheader_ptr, this);
        this.mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
        this.mSubHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_sub_text);
        this.mHeaderImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
        this.mHeaderProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
        Interpolator interpolator = new LinearInterpolator();
        this.mRotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation.setInterpolator(interpolator);
        this.mRotateAnimation.setDuration(150);
        this.mRotateAnimation.setFillAfter(true);
        this.mResetRotateAnimation = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation.setInterpolator(interpolator);
        this.mResetRotateAnimation.setDuration(150);
        this.mResetRotateAnimation.setFillAfter(true);
        ColorStateList colors;
        switch (m0x936774ff()[mode.ordinal()]) {
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                this.mHeaderImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
                this.mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                break;
            default:
                this.mHeaderImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
                this.mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
                break;
        }
        if (attrs.hasValue(2)) {
            colors = attrs.getColorStateList(2);
            if (colors == null) {
                colors = ColorStateList.valueOf(16777216);
            }
            setTextColor(colors);
        }
        if (attrs.hasValue(3)) {
            colors = attrs.getColorStateList(3);
            if (colors == null) {
                colors = ColorStateList.valueOf(16777216);
            }
            setSubTextColor(colors);
        }
        if (attrs.hasValue(1)) {
            Drawable background = attrs.getDrawable(1);
            if (background != null) {
                setBackgroundDrawable(background);
            }
        }
        reset();
    }

    public void reset() {
        this.mHeaderText.setText(Html.fromHtml(this.mPullLabel));
        this.mHeaderImage.setVisibility(VISIBLE);
        this.mHeaderProgress.setVisibility(GONE);
        if (TextUtils.isEmpty(this.mSubHeaderText.getText())) {
            this.mSubHeaderText.setVisibility(GONE);
        } else {
            this.mSubHeaderText.setVisibility(VISIBLE);
        }
    }

    public void releaseToRefresh() {
        this.mHeaderText.setText(Html.fromHtml(this.mReleaseLabel));
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    public void setPullLabel(String pullLabel) {
        this.mPullLabel = pullLabel;
    }

    public void refreshing() {
        this.mHeaderText.setText(Html.fromHtml(this.mRefreshingLabel));
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.setVisibility(GONE);
        this.mHeaderProgress.setVisibility(VISIBLE);
        this.mSubHeaderText.setVisibility(VISIBLE);
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.mRefreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(String releaseLabel) {
        this.mReleaseLabel = releaseLabel;
    }

    public void pullToRefresh() {
        this.mHeaderText.setText(Html.fromHtml(this.mPullLabel));
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.startAnimation(this.mResetRotateAnimation);
    }

    public void setTextColor(ColorStateList color) {
        this.mHeaderText.setTextColor(color);
        this.mSubHeaderText.setTextColor(color);
    }

    public void setSubTextColor(ColorStateList color) {
        this.mSubHeaderText.setTextColor(color);
    }

    public void setTextColor(int color) {
        setTextColor(ColorStateList.valueOf(color));
    }

    public void setSubTextColor(int color) {
        setSubTextColor(ColorStateList.valueOf(color));
    }

    public void setSubHeaderText(CharSequence label) {
        if (TextUtils.isEmpty(label)) {
            this.mSubHeaderText.setVisibility(GONE);
            return;
        }
        this.mSubHeaderText.setText(label);
        this.mSubHeaderText.setVisibility(VISIBLE);
    }
}