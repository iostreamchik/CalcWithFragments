package com.example.CalcWithFragments.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AppPickActionProvider extends ActionProvider implements
        OnMenuItemClickListener {

    static final int LIST_LENGTH = 3;

    Context mContext;

    public AppPickActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        Log.d("AppPick", "onCreateActionView");

        TextView textView = new TextView(mContext);
        textView.setText("Pick");

        return null; // null を返してもいい
    }

    @Override
    public boolean onPerformDefaultAction() {
        Log.d("AppPick", "onPerformDefaultAction");

        return super.onPerformDefaultAction();
    }

    @Override
    public boolean hasSubMenu() {
        Log.d("AppPick", "hasSubMenu");

        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        Log.d("AppPick", "onPrepareSubMenu");

        subMenu.clear();

        PackageManager manager = mContext.getPackageManager();
        List<ApplicationInfo> applicationList = manager
                .getInstalledApplications(PackageManager.GET_ACTIVITIES);

        for (int i = 0; i < Math.min(LIST_LENGTH, applicationList.size()); i++) {
            ApplicationInfo appInfo = applicationList.get(i);

            subMenu.add(0, i, i, manager.getApplicationLabel(appInfo))
                    .setIcon(appInfo.loadIcon(manager))
                    .setOnMenuItemClickListener(this);
        }

        if (LIST_LENGTH < applicationList.size()) {
            subMenu = subMenu.addSubMenu(Menu.NONE, LIST_LENGTH, LIST_LENGTH,
                    "hoge");

            for (int i = 0; i < applicationList.size(); i++) {
                ApplicationInfo appInfo = applicationList.get(i);

                subMenu.add(0, i, i, manager.getApplicationLabel(appInfo))
                        .setIcon(appInfo.loadIcon(manager))
                        .setOnMenuItemClickListener(this);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}