package inteligenty_zamek.app_ik.adapters;

/**
 * Created by Damian on 23.12.2017.
 */
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.Views.UserListActivity;
import inteligenty_zamek.app_ik.models.User;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder>  {


    private List<User> itemList;
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


    public UserListAdapter(List<User> list, Object context) {
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
        User listKey = itemList.get(position);
        Log.i("HHH getview",listKey.getLogin());
        Log.i("hhh",listKey.validitiy_period);
        holder.name.setText(listKey.getLogin());
        holder.place.setText(listKey.getName());


        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/fontawesome.ttf");
        holder.ico.setTypeface(typeface);
        holder.ico.setText("\uf21b");
        holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.acceptColor));

        Calendar myCalendar = Calendar.getInstance();

        String myFormat = "yyyy-MM-dd hh:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);


        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(myCalendar.getTime()));
            Log.i("hhh",date1.toString());
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(listKey.validitiy_period.replace("T", " "));
            Log.i("hhh",date2.toString());
            Log.i("hhh", Integer.toString(date1.compareTo(date2)));
            if (date1.compareTo(date2) > 0) {
                holder.name.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
                holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
                holder.ico.setText("\uf007");
            }
        }catch (Exception ex){
            holder.name.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setText("\uf007");
        }

        if(!listKey.isActive)
        {
            holder.name.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setText("\uf235");
        }

        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((UserListActivity) context).listClick(position);
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