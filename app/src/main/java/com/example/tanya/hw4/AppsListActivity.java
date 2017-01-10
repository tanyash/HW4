package com.example.tanya.hw4;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppsListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<AppInfo> data;
    private AutoCompleteTextView textView;
    private EditText editText;


    public List<AppInfo> getInstallApps() {
        List<ApplicationInfo> packages;
        List<AppInfo> appInfo = new ArrayList<AppInfo>() ;
        final PackageManager pm = getPackageManager();

        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            //packageInfo.processName
            AppInfo appCurrent =  new AppInfo((String) pm.getApplicationLabel(packageInfo), pm.getApplicationIcon(packageInfo),
                    pm.getLaunchIntentForPackage(packageInfo.packageName));
            appInfo.add(appCurrent);
        }

        return appInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        //get a list of installed apps.
        data = getInstallApps();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        editText = (EditText) findViewById(R.id.edit);

        onClickList(null);

        final ListAppsAdaptor mAdapter = new ListAppsAdaptor(data);
        final Filter filter = mAdapter.getFilter();

        //mAdapter.

        mAdapter.setOnItemClickListener(new ListAppsAdaptor.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Intent i =  data.get(position).getAppIntent();

                try {
                    startActivity(i);
                }

                catch(NullPointerException e)
                {
                }

            }

            @Override
            public void onItemLongClick(int position, View v) {
                Intent i =  data.get(position).getAppIntent();
                try {
                    startActivityForResult(i, RESULT_OK);
                }
                catch(NullPointerException e)
                {
                }
            }


        });

        mRecyclerView.setAdapter(mAdapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter.filter(s);
            }
        };

        editText.addTextChangedListener(textWatcher);


    }


    public void onClickGrid(View view) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void onClickList(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

}
