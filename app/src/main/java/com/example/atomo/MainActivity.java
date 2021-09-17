package com.example.atomo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyValue myValue;
    private ImageView background;
    private ImageView comfort_var;
    private TextView lifeday_num;
    private ImageView atomo;

    private float[] th = {800,900,1000,1200};

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CO2");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初期化
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myValue = (MyValue) this.getApplication();
        background = findViewById(R.id.main_background);
        comfort_var = findViewById(R.id.comfort_var);
        lifeday_num = findViewById(R.id.lifeday_num);
        atomo = findViewById(R.id.atomo);
        ImageButton status_Button = findViewById(R.id.status_button);
        ImageButton log_Button = findViewById(R.id.log_button);
        ImageButton mission_Button = findViewById(R.id.mission_button);

        //状態の更新
        float CO2 = myValue.getCO2();
        JudgeStatus(CO2);

        //stausへ遷移
        status_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), StatusActivity.class);
            startActivity(intent);
        });

        //logへ遷移
        log_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), LogActivity.class);
            startActivity(intent);
        });

        //missionへ遷移
        mission_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), MissionActivity.class);
            startActivity(intent);
        });



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = (String)dataSnapshot.child("value").getValue();
                //String value2 = (String)dataSnapshot.child("test2").getValue();
                myValue.setCO2(Float.parseFloat(value1));
                JudgeStatus(myValue.getCO2());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        String msg = token;
                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/



    }

    @Override
    public void onResume() {
        super.onResume();
        //myRef.addValueEventListener(Listener);

        //JudgeStatus(myValue.getCO2());
    }

    @Override
    public void onStop() {


        super.onStop();
        //JudgeStatus(myValue.getCO2());


    }

    public void JudgeStatus(float CO2){


        int Status = CO2Status(CO2);
        myValue.setStatus(Status);


        if(Status == 0){
            background.setImageResource(R.drawable.main_background100);
            comfort_var.setImageResource(R.drawable.comfort_var100);
            atomo.setImageResource(R.drawable.atomo_love);


        }else if(Status == 1){
            background.setImageResource(R.drawable.main_background75);
            comfort_var.setImageResource(R.drawable.comfort_var75);
            atomo.setImageResource(R.drawable.atomo_nikoniko);

        }else if(Status == 2){
            background.setImageResource(R.drawable.main_background50);
            comfort_var.setImageResource(R.drawable.comfort_var50);
            atomo.setImageResource(R.drawable.atomo_default);

        }else if(Status == 3){
            background.setImageResource(R.drawable.main_background25);
            comfort_var.setImageResource(R.drawable.comfort_var25);
            atomo.setImageResource(R.drawable.atomo_syonbori);

        }



    }

    public  int CO2Status(float CO2){

        int Status = 0;

        if(CO2<=th[0]){
            Status = 0;

        }else if(CO2>th[0]&&CO2<=th[1]){
            Status = 1;

        }else if(CO2>th[1]&&CO2<=th[2]){
            Status = 2;

        }else if(CO2>th[2]){
            Status = 3;

        }

        return Status;
    }





}