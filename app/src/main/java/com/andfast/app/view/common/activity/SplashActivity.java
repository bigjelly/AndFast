package com.andfast.app.view.common.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;
import com.andfast.app.util.RXTimingTask;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.statusbar.Eyes;
import com.bumptech.glide.Glide;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by mby on 17-8-4.
 * 广告页
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ImageView mAdImg;
    private TextView  mSkipReal;
    private Subscription mSubscribe;

    @Override
    protected int getContentView() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {
        super.initView();
        Eyes.translucentStatusBar(SplashActivity.this);

        mAdImg = findView(R.id.ad_img_splash);
        mSkipReal = findView(R.id.skip_real);

        mSkipReal.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mSubscribe = RXTimingTask.countDown(5).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Glide.with(mContext)
                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502881074229&di=c5797680e89ffd998d3a822af32605ce&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fupload%2F20170705%2F4f0f54cc3123473892d97de67c20a53f_th.jpg")
                        .placeholder(R.mipmap.null_feeds)
                        .crossFade()
                        .into(mAdImg);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                intent2Activity(MainActivity.class);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                mSkipReal.setText(TextUtils.concat(integer.intValue() + "s", getResources().getString(R.string.splash_ad_ignore)));
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscribe != null && !mSubscribe.isUnsubscribed())
            mSubscribe.unsubscribe();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skip_real:
                intent2Activity(MainActivity.class);
                finish();
                break;
        }
    }
}
