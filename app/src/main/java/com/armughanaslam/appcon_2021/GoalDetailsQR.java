package com.armughanaslam.appcon_2021;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GoalDetailsQR extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView amount_needed;
    TextView amount_collected;
    List<Goal_QR> goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details_qr);

        //Initialize a list of goals
        goals = new ArrayList<Goal_QR>();
        goals.add(new Goal_QR("123","Armughan Aslam",
                "started fund raising to give access to clean water",
                12000,5000));
        goals.add(new Goal_QR("456","Abdul Mueez",
                "started fund raising to give free education",
                15000,9500));
        goals.add(new Goal_QR("789","Muhammad Tufail",
                "wants to raise funds for an orphanage",
                10000,7000));

        //initialize text views
        name = findViewById(R.id.goal_name);
        description = findViewById(R.id.goal_description);
        amount_needed = findViewById(R.id.amount_needed);
        amount_collected = findViewById(R.id.amount_collected);

        //check which data the code matches, set values of text views of that data
        String code = getIntent().getStringExtra("code");

        for(Goal_QR g:goals){
            if(g.getCodeValue().equalsIgnoreCase(code)){
                name.setText(name.getText().toString()+g.getName());
                description.setText(description.getText().toString()+g.getDescription());
                amount_needed.setText(amount_needed.getText().toString()+g.getTotalAmount());
                amount_collected.setText(amount_collected.getText().toString()+g.getAmountCollected());
            }
        }
    }
}