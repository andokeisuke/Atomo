package com.example.atomo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BoardActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);

        ImageButton letter_Button = findViewById(R.id.letter_button);


        //letterへ遷移
        letter_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), LetterActivity.class);
            startActivity(intent);
        });


    }


}
