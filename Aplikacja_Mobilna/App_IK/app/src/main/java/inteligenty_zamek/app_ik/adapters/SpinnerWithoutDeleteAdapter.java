package inteligenty_zamek.app_ik.adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import inteligenty_zamek.app_ik.R;

/**
 * Created by Damian on 29.12.2017.
 */

public class SpinnerWithoutDeleteAdapter  extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> iName;

        TextView spnItemName;

        public  SpinnerWithoutDeleteAdapter (Context context, int textViewResourceId, ArrayList<String> objects, ArrayList<String> iName){
            super(context,textViewResourceId,objects);
            this.context = context;
            this.iName = iName;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View rowView =  inflater.inflate(R.layout.spinner_row2, parent, false);

            spnItemName = (TextView) rowView.findViewById(R.id.spnItemName);

            spnItemName.setText(iName.get(position));
            return rowView;
        }
    }

