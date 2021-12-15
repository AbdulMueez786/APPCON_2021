package com.armughanaslam.appcon_2021.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armughanaslam.appcon_2021.Activity.GoalList;
import com.armughanaslam.appcon_2021.Activity.Home;
import com.armughanaslam.appcon_2021.Activity.NewGoal;
import com.armughanaslam.appcon_2021.Adapters.GoalsAdapter;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private View view;

    //private TextView name;
    //private TextView description;

    private List<goal> goals;
    private RecyclerView recyclerViewGoals;
    private GoalsAdapter goalsAdapter;
    private static final int request_code_1 = 2;
    private Button start_new;
    private ImageButton top_donors,back_arrow,medical_funds,education_funds,disaster_funds,other_funds;
    private com.google.android.material.textfield.TextInputEditText list_search;
    //public TextView getDescription() {
    //    return description;
   // }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);


        back_arrow=view.findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home)getActivity()).logout();
            }
        });

        top_donors=view.findViewById(R.id.top_donors);
        top_donors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home)getActivity()).navigate_topDonors();
            }
        });
        list_search=view.findViewById(R.id.list_search);
        list_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GoalList.class);
                ((Home)getActivity()).startActivity(intent);
            }
        });

        start_new=view.findViewById(R.id.start_new);
        start_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewGoal.class);
                ((Home)getActivity()).startActivity(intent);
            }
        });

        medical_funds=view.findViewById(R.id.medical_funds);

        medical_funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(getContext(), NewGoal.class);
              //  ((Home)getActivity()).startActivity(intent);
                medical_funds();
            }
        });

       education_funds=view.findViewById(R.id.education_funds);
       education_funds.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               education_funds();
              // Intent intent = new Intent(getContext(), NewGoal.class);
              // ((Home)getActivity()).startActivity(intent);
           }
       });

        disaster_funds=view.findViewById(R.id.disaster_funds);
        disaster_funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disaster_funds();
                //Intent intent = new Intent(getContext(), NewGoal.class);
                // ((Home)getActivity()).startActivity(intent);
            }
        });

       other_funds=view.findViewById(R.id.other_funds);
       other_funds.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Others_funds();
               //Intent intent = new Intent(getContext(), NewGoal.class);
              // ((Home)getActivity()).startActivity(intent);
           }
       });

        recyclerViewGoals = view.findViewById(R.id.recyclerviewGoals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true);
        recyclerViewGoals.setLayoutManager(layoutManager);


        goals = new ArrayList<goal>();
        //String goalHeaderUriString = "android.resource://com.armughanaslam.bottomdrawer/drawable/water_donation";
                //goals.add(new goal());
        goalsAdapter = new GoalsAdapter(goals, getContext());
        recyclerViewGoals.setAdapter(goalsAdapter);

        // fetching the goal which are active
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    goal g=snapshot.getValue(goal.class);
                    goals.add(g);
                }
                System.out.println(goals);
                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
    void Others_funds(){
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                goals.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    goal g=snapshot.getValue(goal.class);
                    if(g.getCategroy().matches("Others")){
                        goals.add(g);
                    }

                }
                System.out.println(goals);
                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    void medical_funds(){
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                goals.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    goal g=snapshot.getValue(goal.class);
                    if(g.getCategroy().matches("Medical")){
                        goals.add(g);
                    }

                }
                System.out.println(goals);
                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    void education_funds(){
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                goals.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    goal g=snapshot.getValue(goal.class);
                    if(g.getCategroy().matches("Education")){
                        goals.add(g);
                    }

                }
                System.out.println(goals);
                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    void Disaster_funds(){
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                goals.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    goal g=snapshot.getValue(goal.class);
                    if(g.getCategroy().matches("Disaster")){
                        goals.add(g);
                    }

                }
                System.out.println(goals);
                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}