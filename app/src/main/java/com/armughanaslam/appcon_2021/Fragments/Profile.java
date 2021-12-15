package com.armughanaslam.appcon_2021.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.armughanaslam.appcon_2021.Activity.Home;
import com.armughanaslam.appcon_2021.Adapters.MainAdapter;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.Model.participant;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Profile extends Fragment {

    private View view;
    private List<goal> goal_ls;
    private List<participant>participant_ls;
    private FirebaseUser firebaseUser;
    private ExpandableListView expandableListView;
    private List<String> listGroup;
    private HashMap<String, List<String>> listItem;
    private MainAdapter adapter;
    private CircularImageView profile_image;
    private TextView name,no_goals_created,balance;
    private ImageView varified_icon,varified_not_icon;
    private TextView varified_status;
    private ImageButton back_arrow,top_donors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        goal_ls = new ArrayList<>();
        participant_ls = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);
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

        profile_image=view.findViewById(R.id.profile_image);
        name=view.findViewById(R.id.title);

        varified_icon=view.findViewById(R.id.varified_icon);
        varified_not_icon=view.findViewById(R.id.varified_not_icon);

        varified_status=view.findViewById(R.id.varified_status);

        expandableListView = view.findViewById(R.id.expandable_list);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(getContext(),listGroup,listItem);
        expandableListView.setAdapter(adapter);



        initListData();

        // fetching user information
        FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                name.setText(snapshot.child("name").getValue(String.class));
                System.out.println(snapshot.child("email").getValue(String.class));
                Picasso.with(getContext()).load(snapshot.child("user_profile").getValue(String.class))
                        .into(profile_image);
                String str=snapshot.child("status").getValue(String.class);
                System.out.println("--------------------");
                System.out.println(str);
                if(str.matches("verified")==true){
                    varified_status.setText("Verified");
                    varified_icon.setVisibility(View.VISIBLE);
                    varified_not_icon.setVisibility(View.GONE);
                }
                else if(str.matches("not_verified")==true){
                    varified_status.setText("Not Verified");
                    varified_icon.setVisibility(View.GONE);
                    varified_not_icon.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        // fetching user goal
        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                goal_ls.clear();
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                     goal g=snapshot.getValue(goal.class);
                     if(g.getCreater_id().matches(firebaseUser.getUid())){
                          goal_ls.add(g);
                     }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        return  view;

    }
    private void initListData() {
        listGroup.add("My Goals");
        listGroup.add("Participated Goals");

        String[] array;

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        // fetching goals of user participation
        FirebaseDatabase.getInstance().getReference("participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                participant_ls.clear();
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    for(DataSnapshot d:snapshot.getChildren()) {
                        participant p = d.getValue(participant.class);
                        if (p.getDonor_id().matches(firebaseUser.getUid())) {
                            participant_ls.add(p);
                            list2.add(p.getGoal_title());
                        }
                    }


                }

                FirebaseDatabase.getInstance().getReference("goals")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                           goal g=snapshot1.getValue(goal.class);
                           if(g.getCreater_id().matches(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                               list1.add(g.getTitle());
                           }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);

        adapter.notifyDataSetChanged();
    }

}