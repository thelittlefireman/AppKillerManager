package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thelittlefireman.appkillermanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SamsungUi extends DeviceBaseUi {

    List<Integer> mTutorialImageList;
    private ViewPager mPager;

    private int mCurrentPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mTutorialImageList = new ArrayList<>();
        // TODO SPLIT INTO SEVERAL IMAGES
        mTutorialImageList.add(R.drawable.samsung);
        // Inflate the layout for this fragment

        mPager = mRootView.findViewById(R.id.md_dialog_samsung_pager_imageView);
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mTutorialImageList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup view, int position) {
                View myImageLayout = mInfalter.inflate(R.layout.md_dialog_samsung_ui_pager_content, view, false);
                ImageView myImage = myImageLayout.findViewById(R.id.sumsung_imageview);
                myImage.setImageResource(mTutorialImageList.get(position));
                view.addView(myImageLayout, 0);
                return myImageLayout;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view.equals(object);
            }
        });

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (mCurrentPage == mTutorialImageList.size()) {
                    mCurrentPage = 0;
                }
                mPager.setCurrentItem(mCurrentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
        return mRootView;
    }

    @Override
    public int getLayout() {
        return R.layout.md_dialog_samsung_ui;
    }
}
