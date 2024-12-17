package com.example.chatbox.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbox.ChatActivity;
import com.example.chatbox.R;


import com.example.chatbox.models.ChatMessageModel;
import com.example.chatbox.utils.AndroidUtil;
import com.example.chatbox.utils.FirebaseUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;


public class ChatRecyclerAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatMessageModelViewHolder> {

    public ChatRecyclerAdapter(
            @NonNull FirebaseRecyclerOptions<ChatMessageModel> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ChatMessageModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if (position >= getItemCount()) {
            Log.e("RecyclerViewError", "Invalid position: " + position);
            return;
        }
        if(model.getSenderId().equals(FirebaseUtils.getCurrentUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatMessageTextview.setText(model.getMessage());

        }else{
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatMessageTextview.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatMessageModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_recycler_row, parent, false);

        return new ChatRecyclerAdapter.ChatMessageModelViewHolder(view);
    }

    class ChatMessageModelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatMessageTextview, rightChatMessageTextview;


        public ChatMessageModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout=itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout=itemView.findViewById(R.id.right_chat_layout);
            leftChatMessageTextview=itemView.findViewById(R.id.left_chat_text_view);
            rightChatMessageTextview=itemView.findViewById(R.id.right_chat_text_view);
        }
    }

}
