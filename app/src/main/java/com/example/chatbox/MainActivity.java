package com.example.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    BottomNavigationView bottomNavigationView;
    ImageButton search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //layout overlaping with status bar solution
        View decoreView =getWindow().getDecorView();
        decoreView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View view, @NonNull WindowInsets windowInsets) {
                int left=windowInsets.getSystemWindowInsetLeft();
                int right=windowInsets.getSystemWindowInsetRight();
                int top=windowInsets.getSystemWindowInsetTop();
                int bottom=windowInsets.getSystemWindowInsetBottom();
                view.setPadding(left,top,right,bottom);


                return windowInsets.consumeSystemWindowInsets();
            }
        });

        search=findViewById(R.id.search_main);
        chatFragment=new ChatFragment();
        profileFragment=new ProfileFragment();
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        search.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_chats){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatFragment).commit();

                }
                if(item.getItemId()==R.id.menu_person){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profileFragment).commit();
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chats);
    }
}