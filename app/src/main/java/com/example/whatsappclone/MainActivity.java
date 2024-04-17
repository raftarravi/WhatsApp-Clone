package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.whatsappclone.Adapter.FragmentsAdapter;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;

  FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.groupChat) {
            Intent intentGroupChat = new Intent(MainActivity.this,GroupChatActivity.class);
            startActivity(intentGroupChat);
            //Toast.makeText(this, "Group Chat is clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.setting) {

            Intent intentSetting = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentSetting);
            //Toast.makeText(this, "Settings is Clicked", Toast.LENGTH_SHORT).show();

            return true;
        } else if (itemId == R.id.logOut) {

            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
            //Toast.makeText(this, "Logout is clicked", Toast.LENGTH_SHORT).show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        //getActionBar(Toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

    }
}