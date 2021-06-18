package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.example.quizapp.databinding.RowLeaderboardsBinding;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewHolder> {

    Context context;
    ArrayList<User> users;

    public LeaderboardsAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboards, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        User user = users.get(position);

        holder.name.setText(user.getName());
        holder.coins.setText(String.valueOf(user.getCoins()));
        holder.index.setText(String.format("#%d", position+1));

        Glide.with(context)
                .load(user.getProfile()).placeholder(R.drawable.avatar)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

//        RowLeaderboardsBinding binding;
        ImageView imageView;
        TextView index;
        TextView name;
        TextView coins;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView7);
            index=itemView.findViewById(R.id.index);
            name=itemView.findViewById(R.id.name);
            coins=itemView.findViewById(R.id.coins);
        }
    }
}
