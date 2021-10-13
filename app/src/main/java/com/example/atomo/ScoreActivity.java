package com.example.atomo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private int ite = 0;
    private MyValue myValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myValue = (MyValue) this.getApplication();

        Intent i = getIntent();
        String madorinum = i.getStringExtra("madorinum");

        setContentView(R.layout.activity_score);

        ImageButton start_Button = findViewById(R.id.start_button);
        ImageButton reset_Button = findViewById(R.id.reset_button);
        TextView score = findViewById(R.id.score);
        start_Button.setImageResource(R.drawable.start_icon);


        //letterへ遷移
        start_Button.setOnClickListener(v -> {

            ite++;


            if(ite == 0){
                start_Button.setImageResource(R.drawable.start_icon);
                score.setText("");


            }else if(ite == 1){
                start_Button.setImageResource(R.drawable.socring_icon);
                score.setText("");

            }else if(ite == 2){
                start_Button.setImageResource(R.drawable.finish_icon);
                score.setText("26");

                Time time = new Time("Asia/Tokyo");
                time.setToNow();
                String date = time.year + "\n"+ (time.month+1) + "/" + time.monthDay;
                String[] diagnose = new String[]{date,madorinum,"26"};
                myValue.addDisagnose(diagnose);
            }



        });

        reset_Button.setOnClickListener(view -> {

            ite = 0;
            start_Button.setImageResource(R.drawable.start_icon);
            score.setText("");
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
}
