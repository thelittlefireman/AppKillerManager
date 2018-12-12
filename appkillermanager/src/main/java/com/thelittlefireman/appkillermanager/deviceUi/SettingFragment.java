package com.thelittlefireman.appkillermanager.deviceUi;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.deviceUi.fragments.SettingPageFragment;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.models.KillerManagerActionType;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment implements SettingFragmentClicListener {
    protected View mRootView;

    protected KillerManagerActionType mAction;

    protected DialogKillerManagerBuilder mDialogKillerManagerBuilder;

    protected CheckBox mDoNotShowAgainCheckBox;

    protected Button mButtonOpenSettings;
    protected Button mButtonClose;

    protected LayoutInflater mInfalter;

    private ViewPager mViewPager;

    protected KillerManager mKillerManager = KillerManager.getInstance(getContext());
    private List<SettingPageFragment> mSettingPageFragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mInfalter = inflater;
        mSettingPageFragmentList = new ArrayList<>();
        
        mRootView = inflater.inflate(R.layout.md_dialog_simple_ui, container, false);
        mButtonOpenSettings = mRootView.findViewById(R.id.md_button_open_settings);
        mButtonClose = mRootView.findViewById(R.id.md_button_close);
        mViewPager = mRootView.findViewById(R.id.md_help_image_viewpager);

        mButtonOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicOpenSettings();
            }
        });
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicClose();
            }
        });
        // ----  Common UI ----
        if (mDialogKillerManagerBuilder.isEnableDontShowAgain()) {
            mDoNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            mDoNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(getActivity(), mAction, isChecked);
                }
            });
        } else {
            mDoNotShowAgainCheckBox.setVisibility(View.GONE);
        }

        mViewPager.setAdapter(new SettingPageAdapter(getFragmentManager()));

        initKillerManagerAction()
        return mRootView;
    }

    private void initKillerManagerAction(List<KillerManagerAction> killerManagerActionList) {
        for (KillerManagerAction killerManagerAction : killerManagerActionList) {
            SettingPageFragment settingPageFragment = SettingPageFragment.newInstance(killerManagerAction.getHelpText(),
                                                                                      killerManagerAction.getHelpImage());
            settingPageFragment.setIntentAction(killerManagerAction.getIntentAction());
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

    @Override
    public void onClicOpenSettings() {
        mKillerManager.doAction(getActivity(), mAction);
    }

    @Override
    public void onClicClose() {
        //TODO
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        mKillerManager.onActivityResult(getActivity(), mAction, requestCode);
    }

}
