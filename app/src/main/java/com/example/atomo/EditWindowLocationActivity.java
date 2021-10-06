package com.example.atomo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EditWindowLocationActivity extends AppCompatActivity {


    private MyValue myValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editwindowlocation);
        myValue = (MyValue) this.getApplication();

        Button reset_Button = findViewById(R.id.reset_button);
        Button register_Button = findViewById(R.id.register_button);
        TouchView touchView = findViewById(R.id.touchview);

        reset_Button.setOnClickListener(view -> {

            touchView.clearPoints();

        });

        register_Button.setOnClickListener(view -> {

            myValue.addMadori(touchView.getPoints());

            Intent intent = new Intent(getApplication(), WindowLocationActivity.class);
            startActivity(intent);

        });
    }

}




