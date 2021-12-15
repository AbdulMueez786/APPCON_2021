package com.armughanaslam.appcon_2021.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class NewGoal extends AppCompatActivity {

    private int request_code=200;
    private Uri selectedImage_uri=null;
    private AlertDialog dialog;
    private static final int result_code = 2;
    private String[] items={"Medical","Education","Disaster","Others"};
    private String category_selected="Others";
    private Uri qr_uri=null;
    private AutoCompleteTextView autoCompleteText;
    private ArrayAdapter<String> adapterItem;
    private ImageButton back_arrow;
    private TextInputLayout title,description,target_amount;
    private ImageView image;
    private MaterialButton create_button;

    // Firebase
    private FirebaseAuth auth;
    private DatabaseReference data_ref;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);
        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        data_ref = FirebaseDatabase.getInstance().getReference("goals");

        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        target_amount=findViewById(R.id.target_amount);

        autoCompleteText=findViewById(R.id.autoCompletetxt);
        adapterItem=new ArrayAdapter<String>(this,R.layout.list_category_item,items);

        autoCompleteText.setAdapter(adapterItem);

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item =adapterView.getItemAtPosition(i).toString();
                System.out.println(item);
                category_selected=item;

            }
        });
        image=findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        create_button=findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(title.getEditText().getText().toString());
                StoreData(title.getEditText().getText().toString(),description.getEditText().getText().toString()
                        ,category_selected,target_amount.getEditText().getText().toString(),"0",
                        "0","Active","");

            }
        });






/*
        auth=FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        data_ref = FirebaseDatabase.getInstance().getReference("goals");

        //Image
        image=findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        // title
        EditText title=findViewById(R.id.title);

        //description
        EditText description=findViewById(R.id.description);

        //category
        EditText category=findViewById(R.id.category);

        //Target amount
        EditText Tamt=findViewById(R.id.target_amount);

        //Raised amount
        EditText Ramt=findViewById(R.id.raised_amount);

        //Goal updates (text or images)


        // QR code generation



        Button btn=findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(NewGoal.this);
                LayoutInflater inflater=getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.custom_dialog,null));
                builder.setCancelable(false);
                dialog=builder.create();

                //dialog.setBackgroundDrawable(new ColorDrawable(
                //        Color.TRANSPARENT));
                dialog.show();
              System.out.println(title.getText().toString());
              System.out.println(description.getText().toString());
              System.out.println(category.getText().toString());
              System.out.println(Tamt.getText().toString());
              System.out.println(Ramt.getText().toString());
              StoreData(title.getText().toString(),description.getText().toString()
                      ,category.getText().toString(),Tamt.getText().toString(),Ramt.getText().toString(),
                      "0","Active","");
            }
        });

*/
    }

    void generate_qr(String value){
        if(value.isEmpty()){
            //qr_value.setError("Value Required!");
        }
        else{
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

            try {
                String directory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(directory +"/DCIM/");
                if(!folder.exists()) //create folder if doesn't exist
                    folder.mkdir();

                String mPath =  directory + "/DCIM/" + now + ".jpg";


                QRGEncoder qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, 500);
                Bitmap qrBits = qrgEncoder.getBitmap();
                //qr_img.setImageBitmap(qrBits);
                Uri u=getImageUri(NewGoal.this,qrBits);
                qr_uri=u;

                File imageFile = new File(mPath);
                //save the QR Code Image
                FileOutputStream outputStream = null;

                outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                qrBits.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                //notify the gallery of new file created
                MediaScannerConnection.scanFile(NewGoal.this,
                        new String[] { mPath }, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                //....
                            }
                        });

            } catch (Throwable e) {
                e.printStackTrace();
            }

        }
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
            this.image.setImageURI(this.selectedImage_uri);
        }

    }

    void StoreData(String Title,String Description,String Category,String Target_Amount,String Raised_Amount,String No_of_participants,String Status,String QrCode) {

        if (selectedImage_uri != null) {

            String key=data_ref.push().getKey();
            generate_qr(key);
            StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();

            storageReference1 = storageReference1.child("goals/" + key);
            storageReference1.putFile(this.selectedImage_uri)
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


                            StorageReference storageReference2 = FirebaseStorage.getInstance().getReference();

                            storageReference2 = storageReference2.child("goal_qr/" + key);
                            storageReference2.putFile(qr_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                                                      final String uri_dp = uri.toString();
                                                                      String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                      data_ref.child(key).setValue(new goal(key,FirebaseAuth.getInstance().getCurrentUser().getUid(),dp,Title,Description,
                                                                              Category,Target_Amount,Raised_Amount,No_of_participants,Status,uri_dp)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                          @Override
                                                                          public void onSuccess(Void unused) {
                                                                              Intent intent = new Intent();
                                                                              intent.putExtra("uri", dp);
                                                                              setResult(result_code, intent);
                                                                              //dialog.dismiss();
                                                                              finish();
                                                                          }
                                                                      }).addOnFailureListener(new OnFailureListener() {
                                                                          @Override
                                                                          public void onFailure(@NonNull Exception e) {

                                                                          }
                                                                      });
                                                                  }
                                                              });
                                }});




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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    @Override
    public void onBackPressed() {
        finish();
    }

}