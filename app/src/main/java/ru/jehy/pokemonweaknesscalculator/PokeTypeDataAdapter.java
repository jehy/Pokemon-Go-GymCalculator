package ru.jehy.pokemonweaknesscalculator;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Jehy on 28.07.2016.
 */
public class PokeTypeDataAdapter extends BaseAdapter {
    Context mContext;
    private String[] type;
    private double[] damage;
    private LayoutInflater mInflater;

    public PokeTypeDataAdapter(Context c, String[] type, double[] damage) {
        mContext = c;
        mInflater = LayoutInflater.from(c);
        this.type = type.clone();
        this.damage = damage.clone();
        Log.d("PokeLog", "types:" + Arrays.toString(type));
        Log.d("PokeLog", "damage:" + Arrays.toString(damage));
    }

    public int getCount() {
        return type.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        convertView = null;
        convertView = mInflater.inflate(R.layout.customgrid,
                parent, false);
        holder = new ViewHolder();
        holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
        holder.txtType.setPadding(100, 10, 10, 10);
        holder.txtDamage = (TextView) convertView.findViewById(R.id.txtDamage);
        holder.txtDamage.setPadding(100, 10, 10, 10);
        if (position == 0) {
            convertView.setTag(holder);
        }

        Log.d("PokeLog", String.valueOf(position));
        holder.txtType.setText(type[position]);
        holder.txtDamage.setText(String.valueOf(damage[position]));
        if (damage[position] < 1)
            holder.txtDamage.setBackgroundColor(Color.RED); // set default RED color as background color
        if (damage[position] > 1)
            holder.txtDamage.setBackgroundColor(Color.GREEN); // set default RED color as background color

        return convertView;

        /*
        // create a new LayoutInflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        gridView = null;
        convertView = null;// avoids recycling of grid view
        if (convertView == null) {

            gridView = new View(mContext);
            // inflating grid view item
            gridView = inflater.inflate(R.layout.customgrid, null);

            // set value into textview
            TextView textType = (TextView) gridView
                    .findViewById(R.id.txtType);
            TextView textDamage = (TextView) gridView
                    .findViewById(R.id.txtDamage);
            textType.setText(type[position]);
            textDamage.setText(String.valueOf(damage[position]));

        }

        return gridView;*/
    }

    class ViewHolder {
        TextView txtType;
        TextView txtDamage;
    }
}
