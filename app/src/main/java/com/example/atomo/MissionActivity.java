package com.example.atomo;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MissionActivity extends AppCompatActivity {


    private static final String[] texts = {
            "Daily Mission",
            "充実度70以上を維持（Daily）",
            "CO2濃度800ppm以下を維持（Daily）",
            "温度28度以下を維持（Daily）",
            "湿度50％以下を維持（Daily）",

            "Weekly Mission",
            "充実度70以上を維持（Weekly）",
            "CO2濃度800ppm以下を維持（Weekly）",
            "温度28度以下を維持（Weekly）",
            "湿度50％以下を維持（Weekly）",

            "Monthly Mission",
            "充実度70以上を維持（Monthly）",
            "CO2濃度800ppm以下を維持（Monthly）",
            "温度28度以下を維持（Monthly）",
            "湿度50％以下を維持（Monthly）"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mission);

        Button returnButton = findViewById(R.id.return_button);
//        returnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        // lambda式
        returnButton.setOnClickListener(v -> finish());

        ListView listView = findViewById(R.id.listView);

        List<MyData> list = new ArrayList<>();



        for(int i = 0;i<texts.length;i++){

            MyData myData = new MyData();
            myData.setTextdata(texts[i]);
            if(i == 0 || i == 5 || i == 10) myData.setTitle(true);
            myData.setChecked(false);
            list.add(myData);


        }


        MyAdapter adapter = new MyAdapter(MissionActivity.this, R.layout.list,list);
        listView.setAdapter(adapter);

    }

}