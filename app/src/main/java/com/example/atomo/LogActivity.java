package com.example.atomo;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;


public class LogActivity extends AppCompatActivity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log);
        TextView textView = findViewById(R.id.test);

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





        mChart = findViewById(R.id.lineChart);

        MyMarkerView mv = new MyMarkerView(this, R.layout.marker_view);
        mChart.setMarker(mv);
        mChart.setPinchZoom(false);


                // Grid背景色
        //mChart.setDrawGridBackground(true);

        // no description text
        mChart.getDescription().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        // Grid縦軸を破線
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        // Y軸最大最小設定
        leftAxis.setAxisMaximum(150f);
        leftAxis.setAxisMinimum(0f);
        // Grid横軸を破線
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //leftAxis.setDrawZeroLine(true);

        // 右側の目盛り
        mChart.getAxisRight().setEnabled(false);






        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ppm");

        //myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Time time = new Time("Asia/Tokyo");
                time.setToNow();
                int hour = time.hour;
                int min = time.minute;
                int sec = 0;
                if(sec<=15) sec = 0;
                else if (sec>15&&sec<=30) sec = 15;
                else if (sec>30&&sec<=45) sec = 30;
                else if (sec>45) sec = 45;

                List<String> date_labels = new ArrayList<String>();
                date_labels.add("0:0:0");
                int temp_h = 0;
                int temp_m = 0;
                int temp_s = 0;

                for (int i = 0; i<6000; i++) {

                    temp_s = temp_s + 15;

                    if(temp_s == 60){

                        temp_m++;
                        temp_s = 0;
                    }

                    if(temp_m == 60){

                        temp_h++;
                        temp_m = 0;
                    }

                    if(temp_h == 24){
                        temp_h = 0;
                    }


                    date_labels.add( temp_h + ":" + temp_m + ":" + temp_s);

                    if((hour==temp_h)&&(min == temp_m)&&(sec == temp_s)) break;

                }

                List<Integer> ppm = new ArrayList<>();

                for (int i = 0; i < date_labels.size(); i++) {
                    String value = (String) dataSnapshot.child(date_labels.get(i)).getValue();
                    if(value != null) ppm.add(Integer.parseInt(value));
                    else ppm.add(Integer.parseInt((String) dataSnapshot.child(date_labels.get(i-1)).getValue()));

                }







                //String value1 = (String)dataSnapshot.child("value").getValue();
                //String value2 = (String)dataSnapshot.child("test2").getValue();


                // add data
                //setData(Integer.parseInt(value1));
                setData(ppm,date_labels);
                mChart.invalidate();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });




    }

    private void setData(List<Integer> ppm,List<String> time) {
        // Entry()を使ってLineDataSetに設定できる形に変更してarrayを新しく作成



        YAxis leftAxis = mChart.getAxisLeft();
        // Y軸最大最小設定
        leftAxis.setAxisMaximum(4500);
        leftAxis.setAxisMinimum(400);

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < ppm.size(); i++) {
            final boolean add = values.add(new Entry(i, ppm.get(i), null, null));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet");

            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawVerticalHighlightIndicator(false);

            set1.setDrawIcons(false);
            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(0f);
            set1.setDrawFilled(false);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData lineData = new LineData(dataSets);

            // set data
            mChart.setData(lineData);
        }
    }

}