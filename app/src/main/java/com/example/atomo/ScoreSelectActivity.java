package com.example.atomo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scoreselect);

        ImageButton score_Button = findViewById(R.id.score_button);
        ImageButton past_score_Button = findViewById(R.id.past_score_button);


        //letterへ遷移
        score_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), WindowLocationActivity.class);
            startActivity(intent);
        });

        past_score_Button.setOnClickListener(v -> {

            //Intent intent = new Intent(getApplication(), LetterActivity.class);
            //startActivity(intent);
        });


    }
}
