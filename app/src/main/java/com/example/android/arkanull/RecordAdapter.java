package com.example.android.arkanull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends ArrayAdapter {
    LayoutInflater mInflater;
    public RecordAdapter(Context context, List<Record> objects)  {
        super(context, R.layout.record_item, objects);
        /* We get the inflator in the constructor */
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        /* We inflate the xml which gives us a view */
        view = mInflater.inflate(R.layout.record_item, parent, false);

        /* Get the item in the adapter */
        Record myObject = (Record) getItem(position);

        /* Get the widget with id name which is defined in the xml of the row */
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView score = (TextView) view.findViewById(R.id.score);
        TextView number = (TextView) view.findViewById(R.id.position);

        /* Populate the row's xml with info from the item */

        name.setText(myObject.getDisplayName());
        score.setText(String.valueOf(myObject.getScore()));
        number.setText(String.valueOf(position+1));


        /* Return the generated view */
        return view;
    }


}
