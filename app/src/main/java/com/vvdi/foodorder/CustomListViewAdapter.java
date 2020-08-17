package com.vvdi.foodorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListViewAdapter<String> extends ArrayAdapter<String>
{
    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<String> valuesStatus;

    public CustomListViewAdapter(Context context, ArrayList<String> values,  ArrayList<String> valuesStatus)
    {
        super(context, R.layout.custom_item, values);
        this.context = context;
        this.values = values;
        this.valuesStatus = valuesStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textOrderInfo);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.orderImageType);
        String type = (String) values.get(position).toString().substring(0,2);


        if(type.equals("bg")) {
        imageView.setImageResource(R.drawable.hamburger);
    }
    else if(type.equals("pz")) {
        imageView.setImageResource(R.drawable.pizza);
    }
        textView.setText(values.get(position).toString());

        TextView textViewSt = (TextView) rowView.findViewById(R.id.textOrderStatus);
        textViewSt.setText(valuesStatus.get(position).toString());
        return rowView;
    }

    public ArrayList<String> getData() { return values; }

    public ArrayList<String> getDataStatus() { return valuesStatus; }
}
