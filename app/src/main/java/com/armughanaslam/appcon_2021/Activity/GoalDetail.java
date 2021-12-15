package com.armughanaslam.appcon_2021.Activity;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.armughanaslam.appcon_2021.Model.participant;
import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Integer.parseInt;

public class GoalDetail extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton menu,back,status_icon,
            status_Active_icon,status_Close_icon;
    private ImageView goal_image;
    private TextView goal_description,goal_creater_name,status_text;
    private ProgressBar progressBar;
    private TextView raised_amount,target_amount;
    private CircleImageView participant_image1,participant_image2,participant_image3;
    private TextView no_of_rem_partispants;
    private Button see_all;
    private MaterialButton donated_now;
    private int n=0;
    private Intent intent;
    private DatabaseReference data_ref;
    private  String G_id;
    private LinearLayout mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LinearLayout mHeaderLayout;
    private List<participant> participant_ls;
    TextView hundred;
    TextView five_hundred;
    TextView thousand;
    EditText custom_amt;
    Button donate_button;
    int amount_chosen = 0;
    boolean state_flag=true;
    String creater_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        getSupportActionBar().hide();
        participant_ls = new ArrayList<>();
        intent = getIntent();
        status_Active_icon=findViewById(R.id.status_Active_icon);
        status_Close_icon=findViewById(R.id.status_Close_icon);
        status_text=findViewById(R.id.status_text);
        data_ref = FirebaseDatabase.getInstance().getReference("participants");



        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       menu=findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
        status_icon=findViewById(R.id.status_icon);
        status_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showPopup_status(view);
            }
        });

        goal_image=findViewById(R.id.goal_image);

        Picasso.with(this).load(intent.getStringExtra("goal_image"))
                .resize(900, 600).into(goal_image);


        goal_description=findViewById(R.id.goal_description);
        goal_description.setText(intent.getStringExtra("goal_description"));

        goal_creater_name=findViewById(R.id.goal_creater_name);
        goal_creater_name.setText(intent.getStringExtra("goal_creater_name"));



        raised_amount=findViewById(R.id.raised_amount);
        raised_amount.setText(intent.getStringExtra("raised_amount"));

        target_amount=findViewById(R.id.target_amount);
        target_amount.setText(intent.getStringExtra("target_amount"));

        progressBar=findViewById(R.id.progressBar);
        progressBar.setMax(parseInt(target_amount.getText().toString()));
        progressBar.setProgress(parseInt(raised_amount.getText().toString()));
        //progressBar.setMax(o);
        //participants
        participant_image1=findViewById(R.id.participant_image1);

        participant_image2=findViewById(R.id.participant_image2);

        participant_image3=findViewById(R.id.participant_image3);

        no_of_rem_partispants=findViewById(R.id.no_of_rem_partispants);

        see_all=findViewById(R.id.see_all);

        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalDetail.this, DonorList.class);
                intent.putExtra("goal_id",G_id );
                startActivity(intent);
            }
        });

        hundred = findViewById(R.id.hundred);
        five_hundred = findViewById(R.id.five_hundred);
        thousand = findViewById(R.id.thousand);
        custom_amt = findViewById(R.id.custom_amount);
        //donate_button = findViewById(R.id.donate_button);

        mBottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        hundred = findViewById(R.id.hundred);
        five_hundred = findViewById(R.id.five_hundred);
        thousand = findViewById(R.id.thousand);
        custom_amt = findViewById(R.id.custom_amount);
        donate_button = findViewById(R.id.donate_button);

        //set selected rupees button different color
        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_chosen = 100;
                hundred.setBackground(getResources().getDrawable(R.drawable.button_select));
                five_hundred.setBackground(getResources().getDrawable(R.drawable.blank));
                thousand.setBackground(getResources().getDrawable(R.drawable.blank));

            }
        });

        five_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_chosen = 500;
                five_hundred.setBackground(getResources().getDrawable(R.drawable.button_select));
                hundred.setBackground(getResources().getDrawable(R.drawable.blank));
                thousand.setBackground(getResources().getDrawable(R.drawable.blank));
            }
        });
        thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_chosen = 1000;
                thousand.setBackground(getResources().getDrawable(R.drawable.button_select));
                five_hundred.setBackground(getResources().getDrawable(R.drawable.blank));
                hundred.setBackground(getResources().getDrawable(R.drawable.blank));
            }
        });

        custom_amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount_chosen = 0;
                thousand.setBackground(getResources().getDrawable(R.drawable.blank));
                five_hundred.setBackground(getResources().getDrawable(R.drawable.blank));
                hundred.setBackground(getResources().getDrawable(R.drawable.blank));
            }
        });

        String g_id=intent.getStringExtra("goal_id");




        G_id=g_id;
        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount_chosen == 0){

                    if(custom_amt.getText().toString() != null){
                        String key=data_ref.push().getKey();
                        String txt = "You donated Rs "+custom_amt.getText().toString();
                        Toast.makeText(GoalDetail.this, txt, Toast.LENGTH_LONG).show();
                        String d_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        donate(key,g_id,d_id,txt,intent.getStringExtra("goal_title"));
                        update_goal(g_id,intent.getStringExtra("No_of_participants"),raised_amount.getText().toString(),custom_amt.getText().toString());
                    }
                }
                else {
                    String key=data_ref.push().getKey();
                    String d_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    donate(key,g_id,d_id,String.valueOf(amount_chosen),intent.getStringExtra("goal_title"));
                    update_goal(g_id,intent.getStringExtra("No_of_participants"),raised_amount.getText().toString(),String.valueOf(amount_chosen));
              //      Toast.makeText(GoalDetail.this, "You donated Rs " + Integer.toString(amount_chosen), Toast.LENGTH_LONG).show();
                }

            }
        });


        //opens bottom sheet
        donated_now = findViewById(R.id.open_bottom_sheet);
        donated_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state_flag==true) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else if(state_flag==false){
                    Toast.makeText(GoalDetail.this, "Funding closed", Toast.LENGTH_LONG).show();
                }
            }
        });
        String s=intent.getStringExtra("goal_status");
        if(s.matches("Active")){
            status_Close_icon.setVisibility(View.GONE);
            status_Active_icon.setVisibility(View.VISIBLE);
            status_text.setText("Active");
            state_flag=true;
            //donated_now.setEnabled(true);
        }
        else if(s.matches("Closed")){
            status_Close_icon.setVisibility(View.VISIBLE);
            status_Active_icon.setVisibility(View.GONE);
            status_text.setText("Closed");
            state_flag=false;
            //donated_now.setEnabled(false);
        }
        creater_id=intent.getStringExtra("creater_id");

        if(creater_id.matches(FirebaseAuth.getInstance().getCurrentUser().getUid())==false){
            status_icon.setVisibility(View.INVISIBLE);
        }
        System.out.println("yyyyyyyyyyyyyyy");
        System.out.println(creater_id);
        System.out.println("yyyyyyyyyyyyyyy");
        participants_check();
    }
    void participants_check(){
        FirebaseDatabase.getInstance()
                .getReference("participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot data_snapshot) {
               int i=0;
               participant p1 = null,p2 = null,p3 = null;
                for(DataSnapshot snapshot:data_snapshot.getChildren() ){
                   for(DataSnapshot s:snapshot.getChildren()){
                       participant p=s.getValue(participant.class);
                       if(p.getGoal_id().matches(G_id)){
                       i++;
                       if(i==1){
                           p1=p;
                       }
                       else if(i==2){
                           p2=p;
                       }
                       else if(i==3){
                           p3=p;
                       }
                       participant_ls.add(p);
                       }
                   }
               }

                //n=parseInt(intent.getStringExtra("No_of_participants"));
                n=i;
                participant finalP = p1;
                participant finalP1 = p2;
                participant finalP2 = p3;

                FirebaseDatabase.getInstance().getReference("user").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                       for(DataSnapshot snapshot1:snapshot.getChildren()) {
                           user u=snapshot1.getValue(user.class);
                           Log.d("Data","--------------------");
                           Log.d("Data",u.getUser_profile());
                           Log.d("Data","--------------------");
                           if(u.getId().matches(finalP.getDonor_id())){
                               Log.d("Data","--------------------");
                               Log.d("Data",u.getUser_profile());
                               Log.d("Data","--------------------");
                               Picasso.with(GoalDetail.this)
                                       .load(u.getUser_profile()).into(participant_image1);
                           }
                           else if(u.getId().matches(finalP1.getDonor_id())){
                               Picasso.with(GoalDetail.this)
                                       .load(u.getUser_profile()).into(participant_image2);
                           }
                           else if(u.getId().matches(finalP2.getDonor_id())){
                               Picasso.with(GoalDetail.this)
                                       .load(u.getUser_profile()).into(participant_image3);
                           }
                       }
                       if(n>=3){

                           participant_image1.setVisibility(View.VISIBLE);
                           participant_image2.setVisibility(View.VISIBLE);
                           participant_image3.setVisibility(View.VISIBLE);
                           n=n-3;
                           if(n>0){
                               no_of_rem_partispants.setVisibility(View.VISIBLE);
                           }
                           else if(n==0){
                               no_of_rem_partispants.setVisibility(View.GONE);
                           }

                       }
                       else if(n>=2){
                           participant_image1.setVisibility(View.VISIBLE);
                           participant_image2.setVisibility(View.GONE);
                           participant_image3.setVisibility(View.GONE);
                           no_of_rem_partispants.setVisibility(View.GONE);
                       }
                       else if(n==1){
                           participant_image1.setVisibility(View.VISIBLE);
                           participant_image2.setVisibility(View.GONE);
                           participant_image3.setVisibility(View.GONE);
                           no_of_rem_partispants.setVisibility(View.GONE);
                       }
                       else{
                           participant_image1.setVisibility(View.GONE);
                           participant_image2.setVisibility(View.GONE);
                           participant_image3.setVisibility(View.GONE);
                           no_of_rem_partispants.setVisibility(View.GONE);
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
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);

        bottomSheetDialog.show();
    }

    private void donate(String key,String g_key,String donor_id,String donated_amount,String g_title){

        if(state_flag==true) {
            data_ref.child(g_key).child(key)
                    .setValue(new participant(key, g_key, donor_id, g_title, g_title, donated_amount))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(GoalDetail.this, "Successfully Donated", Toast.LENGTH_LONG).show();

                        }
                    });
        }
        else if(state_flag==false){
            Toast.makeText(GoalDetail.this, "Funding closed", Toast.LENGTH_LONG).show();
        }

    }
    private void update_goal(String goal_id,String no_of_prt,String raised_amt,String amount){
        int no_prt= parseInt(no_of_prt);
        int rst_amd= parseInt(raised_amt);
        int amt= parseInt(amount);
        no_prt=no_prt+1;
        rst_amd=rst_amd+amt;

        HashMap h1=new HashMap();
        h1.put("no_of_participants",String.valueOf(no_prt));
        h1.put("raised_Amount",String.valueOf(rst_amd));
        System.out.println(goal_id);
        FirebaseDatabase.getInstance().getReference("goals")
                .child(goal_id).updateChildren(h1).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(GoalDetail.this, "", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(GoalDetail.this, "", Toast.LENGTH_LONG).show();
            }
        });

        System.out.println("--------------------------------------------");
        // No of Participants increament in goal

        // goal raised_amount increment

    }


    public void showPopup(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    public void showPopup_status(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.status_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.updates:
                Intent intent=new Intent(this,Updates.class);
System.out.println("000000000000000000000");
                System.out.println(creater_id);
                System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
                System.out.println("000000000000000000000");
                intent.putExtra("creater_id", creater_id);
                intent.putExtra("goal_id",G_id);
                startActivity(intent);
                return true;

            case R.id.top_donors:
                startActivity(new Intent(this,Home.class));
                return true;
            case R.id.active:
                       status_Close_icon.setVisibility(View.GONE);
                       status_Active_icon.setVisibility(View.VISIBLE);
                       status_text.setText("Active");
                       //donated_now.setEnabled(true);
                       state_flag=true;

                return true;
            case R.id.closed:
                    status_Close_icon.setVisibility(View.VISIBLE);
                    status_Active_icon.setVisibility(View.GONE);
                    status_text.setText("Closed");
                    //donated_now.setEnabled(false);
                    state_flag=false;

                return true;
        }
        return false;
    }
}
