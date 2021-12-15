package com.armughanaslam.appcon_2021.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.armughanaslam.appcon_2021.Adapters.updates_adapter;
import com.armughanaslam.appcon_2021.Model.update;
import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Updates extends AppCompatActivity {

    private CircleImageView profile_image;
    private TextView username;
    private FirebaseUser fuser;
    private DatabaseReference reference;
    private updates_adapter messageAdapter;
    private Intent intent;
    private RecyclerView rv;
    private List<update> ls;
    private String userid = "";
    private EditText text_send;
    private ImageView btn_send, btn_send_task, report;
    private int request_code = 200;
    private Uri selectedImage_uri = null;
    private String message_Type = "text";
    private ImageView attach_pic;
    private FirebaseUser current_user;
    private String path;
    private List<String> badwordList;
    Dialog badwordDialog;
    Button understandButton;
    String g_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        getSupportActionBar().hide();




//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

  /*
        report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog b = new BottomSheetDialog(
                        Chat.this, R.style.BottomSheetDialogTheme
                );
                View bv = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );
                String pic = intent.getStringExtra("profile");
                RoundedImageView i = bv.findViewById(R.id.user_pic);
                TextView user_name = bv.findViewById(R.id.user_name);
                TextView user_phone_no = bv.findViewById(R.id.user_phone_no);
                TextView user_gender = bv.findViewById(R.id.user_gender);
                EditText report_text = bv.findViewById(R.id.report_text);

                FirebaseDatabase.getInstance().getReference("users").child(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user u = dataSnapshot.getValue(user.class);
                        user_name.setText(u.getName());
                        user_phone_no.setText(u.getEmail());
                        user_gender.setText(u.getGender());
                        if (u.getUser_profile().equals("default") != true) {
                            Picasso.get().load(u.getUser_profile())
                                    .into(i);
                        } else {
                            Picasso.get().load(u.getUser_profile())
                                    .into(i);
                        }
                        readmessage(fuser.getUid(), userid, u.getUser_profile());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                bv.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(" --------------------------------------- ");
                        String key = FirebaseDatabase.getInstance().getReference("report").push().getKey();
                        FirebaseDatabase.getInstance().getReference("report").child(key).setValue(
                                new report(key, pic, user_name.getText().toString(), "", userid, FirebaseAuth.getInstance().getCurrentUser().getUid().toString()
                                        , report_text.getText().toString()));

                    }
                });
                b.setContentView(bv);
                b.show();
            }
        });

        badwordDialog = new Dialog(this);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

        this.attach_pic = findViewById(R.id.attach_pic);
        this.attach_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_Type = "Image";
                openGallery();
            }
        });

        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        intent = getIntent();

        this.userid = intent.getStringExtra("creater_id");
        this.g_id=userid = intent.getStringExtra("goal_id");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(userid);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    message_Type = "text";
                    sendmessage(fuser.getUid(), userid, msg);
      //              checkMessageForBadWords();
                } else if (message_Type.matches("Image")) {
                    sendmessage(fuser.getUid(), userid, selectedImage_uri.toString());
                } else {

                }
                text_send.setText("");
            }
        });

        LinearLayout ln_action=findViewById(R.id.ln_action);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().matches(intent.getStringExtra("creater_id"))==false){
            System.out.println("-------------1-----------");
            System.out.println("-------------2-----------");
            //System.out.println(fuser.getUid());

            System.out.println(intent.getStringExtra("creater_id"));
            ln_action.setVisibility(View.INVISIBLE);
            System.out.println("--------------1----------");
            System.out.println("---------------2---------");
        }else{
            System.out.println("OPPPPPPPPPPPPPPPP");
            System.out.println("---------------2---------");
            ln_action.setVisibility(View.VISIBLE);
        }

       // reference.addValueEventListener(new ValueEventListener() {
        //    @Override
        //    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          //      user u = dataSnapshot.getValue(user.class);
                username.setText("Goal updates ");
                //if (u.getUser_profile().equals("default") != true) {

               // } else {
                 //   Picasso.with(Updates.this).load(R.drawable.user)
                  //          .into(profile_image);
               // }
               // readmessage(fuser.getUid(), userid, u.getUser_profile()); }

            //@Override
  //          public void onCancelled(@NonNull DatabaseError databaseError) {

//            }
    //    });
        readmessage();
        profile_image.setVisibility(View.INVISIBLE);
        //Picasso.with(Updates.this).load(u.getUser_profile())
        //        .into(profile_image);
        btn_send_task = findViewById(R.id.btn_send_task);
        btn_send_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                message_Type = "task";
                sendmessage(fuser.getUid(), userid, msg);
  //              checkMessageForBadWords();
            }
        });
        badwordList = new ArrayList<String>();

        path = this.getApplicationInfo().dataDir + File.separatorChar + "badwords.txt";

    //    readFile(path);
    }


    void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, request_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            this.selectedImage_uri = data.getData();

            Toast.makeText(Updates.this, "Image selected", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Do you want to send this Image?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //activity4_message.setText("Empty Message");
                    //SendMessage();
                    message_Type = "Image";
                    sendmessage(fuser.getUid(), userid, selectedImage_uri.toString());
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    message_Type = "text";
                    selectedImage_uri = null;
                }
            });
            AlertDialog d = dialog.create();
            d.show();

        }
    }

    private void sendmessage(String sender, String reciever, final String message) {

        if (this.message_Type.matches("text") == true) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence s = df.format("hh:mm a", new Date());
            String key = ref.child("updates").push().getKey();
            ref.child("updates").child(key).setValue(new update(key,g_id,message_Type,message,s.toString()));

        }  else if(this.message_Type.matches("Image")==true){

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String key = ref.push().getKey().toString();
            storageReference = storageReference.child("Updates/").child(key);
            storageReference.putFile(this.selectedImage_uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //Toast.makeText(Chat.this, "complited", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            android.text.format.DateFormat df = new android.text.format.DateFormat();
                            CharSequence s = df.format("hh:mm a", new Date());
                            final String dp = uri.toString();
                            String key = ref.child("updates").push().getKey();
                            ref.child("updates").child(key).setValue(new update(key,g_id,message_Type,dp,s.toString()));

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  //  Toast.makeText(Chat.this, "No internet,Try again", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    private void readmessage() {


        ls = new ArrayList<>();
        messageAdapter = new updates_adapter(this, ls, null);
        rv.setAdapter(messageAdapter);
        reference = FirebaseDatabase.getInstance().getReference("updates");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    update c = snapshot.getValue(update.class);


                    if ((c.getGoal_id().equals(g_id) == true)){
                        ls.add(c);
                    }

                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //status("online");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserStatus").child(current_user.getUid());
        //ref.onDisconnect().setValue("offline");
        //ref.setValue("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //status("offline");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserStatus").child(current_user.getUid());
        //ref.onDisconnect().setValue("offline");
        //ref.setValue("offline");
    }
}