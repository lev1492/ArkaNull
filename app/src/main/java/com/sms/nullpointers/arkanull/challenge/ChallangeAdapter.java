package com.sms.nullpointers.arkanull.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.challenge.Challange;

import java.util.List;

public class ChallangeAdapter extends ArrayAdapter {
    LayoutInflater mInflater;
    public ChallangeAdapter(Context context, List<Challange> objects )  {
        super(context, R.layout.challange_item, objects);
        /* We get the inflator in the constructor */
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        /* We inflate the xml which gives us a view */
        view = mInflater.inflate(R.layout.challange_item, parent, false);

        /* Get the item in the adapter */
        Challange myObject = (Challange) getItem(position);

        /* Get the widget with id name which is defined in the xml of the row */
        TextView name = (TextView) view.findViewById(R.id.name1);
        TextView score = (TextView) view.findViewById(R.id.score1);
        TextView name2 = (TextView) view.findViewById(R.id.name2);
        TextView score2 = (TextView) view.findViewById(R.id.score2);
        TextView number = (TextView) view.findViewById(R.id.position);

        /* Populate the row's xml with info from the item */

        name.setText(myObject.getNamePlayer1());
        score.setText(myObject.getScorePlayer1());
        name2.setText(myObject.getNamePlayer2());
        score2.setText(myObject.getScorePlaer2());
        number.setText(String.valueOf(position+1));

        /* Return the generated view */
        return view;
    }
}
