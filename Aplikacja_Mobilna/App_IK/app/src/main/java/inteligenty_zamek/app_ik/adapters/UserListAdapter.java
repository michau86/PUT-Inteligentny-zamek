package inteligenty_zamek.app_ik.adapters;

/**
 * Created by Damian on 23.12.2017.
 */
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.Views.MainActivity;
import inteligenty_zamek.app_ik.inWork.UserListActivity;
import inteligenty_zamek.app_ik.rest_class.Certyficat;
import inteligenty_zamek.app_ik.rest_class.User;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {


    private List<User> itemList;
    private Object context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,place,ico;
        public RecyclerView linearClick;



        public MyViewHolder(View view) {
            super(view);
            place  = (TextView) view.findViewById(R.id.TextView_listPlaceKey);
            name = (TextView) view.findViewById(R.id.TextView_listNameKey);
            ico = (TextView) view.findViewById(R.id.icoKey);
            linearClick= (RecyclerView) view.findViewById(R.id.userListRecyclerViewUserCertyfication);
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
        Log.i("HHH getview ",listKey.getLogin());
        holder.name.setText(listKey.getLogin());
        holder.place.setText(listKey.getName());


        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/fontawesome.ttf");
        holder.ico.setTypeface(typeface);
        holder.ico.setText("\uf007");
        holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.acceptColor));

        if(!listKey.isActive)
        {
            holder.name.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setTextColor(ContextCompat.getColor(((UserListActivity) context), R.color.deactivationtColor));
            holder.ico.setText("\uf235");
        }

      /*  holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((UserListActivity) context).listClick(position);

            }
        });*/

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