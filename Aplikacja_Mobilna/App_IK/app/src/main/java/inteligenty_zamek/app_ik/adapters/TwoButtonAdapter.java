package inteligenty_zamek.app_ik.adapters;

/**
 * Created by Damian on 23.12.2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import inteligenty_zamek.app_ik.R;



public class TwoButtonAdapter extends RecyclerView.Adapter<TwoButtonAdapter.MyViewHolder> {


        private List<userWatModel> moviesList;
        private Object context;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name,place;
            public Button aceptButton;
            public Button deleteButton;


            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.listTitle);
                place = (TextView) view.findViewById(R.id.listDescribe);
                aceptButton=(Button) view.findViewById(R.id.listAcceptButton);
                deleteButton=(Button) view.findViewById(R.id.listDeleteButton);
            }
        }


        public TwoButtonAdapter(List<userWatModel> moviesList, Object context) {
            this.moviesList = moviesList;
            this.context =context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_wait_key_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            userWatModel movie = moviesList.get(position);
            holder.name.setText(movie.getTitle());
            holder.place.setText(movie.getDescribe());
            holder.aceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof ITwoButtonList){
                        ((ITwoButtonList) context).acceptButton(position);
                    }
                }
            });


            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof ITwoButtonList){
                        ((ITwoButtonList) context).removeButton(position);
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

    public void removeAt(int position) {
        moviesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, moviesList.size());
    }



    }