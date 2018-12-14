package com.thelittlefireman.appkillermanager.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ActionUtils {

    public static Intent createIntent() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent createIntent(ComponentName componentName) {
        Intent intent = createIntent();
        intent.setComponent(componentName);
        return intent;
    }

    public static Intent createIntent(String action) {
        Intent intent = createIntent();
        intent.setAction(action);
        return intent;
    }

    public static List<Intent> createIntentList(List<ComponentName> componentNameList) {
        List<Intent> intentList = new ArrayList<>();
        for (ComponentName componentName : componentNameList) {
            Intent intent = createIntent();
            intent.setComponent(componentName);
        }
        return intentList;
    }

    public static String getExtrasDebugInformationsWithKillerManagerAction(
            List<KillerManagerAction> killerManagerActionList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (KillerManagerAction action : killerManagerActionList) {
            stringBuilder.append(getExtrasDebugInformations(action.getIntentActionList()));
        }
        return stringBuilder.toString();
    }

    public static String getExtrasDebugInformations(List<Intent> intentList){
        StringBuilder stringBuilder = new StringBuilder();
        if(intentList !=null){
            if (intentList.isEmpty()){
                stringBuilder.append("intentList is isEmpty");
            }
        }else {
            stringBuilder.append("intent is null");
        }
        return stringBuilder.toString();
    }

    public static boolean isIntentAvailable(@NonNull Context ctx, @NonNull String actionIntent) {
        return isIntentAvailable(ctx, ActionUtils.createIntent().setAction(actionIntent));
    }

    public static boolean isIntentAvailable(@NonNull Context ctx, @NonNull ComponentName componentName) {
        return isIntentAvailable(ctx, ActionUtils.createIntent().setComponent(componentName));
    }

    public static boolean isAtLeastOneIntentAvailable(@NonNull Context ctx,
                                                      @NonNull KillerManagerAction killerManagerAction) {
        return isAtLeastOneIntentAvailable(ctx, killerManagerAction.getIntentActionList());
    }

    public static boolean isAtLeastOneIntentAvailable(@NonNull Context ctx, @NonNull List<Intent> intentList) {
        boolean rst = false;
        for (Intent intent : intentList) {
            rst= rst || isIntentAvailable(ctx,intent);
        }
        return rst;
    }
    public static boolean isIntentAvailable(@NonNull Context ctx, @NonNull Intent intent) {
        if (ctx != null && intent != null) {
            final PackageManager mgr = ctx.getPackageManager();
            List<ResolveInfo> list =
                    mgr.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
            return list != null && list.size() > 0;
        } else {
            return false;
        }
    }
}
