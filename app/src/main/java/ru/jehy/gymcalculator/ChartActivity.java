package ru.jehy.gymcalculator;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                ChartActivity.this.finish();
            }
        });


        //get background color of theme
        TypedValue typedValue = new TypedValue();
        final Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.background, typedValue, true);
        final int backgroundColor = typedValue.data;


        final ScrollView sv = new ScrollView(this);
        TableLayout ll = new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        //make header
        TableRow tbrow = new TableRow(this);
        TextView tv1 = new TextView(this);
        tv1.setText(R.string.atk_def);
        tv1.setPadding(50, 10, 10, 10);
        tbrow.addView(tv1);
        for (int i = 0; i < pokeData.pokeTypes.length; i++) {
            tv1 = new TextView(this);
            tv1.setText(pokeData.pokeTypes[i]);
            tv1.setPadding(50, 10, 10, 10);
            tbrow.addView(tv1);
        }
        ll.addView(tbrow);

        //fill content
        for (int i = 0; i < pokeData.pokeTypes.length; i++) {
            tbrow = new TableRow(this);
            tv1 = new TextView(this);
            tv1.setText(pokeData.pokeTypes[i]);
            tv1.setPadding(50, 10, 10, 10);
            tbrow.addView(tv1);
            for (int j = 0; j < pokeData.pokeTypes.length; j++) {
                tv1 = new TextView(this);
                tv1.setText(String.valueOf(pokeData.pokeMatrix[i][j]));
                tv1.setPadding(50, 10, 10, 10);
                /*
                if (pokeData.pokeMatrix[i][j] > 1)
                    tv1.setBackgroundColor(Color.GREEN);
                else if (pokeData.pokeMatrix[i][j] < 1)
                    tv1.setBackgroundColor(Color.RED);*/
                if (pokeData.pokeMatrix[i][j] < 0.7)
                    tv1.setBackgroundColor(Color.rgb(176, 0, 0));
                else if (pokeData.pokeMatrix[i][j] < 1)
                    tv1.setBackgroundColor(Color.rgb(255, 36, 0));
                else if (pokeData.pokeMatrix[i][j] > 1.4)
                    tv1.setBackgroundColor(Color.rgb(0, 255, 0));
                else if (pokeData.pokeMatrix[i][j] > 1)
                    tv1.setBackgroundColor(Color.rgb(167, 252, 0));

                tbrow.addView(tv1);
            }
            tbrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null && (int) v.getTag() == Color.BLUE) {
                        v.setBackgroundColor(backgroundColor);
                        v.setTag(backgroundColor);
                    } else {
                        v.setBackgroundColor(Color.BLUE);
                        v.setTag(Color.BLUE);
                    }
                }
            });
            ll.addView(tbrow);
        }
        hsv.addView(ll);
        sv.addView(hsv);
        Toolbar.LayoutParams l = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.include);
        rootLayout.addView(sv, l);
    }

}
