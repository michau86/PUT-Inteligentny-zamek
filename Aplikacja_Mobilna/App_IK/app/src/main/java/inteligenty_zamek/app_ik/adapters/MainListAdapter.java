package inteligenty_zamek.app_ik.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.models.MainModel;
import inteligenty_zamek.app_ik.rest_class.Certyficat;

/**
 * Created by Damian on 07.11.2017.
 */

public class MainListAdapter  extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    MainModel model;

    public MainListAdapter(Context applicationContext, MainModel model) {
        this.context = applicationContext;
        this.model = model;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        return model.getKeys().size();
    }

    @Override
    public Object getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.main_key_list, null);
        TextView icon = (TextView) view.findViewById(R.id.icoKey);
        TextView Name = (TextView) view.findViewById(R.id.TextView_listNameKey);
        TextView describe = (TextView) view.findViewById(R.id.TextView_listPlaceKey);
        try {

            Certyficat cert = model.getKeys().get(i);

            //ustawianie teksut
            Name.setText(cert.getLockName());
            describe.setText( cert.getLockLocalization());

            //ustawianie koloru ikon
            switch (cert.status)
            {
              case 0: {
                  icon.setText("aa");
                  icon.setTextColor(Color.BLUE);
                  break;
                      }
              case 1: {
                  icon.setText("bb");
                  icon.setTextColor(Color.RED);
                      }
                case 3:{
                    icon.setText("cc");
                    icon.setTextColor(Color.GREEN);
                }
            }
          }catch(Exception e){}

        return view;
    }

}
