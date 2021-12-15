package com.armughanaslam.appcon_2021.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputLayout password,email,confirm_password,name;
    private com.google.android.material.button.MaterialButton SignUp_button;
    private TextView sign_in_here;

    private CircleImageView image_profile;
    private int request_code=200;
    private Uri selectedImage_uri=null;

    // Firebase
    private FirebaseAuth auth;
    private DatabaseReference data_ref;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        name=findViewById(R.id.title);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Signup_confirm_password=findViewById(R.id.signup_confirm_password);
        SignUp_button=findViewById(R.id.signup_button);
        data_ref = FirebaseDatabase.getInstance().getReference("users");
        sign_in_here=findViewById(R.id.sign_in_here);
        sign_in_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        image_profile=findViewById(R.id.image_profile);

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        SignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String EMAIL=email.getEditText().getText().toString();
                final String PASSWORD=password.getEditText().getText().toString();
                //final String CONFIRM_PASSWORD=Signup_confirm_password.getEditText().getText().toString();

                if(checkDataEntered()) {
                    auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //Ned
                                        /*FirebaseDatabase.getInstance().getReference("points")
                                                .child(auth.getCurrentUser().getUid()).child("cheer_point_sent").setValue("0");
                                        FirebaseDatabase.getInstance().getReference("points")
                                                .child(auth.getCurrentUser().getUid()).child("cheer_point_received").setValue("0");*/
                                        ///Ned
                                        //Toast toast = Toast.makeText(SignUp.this, "Successfully created", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                        //toast.show(); // display the Toast

                                        /*
                                        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
                                        String app_id = status.getSubscriptionStatus().getUserId();
                                        */
                                        //user USE = new user(id, "null", "0", "0", EMAIL, "app_id", "default", "online");
                                        //user_ref.child(id).setValue(USE);
                                        StoreData();



                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast toast = Toast.makeText(SignUp.this, "Already account on this email", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                        toast.show(); // display the Toast
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast toast = Toast.makeText(SignUp.this, "Already account on this email", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                }
                            });
                }
            }
        });
    }
    void openGallery(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,request_code);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            this.selectedImage_uri=data.getData();
            this.image_profile.setImageURI(this.selectedImage_uri);
        }
    }

    void StoreData() {

        if (selectedImage_uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            storageReference = storageReference.child("profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            storageReference.putFile(this.selectedImage_uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            // Toast.makeText(create_profile.this, "complited", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String dp = uri.toString();

                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            data_ref.child(id).setValue(new user(FirebaseAuth.getInstance().getCurrentUser().getUid(),email.getEditText().getText().toString(),"",name.getEditText().getText().toString(),"ONLINE",dp,"0","not suspended")).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(SignUp.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(create_profile.this, "Failuer", Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    //Varification Checks
    private boolean isEmpty(com.google.android.material.textfield.TextInputLayout obj) {
        CharSequence str = obj.getEditText().getText().toString();
        return TextUtils.isEmpty(str);
    }
    private boolean isEmail(com.google.android.material.textfield.TextInputLayout text) {
        CharSequence email = text.getEditText().getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    private boolean checkDataEntered() {

        boolean flag=true;
        if(isEmpty(this.name)){
            this.name.setError("name is required!");
            flag=false;
        }
        else{
            this.name.setErrorEnabled(false);
            flag=true;
        }
        if(isEmpty(this.email)){
            this.email.setError("email is required!");
            flag=false;
        }
        else if(isEmail(this.email)==false){
            this.email.setError("invalid email");
            flag=false;
        }
        else{
            this.email.setErrorEnabled(false);
            flag=true;
        }
        if(isEmpty(this.password)){
            this.password.setError("password is required!");
            flag=false;
        }
        else if(this.password.getEditText().getText().length()<6){
            this.password.setError("password size minimum 6 character!");
            flag=false;
        }
        else{
            this.password.setErrorEnabled(false);
            flag=true;
        }
        if(selectedImage_uri==null){

            flag=false;
        }
        else{
            flag=true;
        }

        /*
        if(this.password.getEditText().getText().toString().matches(this.Signup_confirm_password.getEditText().getText().toString())==false){
            this.Signup_confirm_password.setError("incorrect!");
            return false;
        }
        else{
            this.Signup_confirm_password.setErrorEnabled(false);
        }*/

        return flag;
    }
    @Override
    public void onBackPressed() {
        finish();
    }

}