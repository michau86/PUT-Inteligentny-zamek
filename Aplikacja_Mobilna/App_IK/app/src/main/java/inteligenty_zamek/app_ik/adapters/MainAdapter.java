package inteligenty_zamek.app_ik.adapters;

/**
 * Created by Damian on 23.12.2017.
 */
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.Views.MainActivity;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {


    private List<MainListModel> itemList;
    private Object context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,place,ico;
        public LinearLayout linearClick;



        public MyViewHolder(View view) {
            super(view);
            place  = (TextView) view.findViewById(R.id.TextView_listPlaceKey);

            name = (TextView) view.findViewById(R.id.TextView_listNameKey);
            ico = (TextView) view.findViewById(R.id.icoKey);
            linearClick= (LinearLayout) view.findViewById(R.id.mainListLinearLayout);
        }
    }


    public MainAdapter(List<MainListModel> list, Object context) {
        this.itemList = list;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_key_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MainListModel listKey = itemList.get(position);
        holder.name.setText(listKey.getTextView_listNameKey());
        holder.place.setText(listKey.getTextView_listPlaceKey());
        holder.ico.setText(listKey.getIcoKey());


        try {
            //ustawianie koloru ikon
            switch (listKey.getIcoColor())
            {
                case 0: {

                    holder.ico.setTextColor(Color.WHITE);
                    break;
                }

                case 1:{
                    holder.ico.setTextColor(ContextCompat.getColor(((MainActivity) context), R.color.acceptColor));
                    break;
                }
                case 2: {

                    holder.ico.setTextColor(ContextCompat.getColor(((MainActivity) context), R.color.errorColor));
                    break;
                }

                case 3:
                {
                    holder.ico.setTextColor(ContextCompat.getColor(((MainActivity) context), R.color.waitingColor));
                    break;
                }
            }



        }catch(Exception e){}
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/fontawesome.ttf");
        holder.ico.setTypeface(typeface);


        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Byte x=3;
                    ((MainActivity) context).listClick(position,x);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeAt(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }



}