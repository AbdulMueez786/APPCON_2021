package com.armughanaslam.appcon_2021.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.armughanaslam.appcon_2021.Fragments.HomeFragment;
import com.armughanaslam.appcon_2021.Fragments.MyGoals;
import com.armughanaslam.appcon_2021.Fragments.Profile;
import com.armughanaslam.appcon_2021.Fragments.QR;
import com.armughanaslam.appcon_2021.GoalDetailsQR;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.QRA;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Home extends AppCompatActivity {

    private int selectedTab=1;
    private static final int request_code_1 = 1,request_code_2=2;
    public ActivityResultLauncher<Intent> someActivityResultLauncher;
    public  String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        final LinearLayout home_Layout=findViewById(R.id.home_layout);
        final LinearLayout qr_Layout=findViewById(R.id.qr_layout);
        final LinearLayout profile_Layout=findViewById(R.id.profile_layout);

        final ImageView home_Image =findViewById(R.id.home_image);
        final ImageView qr_Image =findViewById(R.id.qr_image);
        final ImageView profile_Image =findViewById(R.id.profile_image);


        final TextView home_text=findViewById(R.id.home_text);
        final TextView qr_text=findViewById(R.id.qr_text);
        final TextView profile_text=findViewById(R.id.prfile_text);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null).commit();


        home_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if home is already selected or not
                if(selectedTab!=1){
                    //Set home fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class,null).commit();

                    //unselect all other tabs
                    qr_text.setVisibility(View.GONE);
                    profile_text.setVisibility(View.GONE);

                    qr_Image.setImageResource(R.drawable.ic_qr_code_not_selected);
                    profile_Image.setImageResource(R.drawable.profile_not_selected);

                    qr_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profile_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select home tab
                    home_text.setVisibility(View.VISIBLE);
                    home_Image.setImageResource(R.drawable.home_selected);
                    home_Layout.setBackgroundResource(R.drawable.round_back_home);

                    // create animation
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    home_Layout.setAnimation(scaleAnimation);

                    //set 1st tab as selected
                    selectedTab=1;

                }
            }
        });

        qr_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab!=2){

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, QR.class,null).commit();

                    //unselect all other tabs
                    home_text.setVisibility(View.GONE);
                    profile_text.setVisibility(View.GONE);


                    home_Image.setImageResource(R.drawable.home_not_selected);
                    profile_Image.setImageResource(R.drawable.profile_not_selected);

                    home_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profile_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    qr_text.setVisibility(View.VISIBLE);
                    qr_Image.setImageResource(R.drawable.ic_qr_code_selected);
                    qr_Layout.setBackgroundResource(R.drawable.round_back_home);

                    // create animation
                    ScaleAnimation  scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    qr_Layout.setAnimation(scaleAnimation);


                    selectedTab=2;

                }
            }
        });

        profile_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab!=3){

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,Profile.class,null).commit();

                    //unselect all other tabs
                    home_text.setVisibility(View.GONE);
                    qr_text.setVisibility(View.GONE);



                    home_Image.setImageResource(R.drawable.home_not_selected);
                    qr_Image.setImageResource(R.drawable.ic_qr_code_not_selected);



                    home_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    qr_Layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profile_text.setVisibility(View.VISIBLE);
                    profile_Image.setImageResource(R.drawable.profile_selected);
                    profile_Layout.setBackgroundResource(R.drawable.round_back_home);

                    // create animation
                    ScaleAnimation  scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profile_Layout.setAnimation(scaleAnimation);


                    selectedTab=3;

                }
            }
        });



        // fetching qr image from gallery
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                        }
                    }
                });
        // permissions
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        // Requesting Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS,
                PERMISSION_ALL);


    }
    public void logout(){

        Intent intent =new Intent(Home.this,Login.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    public void navigate_topDonors(){
        Intent intent = new Intent(this, TopDonors.class);
        startActivity(intent);
    }


    public void scanGallery(ActivityResultLauncher<Intent> someActivityResultLauncher){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == request_code_1){
            String uri=data.getStringExtra("uri");
        }
        else if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try {

                    Bitmap bMap = selectedImage;

                    String contents = null;

                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    contents = result.getText();

                    Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG).show();

                    this.code=contents;
                    //(QR) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                    //open new activity
                    final goal[] g = new goal[1];

                    FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                            System.out.println(result.getText());
                            boolean flag=false;
                            for(DataSnapshot snapshot:datasnapshot.getChildren()){
                                if(snapshot.getKey().matches(code)){
                                    g[0] =snapshot.getValue(goal.class);
                                    System.out.println("It worked");
                                flag=true;
                                }
                            }

                            if(flag==true){
                            FirebaseDatabase.getInstance().getReference("users").child(g[0].getCreater_id()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    Intent i = new Intent(Home.this, GoalDetail.class);
                                    i.putExtra("goal_id", g[0].getId());
                                    i.putExtra("goal_title",g[0].getTitle());
                                    i.putExtra("goal_image",g[0].getImage());
                                    i.putExtra("goal_status",g[0].getStatus());
                                    i.putExtra("creater_id",g[0].getCreater_id());
                                    i.putExtra("goal_description",g[0].getDescription());
                                    i.putExtra("goal_creater_name", snapshot.child("name").getValue(String.class));
                                    i.putExtra("raised_amount",g[0].getRaised_Amount());
                                    i.putExtra("target_amount",g[0].getTarget_Amount());
                                    i.putExtra("No_of_participants",g[0].getNo_of_participants());
                                    startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });}


                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });






                }catch (Exception e){

                    e.printStackTrace();

                }

                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

                Toast.makeText(Home.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }


    }
}