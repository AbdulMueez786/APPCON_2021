package com.armughanaslam.appcon_2021.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.armughanaslam.appcon_2021.Adapters.TopDonors_adapter;
import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TopDonors extends Fragment {

    private View view;
    private RecyclerView rv;
    private TopDonors_adapter adapter;
    private List<user> ls;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_top_donors, container, false);
        rv=view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ls = new ArrayList<>();
        readUsers();

        return view;
    }

    private void readUsers(){
        //final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        final Context c = getContext();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final user u = snapshot.getValue(user.class);
                    //assert u != null;
                    //assert firebaseUser != null;

                    //if (u.getId().matches(firebaseUser.getUid()) == false && u.getName().matches("admin") == false) {

                        ls.add(u);
                        adapter = new TopDonors_adapter(c, ls,0);
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        /*      FirebaseDatabase.getInstance().getReference("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.child("receiver").getValue(String.class).matches(u.getId()) == true
                                            || snapshot.child("sender").getValue(String.class).matches(u.getId()) == true
                                    ) {

                                        ls.add(u);
                                        break;
                                    }
                                }
                                java.util.Collections.reverse(ls);
                                userAdapter = new ChatUsersRvAdapter(c, ls);
                                rv.setAdapter(userAdapter);
                                userAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
*/

                    //}
/*
                    if (u.getId().matches(firebaseUser.getUid()) == true) {

                        if (u.getUser_profile().matches("default") != true) {
                            Picasso.get().load(u.getUser_profile())
                                    .into(messages_profile);
                        } else {
                            Picasso.get().load(R.drawable.user)
                                    .into(messages_profile);
                        }
                    }
  */
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
     }
}