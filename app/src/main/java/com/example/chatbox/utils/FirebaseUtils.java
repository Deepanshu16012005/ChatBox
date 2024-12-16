package com.example.chatbox.utils;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

public class FirebaseUtils {
    public static String getCurrentUserId(){
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }
    private static final String CHATROOMS_NODE = "chatrooms";

    public static DatabaseReference chatroomref(String chatroomId) {
        return FirebaseDatabase.getInstance().getReference().child(CHATROOMS_NODE).child(chatroomId);
    }

    public static DatabaseReference chatroomMessageReference(String chatroomId) {
        return chatroomref(chatroomId).child("messages");
    }

    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

}
