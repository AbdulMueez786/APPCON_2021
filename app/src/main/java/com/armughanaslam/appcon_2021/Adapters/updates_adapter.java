package com.armughanaslam.appcon_2021.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.armughanaslam.appcon_2021.Model.update;
import com.armughanaslam.appcon_2021.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class updates_adapter extends RecyclerView.Adapter<updates_adapter.ViewHolder> {

    public static final int msg_type_left = 0;

    public static final int msg_type_right = 1;


    private Context c;
    private List<update> ls;
    private String image_url;
    private FirebaseUser fuser;

    public updates_adapter(Context c, List<update> ls, String image_url) {
        this.c = c;
        this.ls = ls;
        this.image_url = image_url;
    }

    @NonNull
    @Override
    public updates_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  if (viewType == msg_type_right) {
      //      View view = LayoutInflater.from(c).inflate(R.layout.chat_item_right, parent, false);
      //      return new updates_adapter.ViewHolder(view);
      //  } else {
            View view = LayoutInflater.from(c).inflate(R.layout.chat_item_left, parent, false);
            return new updates_adapter.ViewHolder(view);
       // }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final update chat = ls.get(position);
        if (ls.get(position).getType().matches("text") == true) {

            holder.show_message.setVisibility(View.VISIBLE);
            holder.show_image.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);

            holder.show_time.setVisibility(View.VISIBLE);
            holder.show_message.setText(chat.getData());
            holder.show_time.setText(chat.getTime());
            holder.l.setVisibility(View.GONE);

            holder.task_ll.setVisibility(View.GONE);
            holder.task.setText(chat.getData());
            holder.task_date.setText("09/10/2021");
            holder.task_date.setText(chat.getTime());

        } else if (ls.get(position).getType().matches("task") == true) {

            holder.show_message.setVisibility(View.GONE);
            holder.show_image.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);

            holder.show_time.setVisibility(View.GONE);
            holder.show_message.setText(chat.getData());
            holder.show_time.setText(chat.getTime());
            holder.l.setVisibility(View.GONE);

            holder.task_ll.setVisibility(View.VISIBLE);
            holder.task.setText(chat.getData());
            holder.task_date.setText("09/10/2021");
            holder.task_date.setText(chat.getTime());

        } else {

            holder.l.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(chat.getTime());
            holder.show_message.setVisibility(View.GONE);
            holder.show_image.setVisibility(View.VISIBLE);
            holder.show_time.setVisibility(View.GONE);
            Picasso.with(c).load(chat.getData())
                    .into(holder.show_image);

            holder.task_ll.setVisibility(View.GONE);
            holder.task.setText(chat.getData());
            holder.task_date.setText("09/10/2021");
            holder.task_date.setText(chat.getTime());

        }

        holder.show_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                dialog.setMessage("Are you sure you want to delete this Conversation ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.show_message.setText("You deleted this message");
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("type", "text");
                        hm.put("text", "You deleted this message");
                        FirebaseDatabase.getInstance().getReference("Chats").child(chat.getId()).updateChildren(hm);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog d = dialog.create();
                d.show();
                return false;
            }
        });
        holder.show_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(c);

                dialog.setMessage("Are you sure you want to delete this Conversation ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.show_message.setText("You deleted this message");
                        holder.show_message.setVisibility(View.VISIBLE);
                        holder.show_image.setVisibility(View.GONE);
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("type", "text");
                        hm.put("text", "You deleted this message");
                        FirebaseDatabase.getInstance().getReference("Chats").child(chat.getId()).updateChildren(hm);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog d = dialog.create();
                d.show();
                return false;
            }
        });


        /*
        if (ls.get(position).getSender_id().equals(fuser.getUid()) != true) {
            if (image_url.equals("default") != true) {
                FirebaseDatabase.getInstance().getReference("users")
                        .child(chat.getSender_id()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Picasso.with(c).load(dataSnapshot.child("user_profile").getValue(String.class))
                                .into(holder.profile_image);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Picasso.with(c).load(R.drawable.user)
                        .into(holder.profile_image);
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView show_image;
        public TextView show_message, time, show_time, task, task_date, task_time;
        public CircleImageView profile_image;
        public LinearLayout l, task_ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.task);
            task_date = itemView.findViewById(R.id.task_date);
            task_time = itemView.findViewById(R.id.task_time);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_image = itemView.findViewById(R.id.show_image);
            time = itemView.findViewById(R.id.time);
            show_time = itemView.findViewById(R.id.show_time);
            l = itemView.findViewById(R.id.l);
            task_ll = itemView.findViewById(R.id.task_ll);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        //if (ls.get(position).getSender_id().equals(fuser.getUid())) {
            return msg_type_right;
        //} else {
         //   return msg_type_left;
        //}
    }
}


