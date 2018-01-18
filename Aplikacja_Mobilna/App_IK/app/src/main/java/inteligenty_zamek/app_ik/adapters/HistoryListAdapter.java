package inteligenty_zamek.app_ik.adapters;

/**
 * Created by Damian on 23.12.2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.Views.History_using_keyActivity;
import inteligenty_zamek.app_ik.models.HistoryListElement;


public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder> {


    private ArrayList<HistoryListElement> historyList;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ico,title,row1,row2;
        public MyViewHolder(View view) {
            super(view);
            ico = (TextView) view.findViewById(R.id.historyIcon);
            title = (TextView) view.findViewById(R.id.historyTitle);
            row1= (TextView) view.findViewById(R.id.history1Row);
            row2= (TextView) view.findViewById(R.id.history2Row);
        }
    }


    public HistoryListAdapter(ArrayList list,Context context) {
        this.historyList = list;
this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_using_key_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        HistoryListElement item = historyList.get(position);
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/fontawesome.ttf");
        holder.ico.setTypeface(typeface);
        holder.ico.setText(item.getIco());

        if (item.getIco() == "\uf00c") {
            holder.ico.setTextColor(ContextCompat.getColor(((History_using_keyActivity) context), R.color.acceptColor));

        } else
        {
            holder.ico.setTextColor(ContextCompat.getColor(((History_using_keyActivity) context), R.color.errorColor));

        }
        holder.title.setText(item.getTitle());
        holder.row1.setText(item.getText()+": "+item.getName()+" "+item.getSurname());
        holder.row2.setText("Dla zamka: "+item.getKey()+"\ndata zdarzenia: "+
        item.getDate()+" "+item.getDateHour()
        );
    }

       @Override
        public int getItemCount () {
            return historyList.size();
        }
    }



