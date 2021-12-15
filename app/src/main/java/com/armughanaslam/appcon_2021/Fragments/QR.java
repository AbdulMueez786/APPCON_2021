package com.armughanaslam.appcon_2021.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.armughanaslam.appcon_2021.Activity.GoalDetail;
import com.armughanaslam.appcon_2021.Activity.Home;
import com.armughanaslam.appcon_2021.Activity.Scanner;
import com.armughanaslam.appcon_2021.QRA;
import com.armughanaslam.appcon_2021.R;


public class QR extends Fragment {
    private View view;
    private com.airbnb.lottie.LottieAnimationView v1;
    public com.airbnb.lottie.LottieAnimationView v2;
    private Button scan_from_gallery,scan_from_camera;
    private ImageButton back_arrow,top_donors;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
             // Inflate the layout for this fragment
             view=inflater.inflate(R.layout.fragment_q_r, container, false);
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
             v1=view.findViewById(R.id.animation_view_1);
             v2=view.findViewById(R.id.animation_view_2);

             scan_from_gallery=view.findViewById(R.id.scan_from_gallery);
             scan_from_camera=view.findViewById(R.id.scan_from_camera);

             scan_from_gallery.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     v1.setVisibility(View.GONE);
                     ((Home)getActivity()).scanGallery(((Home)getActivity()).someActivityResultLauncher);
                     v2.setVisibility(View.VISIBLE);
                 }
             });

             scan_from_camera.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     v1.setVisibility(View.GONE);
                     v2.setVisibility(View.VISIBLE);
                     startActivity(new Intent(getActivity(), Scanner.class));
                 }
             });

        return view;
    }


}