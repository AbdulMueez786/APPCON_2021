package com.armughanaslam.appcon_2021.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.armughanaslam.appcon_2021.Model.user;
import com.armughanaslam.appcon_2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopDonors_adapter extends RecyclerView.Adapter<TopDonors_adapter.ViewHolder> {

    private Context c;
    private List<user> ls;
    private int n=0;

    public TopDonors_adapter(Context c, List<user> ls,int n) {
        this.c = c;
        this.ls = ls;
        this.n=n;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.top_donor_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.donor_name.setText(ls.get(position).getName());

        holder.donor_position.setText(String.valueOf(position+1));
      if(n==1){
          holder.donor_position.setVisibility(View.INVISIBLE);
      }

        Picasso.with(c).load(ls.get(position)
                .getUser_profile()).into(holder.donor_profile);

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
/*
        holder.username.setText(ls.get(position).getName());
        holder.lastmessage.setText(ls.get(position).getPost());
        final user u = ls.get(position);

        FirebaseDatabase.getInstance().getReference("UserStatus")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.child(u.getId()).getValue(String.class);

                        System.out.println(status);
                        if (status != null) {
                            if (status.matches("online")) {
                                holder.offline_status.setVisibility(View.GONE);
                                holder.online_status.setVisibility(View.VISIBLE);
                            } else {
                                holder.offline_status.setVisibility(View.VISIBLE);
                                holder.online_status.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        if (u.getUser_profile().equals("default") != true) {
            Picasso.get().load(u.getUser_profile())
                    .into(holder.profile_image);
        } else {
            Picasso.get().load(R.drawable.users)
                    .into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Chat.class);
                intent.putExtra("userid", u.getId());
                intent.putExtra("profile", u.getUser_profile());
                c.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView donor_name,donor_position;
        public com.mikhaellopez.circularimageview.CircularImageView donor_profile;
        public RelativeLayout l;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

 //           l = itemView.findViewById(R.id.l);
            donor_name=itemView.findViewById(R.id.donor_name);
            donor_position=itemView.findViewById(R.id.donor_position);
 //          donor_position=itemView.findViewById(R.id.donor_position);
            donor_profile=itemView.findViewById(R.id.donor_profile);
        }
    }
}
