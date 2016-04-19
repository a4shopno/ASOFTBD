package com.shojib.asoftbd.adust;

/**
 * Created by Shopno-Shomu on 15-02-16.
 */
public class NavDrawerItem {
    private String count;
    private int icon;
    private boolean isCounterVisible;
    private String title;

    public NavDrawerItem() {
        this.count ="0";
        this.isCounterVisible = false;
    }

    public NavDrawerItem(String count, int icon) {
        this.count ="0";
        this.isCounterVisible = false;
        this.count = count;
        this.icon = icon;
    }

    public NavDrawerItem(String count, int icon, boolean isCounterVisible, String title) {
        this.count ="0";
        this.isCounterVisible = false;
        this.count = count;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isCounterVisible() {
        return this.isCounterVisible;
    }

    public void setIsCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
