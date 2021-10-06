package com.example.atomo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;


public class WindowLocationActivity extends AppCompatActivity {

    private MyValue myValue;
    private int SelectNum = 0;
    private LinearLayout madoriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlocation);

        myValue = (MyValue) this.getApplication();
        madoriList = (LinearLayout) findViewById(R.id.madoriList);
        Button add_Button = findViewById(R.id.add_button);

        add_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), EditWindowLocationActivity.class);
            startActivity(intent);


        });

        for(int i = 0;i<myValue.getMadori_list().size();i++){

            View view1 = getLayoutInflater().inflate(R.layout.madori_list, null);
            ImageView waku = view1.findViewById(R.id.waku);
            MadoriView canvas1 = view1.findViewById(R.id.canvas);
            Button touch = view1.findViewById(R.id.touch);
            waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_off));
            canvas1.setPoint(myValue.getMadori_list().get(i));
            madoriList.addView(view1,0);

            touch.setOnClickListener(view ->{


                for(int j = 0;j<madoriList.getChildCount()-1;j++){

                    View temp_view = madoriList.getChildAt(j);
                    ImageView temp_waku = temp_view.findViewById(R.id.waku);
                    temp_waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_off));

                }
                waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_on));

            } );


        }





//        LinearLayout canvas = (LinearLayout) findViewById(R.id.canvas);
//
//        View view = getLayoutInflater().inflate(R.layout.custom_circleseekbar, null);
//        canvas.addView(view);
//        final TextView textEvent = view.findViewById(R.id.text_event);
//        final TextView textProgress = view.findViewById(R.id.text_progress);
//        CircularSeekBar seekBar = (CircularSeekBar) view.findViewById(R.id.seek_bar);
//
//
//        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
//                String message = String.format("Progress changed to %.2f, fromUser %s", progress, fromUser);
//                Log.d("Main", message);
//                textProgress.setText(message);
//            }
//
//            @Override
//            public void onStopTrackingTouch(CircularSeekBar seekBar) {
//                Log.d("Main", "onStopTrackingTouch");
//                textEvent.setText("");
//            }
//
//            @Override
//            public void onStartTrackingTouch(CircularSeekBar seekBar) {
//                Log.d("Main", "onStartTrackingTouch");
//                textEvent.setText("touched | ");
//            }
//        });
    }

    @Override
    protected void onResume() {

        super.onResume();





    }

    @SuppressWarnings("unchecked")
    private <T> T findTheViewById(@IdRes int id) {
        return (T) super.findViewById(id);
    }

}
