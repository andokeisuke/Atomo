package com.example.atomo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class ScoreActivity extends AppCompatActivity {


    private MyValue myValue;
    private Timer timer;
    private CountUpTimerTask timerTask;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long count;
    private ImageButton start_Button;
    private TextView score;
    private TextView time;
    private ProgressBar progressBar;
    private String madorinum;
    private float nowppm;
    private float nextppm;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myValue = (MyValue) this.getApplication();

        Intent i = getIntent();
        madorinum = i.getStringExtra("madorinum");

        setContentView(R.layout.activity_score);

        start_Button = findViewById(R.id.start_button);
        ImageButton reset_Button = findViewById(R.id.reset_button);
        score = findViewById(R.id.score);
        time = findViewById(R.id.time);
        start_Button.setImageResource(R.drawable.start_icon);

        progressBar = findViewById(R.id.progressBarToday);
        progressBar.setProgress(0);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CO2");




        //letterへ遷移
        start_Button.setOnClickListener(v -> {


            start_Button.setImageResource(R.drawable.socring_icon);
            score.setText("");
            time.setText("");
            progressBar.setProgress(0);

            myRef.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        nowppm = Float.parseFloat(String.valueOf(task.getResult().getValue()));


                    }
                }
            });

            // Timer インスタンスを生成
            timer = new Timer();

            // TimerTask インスタンスを生成
            timerTask = new CountUpTimerTask();

            // スケジュールを設定 100msec
            // public void schedule (TimerTask task, long delay, long period)
            timer.schedule(timerTask, 0, 1000);

        });

        reset_Button.setOnClickListener(view -> {

            start_Button.setImageResource(R.drawable.start_icon);
            score.setText("");
            time.setText("");
            progressBar.setProgress(0);

            count = 0;
            if(null != timer){
                // Cancel
                timer.cancel();
                timer = null;
            }
        });

        ImageButton diagnose_Button = findViewById(R.id.diagnose_button);
        ImageButton log_Button = findViewById(R.id.log_button);
        ImageButton mission_Button = findViewById(R.id.mission_button);
        ImageButton board_Button = findViewById(R.id.board_button);
        ImageButton home_Button = findViewById(R.id.home_button);

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



    }

    class CountUpTimerTask extends TimerTask {
        @Override
        public void run() {
            // handlerを使って処理をキューイングする
            handler.post(new Runnable() {
                public void run() {
                    count++;
                    progressBar.setProgress((int)(count));

                    if(count > 10){
                        start_Button.setImageResource(R.drawable.finish_icon);
                        progressBar.setProgress(0);

                        database.getReference("CO2").child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    nextppm = Float.parseFloat(String.valueOf(task.getResult().getValue()));


                                }
                            }
                        });

                        nextppm = 500;
                        score.setText(String.valueOf(CalScore(nowppm,nextppm)));
                        time.setText(String.valueOf((int)(60*(100-CalScore(nowppm,nextppm))/100)));

                        Time time = new Time("Asia/Tokyo");
                        time.setToNow();
                        String date = time.year + "\n"+ (time.month+1) + "/" + time.monthDay;
                        String[] diagnose = new String[]{date,madorinum,"26"};
                        myValue.addDisagnose(diagnose);
                        count = 0;

                        if(null != timer){
                            // Cancel
                            timer.cancel();
                            timer = null;
                        }

                    }

                }
            });
        }
    }

    private int CalScore(float nowppm,float nextppm){

        int result = 0;

        float roomsize = (float) (myValue.getRoom_size()*1.62*2f);//[m3]
        float peoplenum = (float)(myValue.getPeople_num());
        float time = 1/6;
        float humanppm = 20000;
        float outppm = 413;

        float predictppm = (nowppm * roomsize + humanppm * peoplenum * time)/roomsize; //[ppm]

        float kankiryo = (predictppm - nextppm)*roomsize/(predictppm-outppm);

        result = (int)(kankiryo/roomsize*100);


        return result;


    }
}
