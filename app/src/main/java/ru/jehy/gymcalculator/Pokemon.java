package ru.jehy.gymcalculator;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jehy on 29.07.2016.
 */
public class Pokemon {
    @SerializedName("name")
    String name;
    @SerializedName("types")
    String[] types;
}
