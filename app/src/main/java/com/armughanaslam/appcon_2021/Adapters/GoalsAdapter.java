package com.armughanaslam.appcon_2021.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//import com.bumptech.glide.Glide;

import com.armughanaslam.appcon_2021.Activity.GoalDetail;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;



public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.MyViewHolder> {
    static List<goal> goals;
    Context context;
    public GoalsAdapter(List<goal> goalList , Context context) {
        this.context= context;
        this.goals=goalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.goal_row,parent,false);
        return new MyViewHolder(itemRow);
    }


    @Override
    public void onBindViewHolder(@NonNull final GoalsAdapter.MyViewHolder holder, final int position) {

        goal g=goals.get(position);
        final String[] creater_name = new String[1];
        holder.goal_name.setText(goals.get(position).getTitle());
        holder.description.setText(goals.get(position).getDescription());
        System.out.println(holder.goal_name);

        FirebaseDatabase.getInstance().getReference("users").child(g.getCreater_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                creater_name[0] =snapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        System.out.println("-------------------------------------------");

        holder.goal_details_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (context, GoalDetail.class);
                intent.putExtra("goal_id",g.getId());
                intent.putExtra("goal_status",g.getStatus());
                intent.putExtra("goal_title",g.getTitle());

                intent.putExtra("goal_image",g.getImage());
                intent.putExtra("creater_id",g.getCreater_id());
                intent.putExtra("goal_description",g.getDescription());
                intent.putExtra("goal_creater_name",creater_name[0]);
                intent.putExtra("raised_amount",g.getRaised_Amount());
                intent.putExtra("target_amount",g.getTarget_Amount());
                intent.putExtra("No_of_participants",g.getNo_of_participants());
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(goals.get(position).getImage()).into(holder.goalHeader);


    }



    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView goal_name,description;
        ImageView goalHeader;
        ImageButton goal_details_nav;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goal_name = itemView.findViewById(R.id.goal_name);
            description = itemView.findViewById(R.id.goal_description);
            goalHeader = (ImageView)itemView.findViewById(R.id.goal_header);
            goal_details_nav = (ImageButton)itemView.findViewById(R.id.goal_detail_nav);
            linearLayout = itemView.findViewById(R.id.llgoal);


        }
    }

}

