package ru.jehy.gymcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    int tableViewID=0;

    private void updatePokeList() throws IOException {
        List<String> pokeList = pokeData.getPokemonsList(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, pokeList);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.pokemonName);
        textView.setAdapter(adapter);
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //set controls with types
        Spinner s = (Spinner) findViewById(R.id.defenderType1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, pokeData.pokeTypes);
        s.setAdapter(adapter);
        s = (Spinner) findViewById(R.id.defenderType2);
        s.setAdapter(adapter);

        //init autosuggest
        try {
            updatePokeList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ChartActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
        Button buttonOne = (Button) findViewById(R.id.computeAttacker);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Spinner mySpinner = (Spinner) findViewById(R.id.defenderType1);
                int type = (int) mySpinner.getSelectedItemId();
                double[] damage = pokeData.pokeMatrixTransposed[type].clone();
                //GridView grid = (GridView) findViewById(R.id.damageTable);


                CheckBox secType = (CheckBox) findViewById(R.id.checkBox);
                if ((secType).isChecked()) {
                    Spinner mySpinner2 = (Spinner) findViewById(R.id.defenderType2);
                    int type2 = (int) mySpinner2.getSelectedItemId();
                    double[] damage2 = pokeData.pokeMatrixTransposed[type2].clone();
                    for (int i = 0; i < damage.length; i++) {
                        damage[i] = Math.rint(100.0 * damage[i] * damage2[i]) / 100.0;
                    }
                }
                //grid.setAdapter(new PokeTypeDataAdapter(v.getContext(), pokeData.pokeTypes, damage));

                RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.mainRootView);
                if(tableViewID!=0)
                {
                    View oldDmgTable=findViewById(tableViewID);
                    if(oldDmgTable!=null)
                        rootLayout.removeView(oldDmgTable);

                }
                TableLayout dmgTable = new TableLayout(MainActivity.this);
                TableRow tbrow = new TableRow(MainActivity.this);
                TextView tv1 = new TextView(MainActivity.this);
                tv1.setText("Attack type");
                tv1.setPadding(100, 10, 10, 10);
                tbrow.addView(tv1);
                tv1 = new TextView(MainActivity.this);
                tv1.setText("Damage multiplier");
                tv1.setPadding(100, 10, 10, 10);
                tbrow.addView(tv1);

                for (int i = 0; i < pokeData.pokeTypes.length; i++) {
                    tbrow = new TableRow(MainActivity.this);
                    tv1 = new TextView(MainActivity.this);
                    tv1.setText(pokeData.pokeTypes[i]);
                    tv1.setPadding(100, 10, 10, 10);
                    tbrow.addView(tv1);
                    tv1 = new TextView(MainActivity.this);
                    tv1.setText(String.valueOf(damage[i]));
                    tv1.setPadding(100, 10, 10, 10);
                    if (damage[i] < 1)
                        tv1.setBackgroundColor(Color.RED); // set default RED color as background color
                    if (damage[i] > 1)
                        tv1.setBackgroundColor(Color.GREEN); // set default RED color as background color
                    tbrow.addView(tv1);
                    dmgTable.addView(tbrow);
                }


                RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                        Toolbar.LayoutParams.WRAP_CONTENT);

                l.addRule(RelativeLayout.BELOW, R.id.computeAttacker);


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                    tableViewID=Utils.generateViewId();
                 else
                    tableViewID=View.generateViewId();

                dmgTable.setId(tableViewID);
                rootLayout.addView(dmgTable, l);

            }}

            );

            Button getTypeBtn = (Button) findViewById(R.id.getType);
            getTypeBtn.setOnClickListener(new Button.OnClickListener()

            {
                public void onClick (View v){

                AutoCompleteTextView pokeName = (AutoCompleteTextView) findViewById(R.id.pokemonName);
                String name = pokeName.getText().toString().toLowerCase();
                for (Pokemon pokemon : pokeData.pokemons) {
                    if (pokemon.name.toLowerCase().equals(name)) {
                        Spinner mySpinner = (Spinner) findViewById(R.id.defenderType1);
                        mySpinner.setSelection(getIndex(mySpinner, pokemon.types[0].toUpperCase()));
                        CheckBox secType = (CheckBox) findViewById(R.id.checkBox);
                        if (pokemon.types.length == 2)//dual type pokemon
                        {
                            if (!secType.isChecked())
                                secType.performClick();
                            Spinner mySpinner2 = (Spinner) findViewById(R.id.defenderType2);
                            mySpinner2.setSelection(getIndex(mySpinner2, pokemon.types[1].toUpperCase()));
                        } else if (secType.isChecked())
                            secType.performClick();
                        return;
                    }
                }
                Toast.makeText(MainActivity.this, "Pokemon not found!",
                        Toast.LENGTH_LONG).show();
            }
            }

            );


            CheckBox secType = (CheckBox) findViewById(R.id.checkBox);

            secType.setOnClickListener(new View.OnClickListener()

            {

                @Override
                public void onClick (View v){
                if (((CheckBox) v).isChecked()) {
                    TextView t2 = (TextView) findViewById(R.id.textView4);
                    t2.setVisibility(View.VISIBLE);
                    Spinner s2 = (Spinner) findViewById(R.id.defenderType2);
                    s2.setVisibility(View.VISIBLE);
                } else {
                    TextView t2 = (TextView) findViewById(R.id.textView4);
                    t2.setVisibility(View.GONE);
                    Spinner s2 = (Spinner) findViewById(R.id.defenderType2);
                    s2.setVisibility(View.GONE);
                }

            }
            }

            );
        }
    }
