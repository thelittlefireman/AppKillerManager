package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelittlefireman.appkillermanager.R;

public class OppoUi extends DeviceBaseUi {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new OppoPageAdapter());

        return mRootView;
    }

    public class OppoSettingFragement extends Fragment {
        https://guides.codepath.com/android/viewpager-with-fragmentpageradapter

        private String mContentMessage;
        @DrawableRes
        private int mHelpImage;

        public static OppoSettingFragement newInstance(String contentMessage, @DrawableRes int helpImage) {
            mContentMessage = contentMessage;
            mHelpImage = helpImage;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View mFragmentView = inflater.inflate(R.layout.md_dialog_main_content, container, false);
            TextView textView = mFragmentView.findViewById(R.id.md_content_message);
            ImageView imageView = mFragmentView.findViewById(R.id.md_help_image);
            //TODO OPPO IMAGES
            imageView.setVisibility(View.GONE);
            //imageView.setImageResource(mHelpImage);
            //TODO OPPO text
            textView.setText(mContentMessage);
            return mFragmentView;
        }
    }

    private class OppoPageAdapter extends FragmentPagerAdapter {
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    }

}
