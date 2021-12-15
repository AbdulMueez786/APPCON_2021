package com.armughanaslam.appcon_2021.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.armughanaslam.appcon_2021.Activity.GoalDetail;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyGoals_adapter  extends RecyclerView.Adapter<MyGoals_adapter.MyViewHolder> implements Filterable {
    List<goal> report_ls;
    private List<goal> report_ls_copy;
    Context c;

    public MyGoals_adapter(List<goal> ls, Context c) {
        this.c = c;
        this.report_ls = ls;
        this.report_ls_copy = new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.goal_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        goal g=report_ls.get(position);
        final String[] creater_name = new String[1];
        holder.goal_name.setText(report_ls.get(position).getTitle());
        holder.description.setText(report_ls.get(position).getDescription());
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

                Intent intent = new Intent (c, GoalDetail.class);
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
                c.startActivity(intent);
            }
        });
        Picasso.with(c).load(report_ls.get(position).getImage()).into(holder.goalHeader);

    }

    @Override
    public int getItemCount() {
        return report_ls.size();
    }

    public void addlist() {
        report_ls_copy = new ArrayList<goal>(report_ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<goal> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh" + constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(report_ls_copy);
                System.out.println("Working_______________");
                System.out.println(report_ls_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (goal item : report_ls_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            report_ls.clear();
            System.out.println("Result");
            report_ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


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


