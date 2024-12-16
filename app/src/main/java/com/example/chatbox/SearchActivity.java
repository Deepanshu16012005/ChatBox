package com.example.chatbox;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbox.adapters.SearchUserRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity {
    EditText searchUserInput;
    ImageButton searchButton,backButton;
    RecyclerView searchRecyclerView;
    SearchUserRecyclerAdapter adapter;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchUserInput=findViewById(R.id.search_user);
        searchUserInput.requestFocus();
        searchButton=findViewById(R.id.search_user_btn);
        searchRecyclerView=findViewById(R.id.search_user_recycler_view);
        backButton=findViewById(R.id.back_search);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        backButton.setOnClickListener(view -> {
            finish();
        });
        searchButton.setOnClickListener(view -> {
            String searchedUser=searchUserInput.getText().toString();
            if(searchedUser.isEmpty()){
               searchUserInput.setError("Invalid Input");
               return;
            }
            setupSearchRecyclerView(searchedUser);
            searchUserInput.setText("");
        });


    }

    void setupSearchRecyclerView(String searchedUser) {
        String lowercaseSearchQuery = searchedUser;
        Query query=databaseReference.child("users").orderByChild("userName").startAt(lowercaseSearchQuery).endAt(lowercaseSearchQuery+"\uf8ff");
        FirebaseRecyclerOptions<UserModel> options=new FirebaseRecyclerOptions.Builder<UserModel>().setQuery(query,UserModel.class).build();
        adapter=new SearchUserRecyclerAdapter(options);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }
}