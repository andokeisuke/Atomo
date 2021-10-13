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
    private ImageButton atomo;
    private ImageView climate_background;
    private ImageView window_background;

    private float[] th = {800,900,1000,1200};

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference in = database.getReference("in");
    DatabaseReference out = database.getReference("out");


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
        climate_background = findViewById(R.id.climate_background);
        window_background = findViewById(R.id.window_background);


        //状態の更新
        float CO2 = myValue.getCO2();
        JudgeStatus(CO2,(int)myValue.getOut_status()[0]);

        //stausへ遷移
        atomo.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), StatusActivity.class);
            startActivity(intent);
        });

        ImageButton diagnose_Button = findViewById(R.id.diagnose_button);
        ImageButton log_Button = findViewById(R.id.log_button);
        ImageButton mission_Button = findViewById(R.id.mission_button);
        ImageButton board_Button = findViewById(R.id.board_button);
        ImageButton home_Button = findViewById(R.id.home_button);
        TextView in_tempture = findViewById(R.id.in_tempture);
        TextView in_humidity = findViewById(R.id.in_humidity);
        TextView out_tempture = findViewById(R.id.out_tempture);
        TextView out_humidity = findViewById(R.id.out_humidity);

        //boardへ遷移
        board_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), BoardActivity.class);
            startActivity(intent);
        });

        //scoreへ遷移
        diagnose_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), ScoreSelectActivity.class);
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

        //homeへ遷移
        home_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        });



        in.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = (String)dataSnapshot.child("CO2").getValue();
                //String value2 = (String)dataSnapshot.child("test2").getValue();
                myValue.setCO2(Float.parseFloat(value1));
                JudgeStatus(myValue.getCO2(),(int)myValue.getOut_status()[0]);

                String tempture = (String) dataSnapshot.child("tempture").getValue();
                String humidity = (String) dataSnapshot.child("humidity").getValue();
                myValue.setIn_status(Float.parseFloat(tempture),Float.parseFloat(humidity));
                in_tempture.setText(tempture);
                in_humidity.setText(humidity);

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        out.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String climate = (String)dataSnapshot.child("climate").getValue();
                String tempture = (String) dataSnapshot.child("tempture").getValue();
                String humidity = (String) dataSnapshot.child("humidity").getValue();
                myValue.setOut_status(Integer.parseInt(climate),Float.parseFloat(tempture),Float.parseFloat(humidity));
                out_tempture.setText(tempture);
                out_humidity.setText(humidity);
                JudgeStatus(myValue.getCO2(),(int)myValue.getOut_status()[0]);



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

    public void JudgeStatus(float CO2,int climate){


        int Status = CO2Status(CO2);
        myValue.setStatus(Status);


        if(Status == 0){
            background.setImageResource(R.drawable.main_background100);
            comfort_var.setImageResource(R.drawable.comfort_var100);
            atomo.setImageResource(R.drawable.atomo_love);
            if(climate==0){
                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.GONE);

            }else if(climate==1){

                climate_background.setVisibility(View.VISIBLE);
                climate_background.setImageResource(R.drawable.climate_cloudy);
                window_background.setVisibility(View.GONE);

            }else if(climate==2){

                climate_background.setVisibility(View.VISIBLE);
                climate_background.setImageResource(R.drawable.climate_rain);
                window_background.setVisibility(View.GONE);

            }


        }else if(Status == 1){
            background.setImageResource(R.drawable.main_background75);
            comfort_var.setImageResource(R.drawable.comfort_var75);
            atomo.setImageResource(R.drawable.atomo_nikoniko);
            if(climate==0){
                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.GONE);


            }else if(climate==1){

                climate_background.setVisibility(View.VISIBLE);
                climate_background.setImageResource(R.drawable.climate_cloudy);
                window_background.setVisibility(View.GONE);

            }else if(climate==2){

                climate_background.setVisibility(View.VISIBLE);
                climate_background.setImageResource(R.drawable.climate_rain);
                window_background.setVisibility(View.GONE);

            }

        }else if(Status == 2){
            background.setImageResource(R.drawable.main_background50);
            comfort_var.setImageResource(R.drawable.comfort_var50);
            atomo.setImageResource(R.drawable.atomo_default);

            if(climate==0){
                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_sun);


            }else if(climate==1){

                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_cloud);

            }else if(climate==2){

                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_rain);

            }

        }else if(Status == 3){
            background.setImageResource(R.drawable.main_background25);
            comfort_var.setImageResource(R.drawable.comfort_var25);
            atomo.setImageResource(R.drawable.atomo_syonbori);

            if(climate==0){
                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_sun);


            }else if(climate==1){

                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_cloud);

            }else if(climate==2){

                climate_background.setVisibility(View.GONE);
                window_background.setVisibility(View.VISIBLE);
                window_background.setImageResource(R.drawable.window_rain);

            }

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