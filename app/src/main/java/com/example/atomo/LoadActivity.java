package com.example.atomo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadActivity extends AppCompatActivity {

    private MyValue myValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_load);
        myValue = (MyValue) this.getApplication();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CO2");

        myRef.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    myValue.setCO2(Float.parseFloat(String.valueOf(task.getResult().getValue())));

                }
            }
        });


/*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = (String)dataSnapshot.child("value").getValue();

                myValue.setCO2(Float.parseFloat(value1));
                myRef.removeEventListener(this);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });*/





    }

}