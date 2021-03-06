package ru.jehy.gymcalculator;

import android.graphics.Color;

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

/**
 * Created by Jehy on 11.10.2016.
 */

public class pokeData {
    static Pokemon[] pokemons = null;

    public static double[][] pokeMatrix =
            {
                    {1, 1, 1, 1, 1, 0.625, 1, 0.390625, 0.625, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1.6, 1, 0.625, 0.625, 1, 1.6, 0.625, 0.390625, 1.6, 1, 1, 1, 1, 0.625, 1.6, 1, 1.6, 0.625},
                    {1, 1.6, 1, 1, 1, 0.625, 1.6, 1, 0.625, 1, 1, 1.6, 0.625, 1, 1, 1, 1, 1},
                    {1, 1, 1, 0.625, 0.625, 0.625, 1, 0.625, 0.390625, 1, 1, 1.6, 1, 1, 1, 1, 1, 1.6},
                    {1, 1, 0.390625, 1.6, 1, 1.6, 0.625, 1, 1.6, 1.6, 1, 0.625, 1.6, 1, 1, 1, 1, 1},
                    {1, 0.625, 1.6, 1, 0.625, 1, 1.6, 1, 0.625, 1.6, 1, 1, 1, 1, 1.6, 1, 1, 1},
                    {1, 0.625, 0.625, 0.625, 1, 1, 1, 0.625, 0.625, 0.625, 1, 1.6, 1, 1.6, 1, 1, 1.6, 0.625},
                    {0.390625, 1, 1, 1, 1, 1, 1, 1.6, 1, 1, 1, 1, 1, 1.6, 1, 1, 0.625, 1},
                    {1, 1, 1, 1, 1, 1.6, 1, 1, 0.625, 0.625, 0.625, 1, 0.625, 1, 1.6, 1, 1, 1.6},
                    {1, 1, 1, 1, 1, 0.625, 1.6, 1, 1.6, 0.625, 0.625, 1.6, 1, 1, 1.6, 0.625, 1, 1},
                    {1, 1, 1, 1, 1.6, 1.6, 1, 1, 1, 1.6, 0.625, 0.625, 1, 1, 1, 0.625, 1, 1},
                    {1, 1, 0.625, 0.625, 1.6, 1.6, 0.625, 1, 0.625, 0.625, 1.6, 0.625, 1, 1, 1, 0.625, 1, 1},
                    {1, 1, 1.6, 1, 0.390625, 1, 1, 1, 1, 1, 1.6, 0.625, 0.625, 1, 1, 0.625, 1, 1},
                    {1, 1.6, 1, 1.6, 1, 1, 1, 1, 0.625, 1, 1, 1, 1, 0.625, 1, 1, 0.390625, 1},
                    {1, 1, 1.6, 1, 1.6, 1, 1, 1, 0.625, 0.625, 0.625, 1.6, 1, 1, 0.625, 1.6, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 0.625, 1, 1, 1, 1, 1, 1, 1.6, 1, 0.390625},
                    {1, 0.625, 1, 1, 1, 1, 1, 1.6, 1, 1, 1, 1, 1, 1.6, 1, 1, 0.625, 0.625},
                    {1, 1.6, 1, 0.625, 1, 1, 1, 1, 0.625, 0.625, 1, 1, 1, 1, 1, 1.6, 1.6, 1}
            };
    public static double[][] pokeMatrixTransposed = trasposeMatrix(pokeMatrix);
    public static String[] pokeTypes = {"NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND", "ROCK", "BUG", "GHOST",
            "STEEL", "FIRE", "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE", "DRAGON", "DARK", "FAIRY"};

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

    public static void loadPokemons(MainActivity activity) {
        InputStream is = activity.getResources().openRawResource(R.raw.pokemons);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonString = writer.toString();
        if (jsonString.length() < 1)
            return;
        Gson gson = new Gson();
        pokemons = gson.fromJson(jsonString, Pokemon[].class);
    }

    public static List<String> getPokemonsList(MainActivity activity) throws IOException {
        if (pokemons == null)
            loadPokemons(activity);
        if (pokemons == null)
            return new ArrayList<>();
        List<String> pokeList = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            pokeList.add(pokemon.name);
        }
        return pokeList;
    }

    public static int getColor(double damage)
    {
        if (damage < 0.6)
            return Color.rgb(176, 0, 0);
        if (damage < 1)
            return Color.rgb(255, 36, 0);
        if (damage >= 1.6)
            return Color.rgb(0, 255, 0);
        if (damage > 1)
            return Color.rgb(167, 252, 0);
        return Color.rgb(255, 255, 255);
    }
}
