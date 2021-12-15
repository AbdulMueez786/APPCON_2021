package com.armughanaslam.appcon_2021.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.armughanaslam.appcon_2021.Fragments.QR;
import com.armughanaslam.appcon_2021.GoalDetailsQR;
import com.armughanaslam.appcon_2021.Model.goal;
import com.armughanaslam.appcon_2021.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

public class Scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView code_scanner;
    TextView scanned_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scanned_text = findViewById(R.id.scanned_text);

        code_scanner = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, code_scanner);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scanned_text.setText("Scanned Code: "+result.getText());

                        //make the device vibrate
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(500);
                        }

                        //open new activity
                        final goal[] g = new goal[1];
                        FirebaseDatabase.getInstance().getReference("goals").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                                System.out.println(result.getText());
                                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                                    if(snapshot.getKey().matches(result.getText())){
                                        g[0] =snapshot.getValue(goal.class);
                                        System.out.println("It worked");
                                    }
                                }

                                FirebaseDatabase.getInstance().getReference("users").child(g[0].getCreater_id()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        Intent i = new Intent(Scanner.this, GoalDetail.class);
                                        i.putExtra("goal_id", g[0].getId());
                                        i.putExtra("goal_title",g[0].getTitle());
                                        i.putExtra("goal_status",g[0].getStatus());
                                        i.putExtra("goal_image",g[0].getImage());
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
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });



                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}