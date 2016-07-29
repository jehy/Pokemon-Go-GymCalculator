package ru.jehy.pokemonweaknesscalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double[][] pokeMatrix = {{1, 1, 1, 1, 1, 0.8, 1, 0.8, 0.8, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1.25, 1, 0.8, 0.8, 1, 1.25, 0.8, 0.8, 1.25, 1, 1, 1, 1, 0.8, 1.25, 1, 1.25, 0.8},
            {1, 1.25, 1, 1, 1, 0.8, 1.25, 1, 0.8, 1, 1, 1.25, 0.8, 1, 1, 1, 1, 1},
            {1, 1, 1, 0.8, 0.8, 0.8, 1, 0.8, 0.8, 1, 1, 1.25, 1, 1, 1, 1, 1, 1.25},
            {1, 1, 0.8, 1.25, 1, 1.25, 0.8, 1, 1.25, 1.25, 1, 0.8, 1.25, 1, 1, 1, 1, 1},
            {1, 0.8, 1.25, 1, 0.8, 1, 1.25, 1, 0.8, 1.25, 1, 1, 1, 1, 1.25, 1, 1, 1},
            {1, 0.8, 0.8, 0.8, 1, 1, 1, 0.8, 0.8, 0.8, 1, 1.25, 1, 1.25, 1, 1, 1.25, 0.8},
            {0.8, 1, 1, 1, 1, 1, 1, 1.25, 1, 1, 1, 1, 1, 1.25, 1, 1, 0.8, 1},
            {1, 1, 1, 1, 1, 1.25, 1, 1, 0.8, 0.8, 0.8, 1, 0.8, 1, 1.25, 1, 1, 1.25},
            {1, 1, 1, 1, 1, 0.8, 1.25, 1, 1.25, 0.8, 0.8, 1.25, 1, 1, 1.25, 0.8, 1, 1},
            {1, 1, 1, 1, 1.25, 1.25, 1, 1, 1, 1.25, 0.8, 0.8, 1, 1, 1, 0.8, 1, 1},
            {1, 1, 0.8, 0.8, 1.25, 1.25, 0.8, 1, 0.8, 0.8, 1.25, 0.8, 1, 1, 1, 0.8, 1, 1},
            {1, 1, 1.25, 1, 0.8, 1, 1, 1, 1, 1, 1.25, 0.8, 0.8, 1, 1, 0.8, 1, 1},
            {1, 1.25, 1, 1.25, 1, 1, 1, 1, 0.8, 1, 1, 1, 1, 0.8, 1, 1, 0.8, 1},
            {1, 1, 1.25, 1, 1.25, 1, 1, 1, 0.8, 0.8, 0.8, 1.25, 1, 1, 0.8, 1.25, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0.8, 1, 1, 1, 1, 1, 1, 1.25, 1, 0.8},
            {1, 0.8, 1, 1, 1, 1, 1, 1.25, 1, 1, 1, 1, 1, 1.25, 1, 1, 0.8, 0.8},
            {1, 1.25, 1, 0.8, 1, 1, 1, 1, 0.8, 0.8, 1, 1, 1, 1, 1, 1.25, 1.25, 1}};
    double[][] pokeMatrixTransposed = trasposeMatrix(pokeMatrix);
    String[] pokeTypes = {"NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND", "ROCK", "BUG", "GHOST",
            "STEEL", "FIRE", "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE", "DRAGON", "DARK", "FAIRY"};
    Pokemon[] pokemons = null;

    public static double[][] trasposeMatrix(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] trasposedMatrix = new double[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                trasposedMatrix[x][y] = matrix[y][x];
            }
        }

        return trasposedMatrix;
    }

    private void updatePokeList() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.pokemons);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String jsonString = writer.toString();
        Gson gson = new Gson();
        pokemons = gson.fromJson(jsonString, Pokemon[].class);
        List<String> pokeList = new ArrayList<String>();
        for (Pokemon pokemon : pokemons) {
            //Log.d("PokeLog", pokemons[i].name);
            //Log.d("PokeLog", Arrays.toString(pokemons[i].types));
            pokeList.add(pokemon.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, pokeTypes);
        s.setAdapter(adapter);
        s = (Spinner) findViewById(R.id.defenderType2);
        s.setAdapter(adapter);

        //init autosuggest
        try {
            updatePokeList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Button buttonOne = (Button) findViewById(R.id.computeAttacker);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Spinner mySpinner = (Spinner) findViewById(R.id.defenderType1);
                int type = (int) mySpinner.getSelectedItemId();
                double[] damage = pokeMatrixTransposed[type].clone();
                GridView grid = (GridView) findViewById(R.id.damageTable);


                CheckBox secType = (CheckBox) findViewById(R.id.checkBox);
                if ((secType).isChecked()) {
                    Spinner mySpinner2 = (Spinner) findViewById(R.id.defenderType2);
                    int type2 = (int) mySpinner2.getSelectedItemId();
                    double[] damage2 = pokeMatrixTransposed[type2].clone();
                    for (int i = 0; i < damage.length; i++) {
                        damage[i] = Math.rint(100.0 * damage[i] * damage2[i]) / 100.0;
                    }
                }
                grid.setAdapter(new PokeTypeDataAdapter(v.getContext(), pokeTypes, damage));
            }
        });

        Button getTypeBtn = (Button) findViewById(R.id.getType);
        getTypeBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                AutoCompleteTextView pokeName = (AutoCompleteTextView) findViewById(R.id.pokemonName);
                String name = pokeName.getText().toString().toLowerCase();
                for (Pokemon pokemon : pokemons) {
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
                /*
                Spinner mySpinner = (Spinner) findViewById(R.id.defenderType1);
                int type = (int) mySpinner.getSelectedItemId();
                double[] damage = pokeMatrixTransposed[type];
                GridView grid = (GridView) findViewById(R.id.damageTable);


                CheckBox secType = (CheckBox) findViewById(R.id.checkBox);
                if ((secType).isChecked()) {
                    Spinner mySpinner2 = (Spinner) findViewById(R.id.defenderType2);
                    int type2 = (int) mySpinner2.getSelectedItemId();
                    double[] damage2 = pokeMatrixTransposed[type2];
                    for (int i = 0; i < damage.length; i++) {
                        damage[i] = Math.rint(100.0 * damage[i] * damage2[i]) / 100.0;
                    }
                }
                grid.setAdapter(new PokeTypeDataAdapter(v.getContext(), pokeTypes, damage));*/
            }
        });


        CheckBox secType = (CheckBox) findViewById(R.id.checkBox);

        secType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
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
        });
    }
}
