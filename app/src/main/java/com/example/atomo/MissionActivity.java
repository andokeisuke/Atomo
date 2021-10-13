package com.example.atomo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class MissionActivity extends AppCompatActivity {

    private MyValue myValue;
    private MyAdapter myAdapter;
    private int tab_state = 0;


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

    private static final String[] daily_text = {

            "充実度70以上を維持",
            "CO2 800ppm以下を維持",
            "温度28度以下を維持",
            "湿度50％以下を維持",
            "湿度50％以下を維持"

    };

    private static final String[] weekly_text = {

            "充実度70以上を維持",
            "CO2 800ppm以下を維持",
            "温度28度以下を維持",
            "湿度50％以下を維持",
            "5日間ミッション達成"

    };

    private static final String[] monthly_text = {

            "充実度70以上を維持",
            "CO2 800ppm以下を維持",
            "温度28度以下を維持",
            "湿度50％以下を維持",
            "20日ミッション達成"

    };

    private static final int[] box = {
            R.drawable.present_off,
            R.drawable.present_on

    };

    private static final int[] var = {
            R.drawable.goal_var0,
            R.drawable.goal_var25,
            R.drawable.goal_var50,
            R.drawable.goal_var75,
            R.drawable.goal_var100
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mission);
        myValue = (MyValue) this.getApplication();

        ImageButton daily_Button = findViewById(R.id.daily);
        ImageButton weekly_Button = findViewById(R.id.weekly);
        ImageButton monthly_Button = findViewById(R.id.monthly);

        ListView listView = findViewById(R.id.listView);

        myAdapter = new MyAdapter(MissionActivity.this, R.layout.list,daily_text, myValue.getdaily(),box,var );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                int[] goal = {myValue.getdaily()[position],myValue.getweekly()[position],myValue.getmonthly()[position]};


                if(goal[tab_state]==4) {
                    ListView listView = (ListView) parent;
                    ImageView present = (ImageView) listView.findViewWithTag(position);
                    present.setImageResource(R.drawable.present_off);

                    DialogFragment dialogFragment = new MyDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "my_dialog");



                }




            }
        });



        //daily
        daily_Button.setOnClickListener(v -> {

            myAdapter = new MyAdapter(MissionActivity.this, R.layout.list,daily_text, myValue.getdaily(),box,var );
            listView.setAdapter(myAdapter);
            daily_Button.setImageResource(R.drawable.daily_on);
            weekly_Button.setImageResource(R.drawable.weekly_off);
            monthly_Button.setImageResource(R.drawable.monthly_off);
            tab_state = 0;

        });

        weekly_Button.setOnClickListener(v -> {

            myAdapter = new MyAdapter(MissionActivity.this, R.layout.list,weekly_text, myValue.getweekly(),box,var );
            listView.setAdapter(myAdapter);
            daily_Button.setImageResource(R.drawable.daily_off);
            weekly_Button.setImageResource(R.drawable.weekly_on);
            monthly_Button.setImageResource(R.drawable.monthly_off);
            tab_state = 1;

        });

        monthly_Button.setOnClickListener(v -> {

            myAdapter = new MyAdapter(MissionActivity.this, R.layout.list,monthly_text, myValue.getmonthly(),box,var );
            listView.setAdapter(myAdapter);
            daily_Button.setImageResource(R.drawable.daily_off);
            weekly_Button.setImageResource(R.drawable.weekly_off);
            monthly_Button.setImageResource(R.drawable.monthly_on);
            tab_state = 2;

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