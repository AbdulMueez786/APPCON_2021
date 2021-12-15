package com.armughanaslam.appcon_2021.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.armughanaslam.appcon_2021.Adapters.TopDonors_adapter;
import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TopDonors extends AppCompatActivity {

    private ImageButton back_arrow;
    private RecyclerView rv;
    private List<user> ls;
    private TopDonors_adapter top_donors_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_top_donors);
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
        top_donors_adapter = new TopDonors_adapter(this,ls,0);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(top_donors_adapter);
        read();
    }
    void read(){
        FirebaseDatabase.getInstance().getReference("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        ls.clear();
                        for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                            user u = snapshot1.getValue(user.class);
                            ls.add(u);
                        }
                        // maximum 5 top donors initialization

                        top_donors_adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}