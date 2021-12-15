package com.armughanaslam.appcon_2021.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.armughanaslam.appcon_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputLayout email,password;
    private com.google.android.material.button.MaterialButton login_button;
    private TextView create_account;

    //Firebase
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    //private DatabaseReference data_ref;


    @Override
    protected void onStart(){
        super.onStart();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){ // if user is already login

            if(firebaseUser.getEmail().matches("admin@gmail.com")==true){
                //Admin
                /*Intent intent=new Intent(Login.this,AdminHome.class);
                startActivity(intent);
                finish();*/
            }
            else {
                //employee
                Intent intent=new Intent(Login.this,Home.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        login_button=findViewById(R.id.login_button);
        create_account =findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, com.armughanaslam.appcon_2021.Activity.SignUp.class);
                startActivity(intent);
            }
        });

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkDataEntered()) {
                    final String Email = email.getEditText().getText().toString();
                    final String Password = password.getEditText().getText().toString();

                    System.out.println("--------------------");
                    System.out.println(Email);
                    System.out.println(Password);
                    System.out.println("--------------------");

                    auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        /*OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
                                        String app_id = status.getSubscriptionStatus().getUserId();
                                        HashMap hasmap = new HashMap();
                                        hasmap.put("app_id", app_id);
                                            */
                                        //data_ref = FirebaseDatabase.getInstance().getReference("users");
                                        //data_ref.child(auth.getUid()).setValue(new user(auth.getUid(),));

                                        if (Email.trim().matches("admin@gmail.com")) {
                                           /* Intent intent = new Intent(Login.this, AdminHome.class);
                                            startActivity(intent);
                                            finish();*/
                                        } else {
                                            Intent intent = new Intent(Login.this, Home.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast toast = Toast.makeText(Login.this, "Incorrect Input", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                        toast.show(); // display the Toast
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast toast = Toast.makeText(Login.this, "Sorry some error occur please try again", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                }
                            });
                }
            }
        });
    }

    //Varification
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
        if (isEmpty(this.email)) {
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

        if (isEmpty(this.password)) {
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
        return flag;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}