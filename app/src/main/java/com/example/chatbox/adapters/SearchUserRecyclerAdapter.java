package com.example.chatbox.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbox.ChatActivity;
import com.example.chatbox.R;


import com.example.chatbox.utils.AndroidUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.example.chatbox.UserModel;
import com.google.firebase.auth.FirebaseAuth;


public class SearchUserRecyclerAdapter extends FirebaseRecyclerAdapter<UserModel, SearchUserRecyclerAdapter.UserModelViewHolder> {

    public SearchUserRecyclerAdapter(
            @NonNull FirebaseRecyclerOptions<UserModel> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
        if (position >= 0 && position < getItemCount() && model != null) {
            // ... (Your existing code) ...
            holder.userEmail.setText(model.getUserEmail());
            holder.userName.setText(model.getUserName());
            if(model.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                holder.userName.setText(model.getUserName()+" (me)");
            }
            holder.itemView.setOnClickListener(view -> {
                //when clicked on user open chat activity of that user
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                AndroidUtil.passUserModelAsIntent(intent, model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            });
        } else {
            Log.e("RecyclerView", "Invalid position: " + position);
            // Handle the invalid position gracefully
            // (e.g., log the error, display an empty view)
        }


    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);

        return new SearchUserRecyclerAdapter.UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail;
        ImageView userImage;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.recycler_user_name);
            userEmail = itemView.findViewById(R.id.recycler_email);
            userImage = itemView.findViewById(R.id.profile_pic_default);


        }
    }

}
