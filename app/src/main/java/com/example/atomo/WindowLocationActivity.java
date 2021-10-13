package com.example.atomo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.text.format.Time;
import java.util.ArrayList;
import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;


public class WindowLocationActivity extends AppCompatActivity {

    private MyValue myValue;
    private int SelectNum = 0;
    private LinearLayout madoriList;
    private List<View> SeekBars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowlocation);

        myValue = (MyValue) this.getApplication();
        madoriList = (LinearLayout) findViewById(R.id.madoriList);
        Button add_Button = findViewById(R.id.add_button);
        ImageButton start_Button = findViewById(R.id.start_button);

        add_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), EditWindowLocationActivity.class);
            startActivity(intent);


        });

        start_Button.setOnClickListener(v -> {


            ConstraintLayout windows = findTheViewById(R.id.windowList);

            List<float[]> temp_Madori_list = new ArrayList<>();

            for(int i =0;i<myValue.getMadori_list().get(SelectNum).size();i++){
                View temp_view = windows.getChildAt(2+i);
                CircularSeekBar seekBar = (CircularSeekBar) temp_view.findViewById(R.id.seek_bar);
                float[] temp = new float[]{myValue.getMadori_list().get(SelectNum).get(i)[0],myValue.getMadori_list().get(SelectNum).get(i)[1],(int)(seekBar.getProgress())};

                temp_Madori_list.add(temp);

            }

            myValue.setMadori(temp_Madori_list,SelectNum);



            String madorinum = String.valueOf(SelectNum+1);
            Intent intent = new Intent(getApplication(), ScoreActivity.class);
            intent.putExtra("madorinum", madorinum);
            startActivity(intent);


        });

        for(int i = 0;i<myValue.getMadori_list().size();i++){

            View view1 = getLayoutInflater().inflate(R.layout.madori_list, null);
            ImageView waku = view1.findViewById(R.id.waku);
            MadoriView canvas1 = view1.findViewById(R.id.canvas);
            Button touch = view1.findViewById(R.id.touch);
            TextView madorinum = view1.findViewById(R.id.madorinum);
            waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_off));
            canvas1.setPoint(myValue.getMadori_list().get(i));
            madorinum.setText(String.valueOf(i+1));
            madoriList.addView(view1,i);

            touch.setOnClickListener(view ->{

                View vg = (View) view.getParent();
                int index = madoriList.indexOfChild(vg);


                for(int j = 0;j<madoriList.getChildCount()-1;j++){

                    View temp_view = madoriList.getChildAt(j);
                    ImageView temp_waku = temp_view.findViewById(R.id.waku);
                    temp_waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_off));

                }
                waku.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.madori_waku_on));

                SelectNum = index;

                WindowView windowView = findTheViewById(R.id.windowview);
                windowView.setPoint(myValue.getMadori_list().get(index));
                ConstraintLayout window = findTheViewById(R.id.windowList);
                for(int l = 0;l<SeekBars.size();l++){
                    window.removeView(SeekBars.get(l));
                }
                SeekBars = new ArrayList<>();

                for(int k = 0;k<myValue.getMadori_list().get(index).size();k++){


                    View customSeekbar = getLayoutInflater().inflate(R.layout.custom_circleseekbar, null);
                    window.addView(customSeekbar);
                    customSeekbar.setTranslationX(myValue.getMadori_list().get(index).get(k)[0]);
                    customSeekbar.setTranslationY(myValue.getMadori_list().get(index).get(k)[1]);
                    SeekBars.add(customSeekbar);
                    TextView percent = customSeekbar.findViewById(R.id.percent);
                    CircularSeekBar seekBar = (CircularSeekBar) customSeekbar.findViewById(R.id.seek_bar);

                    seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                            percent.setText(String.valueOf((int)(progress))+"%");

                        }

                        @Override
                        public void onStopTrackingTouch(CircularSeekBar seekBar) {


                        }

                        @Override
                        public void onStartTrackingTouch(CircularSeekBar seekBar) {


                        }
                    });



                }













            } );


        }






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
