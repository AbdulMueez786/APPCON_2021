package com.armughanaslam.appcon_2021.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.armughanaslam.appcon_2021.Adapters.MyGoals_adapter;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyGoals extends Fragment {

    private View view;
    private RecyclerView rv;
    private List<goal> ls;
    private com.google.android.material.textfield.TextInputEditText list_search;
    private MyGoals_adapter list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_goals, container, false);
        rv=view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ls = new ArrayList<>();
        this.list_search = view.findViewById(R.id.list_search);
        list_adapter = new MyGoals_adapter(ls, getContext());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(list_adapter);

        this.list_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        read_goals();

        return view;
    }
    void read_goals(){
        FirebaseDatabase.getInstance().getReference("goals")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        ls.clear();
                        for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                            goal r = snapshot1.getValue(goal.class);

                            ls.add(r);
                            list_adapter.notifyDataSetChanged();
                            list_adapter.addlist();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


}