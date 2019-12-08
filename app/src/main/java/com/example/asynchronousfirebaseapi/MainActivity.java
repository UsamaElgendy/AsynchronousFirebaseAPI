package com.example.asynchronousfirebaseapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference firebaseRootRef, itemaRef;
    ArrayList itemList;
    final static String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseRootRef = firebaseDatabase.getReference();
        itemaRef = firebaseRootRef.child("Student");
        itemList = new ArrayList<>();

        readData(new FirebaseCallBack() {
            @Override
            public void OnCallback(List<String> list) {
                Log.d(TAG,list.toString());
            }
        });

    }
    private void readData(final FirebaseCallBack firebaseCallBack){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String itemName = ds.child("nationalId").getValue(String.class);
                    itemList.add(itemName);
                }
                firebaseCallBack.OnCallback(itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        itemaRef.addListenerForSingleValueEvent(valueEventListener);


    }
    private interface FirebaseCallBack{
        void OnCallback(List<String> list);
    }
}
