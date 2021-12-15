package com.armughanaslam.appcon_2021.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.armughanaslam.appcon_2021.Adapters.TopDonors_adapter;
import com.armughanaslam.appcon_2021.Model.participant;
import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DonorList extends AppCompatActivity {
    private ImageButton back_arrow;
    private RecyclerView rv;
    private List<user> ls;
    private TopDonors_adapter top_donors_adapter;
    private List<participant> p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ls = new ArrayList<>();
        p =new ArrayList<>();
        top_donors_adapter = new TopDonors_adapter(this,ls,1);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(top_donors_adapter);
        read();
    }
    void read(){

        FirebaseDatabase.getInstance().getReference("participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {

                  String g_id=getIntent().getStringExtra("goal_id");
                   for(DataSnapshot snapshot:datasnapshot.getChildren()){
                          for(DataSnapshot snapshot1:snapshot.getChildren()) {
                              participant p1 = snapshot1.getValue(participant.class);
                              if (p1.getGoal_id().matches(g_id)) {
                                  p.add(p1);
                              }
                          }
                   }
                FirebaseDatabase.getInstance().getReference("users")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                ls.clear();
                                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                    user u = snapshot1.getValue(user.class);
                                    boolean f=false;
                                    for(participant i: p){
                                      if(i.getDonor_id().matches(u.getId())){
                                         f=true;
                                      }
                                    }
                                    if(f==true){
                                        ls.add(u);
                                    }

                                }
                                // maximum 5 top donors initialization

                                top_donors_adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}