package com.example.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbox.models.ChatMessageModel;
import com.example.chatbox.models.ChatroomModel;
import com.example.chatbox.utils.AndroidUtil;
import com.example.chatbox.utils.FirebaseUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    UserModel othermodel;
    TextView UserName;
    EditText messageInput;
    ChatroomModel chatroomModel;
    ImageButton sendButton,backButton;
    RecyclerView recyclerView;
    String chatRoomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        othermodel = AndroidUtil.getUserModelFromIntent(getIntent());
        UserName = findViewById(R.id.chat_inside_set_username);
        messageInput = findViewById(R.id.msg_input_chat_activity);
        sendButton = findViewById(R.id.send_btn_chat_activity);
        backButton = findViewById(R.id.back_chat_inside_activity);
        //recyclerView = findViewById(R.id.recycler_view_chat_activity);
        chatRoomId=FirebaseUtils.getChatroomId(FirebaseUtils.getCurrentUserId(),othermodel.getUserId());

        UserName.setText(othermodel.getUserName());
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        getOrCreateChatRoomModel();
        sendButton.setOnClickListener(view -> {
            String msg=messageInput.getText().toString().trim();
            if(msg.isEmpty()){
                return;
            }
            sendMessageToUser(msg);
        });
    }

    void sendMessageToUser(String msg) {

    }

    void getOrCreateChatRoomModel() {
        FirebaseUtils.chatroomref(chatRoomId).get().addOnCompleteListener(task -> {
            chatroomModel=task.getResult().getValue(ChatroomModel.class);
            if(chatroomModel==null){
                // first time chat
                chatroomModel=new ChatroomModel(
                        chatRoomId,
                        Arrays.asList(FirebaseUtils.getCurrentUserId(),othermodel.getUserId()),"");
                FirebaseUtils.chatroomref(chatRoomId).setValue(chatroomModel);
            }
        });
    }
}