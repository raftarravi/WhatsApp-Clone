package com.example.whatsappclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.Adapter.UsersAdapter;
import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chatFragment extends Fragment {


    public chatFragment() {
        // Required empty public constructor
    }
    FragmentChatBinding binding;

    ArrayList<Users> list;
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list = new ArrayList<>();

        binding = FragmentChatBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        UsersAdapter adapter = new UsersAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list != null) {
                    list.clear(); // This is safe now
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    Log.i("key", String.valueOf(users));

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                      if (list != null) {
                          list.add(users); // Add item only if myList is not null
                      }
                    }

                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}