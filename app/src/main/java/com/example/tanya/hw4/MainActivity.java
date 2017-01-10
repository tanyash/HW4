package com.example.tanya.hw4;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    private Intent startIntentForView(View view) {
        Intent i;

        switch(view.getId()){
            case R.id.sms_btn:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:"));
                break;
            case R.id.call_btn:
                i = new Intent(Intent.ACTION_DIAL);
                break;
            default:
                i = new Intent(this, AppsListActivity.class);
        }

        startActivity(i);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntentForView(view);
            }
        };

        findViewById(R.id.call_btn).setOnClickListener(buttonListener);
        findViewById(R.id.app_btn).setOnClickListener(buttonListener);
        findViewById(R.id.sms_btn).setOnClickListener(buttonListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;

    }


}
