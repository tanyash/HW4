package com.example.tanya.hw4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getwidth(ViewGroup p) {
        int width = 0;

        float k = 1;
        int marginSum = 0;

        ViewGroup v = p;
        while (width == 0) {
            width = v.getMeasuredWidth();

            if (width <= 0) {
                if (v.getParent() != null) {
                    if ((v.getParent() instanceof LinearLayout) && (((LinearLayout) v.getParent()).getOrientation() == LinearLayout.HORIZONTAL)) {
                        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) (v.getLayoutParams());
                        if (lp1.weight != 0) {
                            k *= lp1.weight;
                        }
                        marginSum += lp1.leftMargin + lp1.rightMargin;
                    }

                    v = (ViewGroup) v.getParent();
                    continue;
                } else {

                    DisplayMetrics size = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(size);
                    width = size.widthPixels;

                    break;

                }
            }
        }

        width = Math.round(width * k) - marginSum;
        return width;

    }

    public int getheight(ViewGroup v) {
        int height = 0;
        float k = 1;
        int marginSum = 0;

        while (height == 0) {
            height = v.getMeasuredHeight();

            if (height <= 0) {
                if (v.getParent() != null) {
                    if ((v.getParent() instanceof LinearLayout) && (((LinearLayout) v.getParent()).getOrientation() == LinearLayout.VERTICAL)) {
                        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) (v.getLayoutParams());
                        if (lp1.weight != 0) {
                            k *= lp1.weight;
                        }
                        marginSum += lp1.topMargin +lp1.bottomMargin;
                    }
                    v = (ViewGroup) v.getParent();
                    continue;
                } else {

                    DisplayMetrics size = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(size );
                    height = size.heightPixels;
                    break;

                }
            }
        }
        height = Math.round(height * k) - marginSum - 3 * getStatusBarHeight();
        return height;
    }

    public View addDataItemToGrid(GridLayout grid, int margin){
        TextView textView = new TextView(this);
        grid.addView(textView);

        GridLayout.LayoutParams params = (GridLayout.LayoutParams) textView.getLayoutParams();


        params.setMargins(margin, margin, margin, margin);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);

        params.width = ((getwidth(grid)) / (grid.getColumnCount())) - params.rightMargin - params.leftMargin;
        params.height = ((getheight(grid)) / (grid.getRowCount())) - params.topMargin - params.bottomMargin;

        textView.setLayoutParams(params);
        textView.setText(R.string.app_data);
        textView.invalidate();
        return textView;

    }

    public View addButtontoLinearLayout(LinearLayout buttonLayout, int margin, int type){

        Button button = new Button(this);
        buttonLayout.addView(button);
        button.setGravity(Gravity.CENTER);
        button.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams paramsButton = (LinearLayout.LayoutParams) button.getLayoutParams();
        final Intent i;

        //params1.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        //params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        paramsButton.weight = 0.3f;
        paramsButton.setMargins(margin, margin, margin, margin);
        final MainActivity mainA = this;

        switch(type) {
            case 1:
                button.setText(R.string.button_data);
                i = new Intent(mainA, Main2Activity.class);
                break;
            default:
                button.setText(R.string.button1_data);
                i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                break;
        }

        button.setLayoutParams(paramsButton);
        button.invalidate();

        button.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                   Intent intent = i;
                    startActivity(intent);
                }
            });

        return button;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout grid = (GridLayout) findViewById(R.id.mainGrid);
        int margin = getResources().getDimensionPixelSize(R.dimen.item_margin);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                addDataItemToGrid(grid, margin);
            }
        }

        LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        for (int i = 0; i < 3; i++) {
            addButtontoLinearLayout(buttonLayout, margin, i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;

    }


}
