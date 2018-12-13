package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.deviceUi.fragments.SettingPageFragment;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SettingFragment extends Fragment{
    protected View mRootView;

    private static final String KILLER_MANAGER_LIST = "KILLER_MANAGER_LIST";

    protected DialogKillerManagerBuilder mDialogKillerManagerBuilder;

    protected LayoutInflater mInfalter;

    private ViewPager mViewPager;

    protected KillerManager mKillerManager = KillerManager.getInstance(getContext());
    private List<SettingPageFragment> mSettingPageFragmentList;

    private List<KillerManagerAction> mKillerManagerActionList;

    public static SettingFragment newInstance(List<KillerManagerAction> killerManagerActionList) {
        SettingFragment fragmentFirst = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(KILLER_MANAGER_LIST, KillerManagerAction.toJsonList(killerManagerActionList));
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKillerManagerActionList = KillerManagerAction.fromJsonList(getArguments().getString(KILLER_MANAGER_LIST));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mInfalter = inflater;
        mSettingPageFragmentList = new ArrayList<>();

        mRootView = inflater.inflate(R.layout.md_dialog_ui, container, false);
        mViewPager = mRootView.findViewById(R.id.md_help_image_viewpager);

        mViewPager.setAdapter(new SettingPageAdapter(getFragmentManager()));

        initKillerManagerAction(mKillerManagerActionList);
        return mRootView;
    }

    private void initKillerManagerAction(List<KillerManagerAction> killerManagerActionList) {
        for (KillerManagerAction killerManagerAction : killerManagerActionList) {
            SettingPageFragment settingPageFragment = SettingPageFragment.newInstance(killerManagerAction);
            mSettingPageFragmentList.add(settingPageFragment);
        }
    }


    private class SettingPageAdapter extends FragmentPagerAdapter {
        public SettingPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mSettingPageFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mSettingPageFragmentList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    }
}
