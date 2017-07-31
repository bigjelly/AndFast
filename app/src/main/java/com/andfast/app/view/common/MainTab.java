package com.andfast.app.view.common;

import com.andfast.app.R;
import com.andfast.app.view.home.HomeFragment;
import com.andfast.app.view.me.MeFragment;
import com.andfast.app.view.video.VideoFragment;

/**
 * Created by mby on 17-7-31.
 */

public enum MainTab {
    HOME(0, R.string.tab_name_home, R.drawable.tab_home_selector, HomeFragment.class),
    VIDEO(0, R.string.tab_name_video, R.drawable.tab_home_selector, VideoFragment.class),
    ME(0, R.string.tab_name_my, R.drawable.tab_home_selector, MeFragment.class);
    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clazz;

    MainTab(int idx, int resName, int resIcon, Class<?> clazz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clazz = clazz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
