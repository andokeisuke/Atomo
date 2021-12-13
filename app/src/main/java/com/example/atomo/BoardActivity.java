package com.example.atomo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class BoardActivity  extends AppCompatActivity {

    private Handler handler = new Handler();
    private String weather_url = "";
    private String pollen_url = "";
    private float[] new_OutState = {1,1,0,0};
    private MyValue myValue;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference in = database.getReference("in");
    DatabaseReference out = database.getReference("out");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myValue = (MyValue) this.getApplication();
        pollen_url = myValue.getAPI_URL()[0];
        weather_url = myValue.getAPI_URL()[1];
        setContentView(R.layout.activity_board);

        ImageButton letter_Button = findViewById(R.id.letter_button);
        TextView board_Text = findViewById(R.id.board);


        //letterへ遷移
        letter_Button.setOnClickListener(v -> {

            Intent intent = new Intent(getApplication(), LetterActivity.class);
            startActivity(intent);
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

        in.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = (String)dataSnapshot.child("CO2").getValue();
                //String value2 = (String)dataSnapshot.child("test2").getValue();
                myValue.setCO2(Float.parseFloat(value1));

                String tempture = (String) dataSnapshot.child("tempture").getValue();
                String humidity = (String) dataSnapshot.child("humidity").getValue();
                myValue.setIn_status(Float.parseFloat(tempture),Float.parseFloat(humidity));


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = "";
                try {

                    response = getAPI(weather_url);
                    JSONObject rootJSON = new JSONObject(response);

                    String Current = rootJSON.getString("current");
                    rootJSON = new JSONObject(Current);
                    String nowTempture = rootJSON.getString("temp");
                    new_OutState[1] = Float.parseFloat(nowTempture);
                    String nowHumidity = rootJSON.getString("humidity");
                    new_OutState[2] = Float.parseFloat(nowHumidity);


                    response = getAPI(pollen_url);
                    JSONArray JSON = new JSONArray(response);
                    rootJSON = JSON.getJSONObject(JSON.length()-1);
                    String pollen = rootJSON.getString("KFN_NUM");

                    new_OutState[3] = Integer.parseInt(pollen);

                    myValue.setOut_status(0,new_OutState[1],new_OutState[2],new_OutState[3]);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        out.child("tempture").setValue(String.valueOf(new_OutState[1]));
                        out.child("humidity").setValue(String.valueOf(new_OutState[2]));
                        out.child("pollen").setValue(String.valueOf(new_OutState[3]));
                        //board_Text.setText(String.valueOf(new_OutState[1])+String.valueOf(new_OutState[2])+String.valueOf(new_OutState[3]));


                        board_Text.setText(recomend(myValue.getIn_status(), myValue.getOut_status(), myValue.getUser_status()));

                    }
                });
            }
        });
        thread.start();



    }



    public String getAPI(String URL_text){
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String result = "";
        String str = "";
        try {
            URL url = new URL(URL_text);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.addRequestProperty("User-Agent", "Android");
            urlConnection.addRequestProperty("Accept-Language", Locale.getDefault().toString());
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200){
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                result = bufferedReader.readLine();
                while (result != null){
                    str += result;
                    result = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String recomend(float[] In_status,float[] Out_status,int[] User_status){
        String dst = "";
        String pollen_text = "";
        String tempture_text = "";


        String[] template_text = {

                "花粉が飛んでいるよ！\n"+
                        "10時前は花粉が少ないから積極的に換気しよう！\n"+
                        "レースカーテンをしめ、窓10cmぐらい開けよう",

                "花粉が飛んでいるよ！\n"+
                        "レースカーテンをしめ、窓10cmぐらい開けよう",


                "室内は快適だけど\n外との気温差が大きいみたい\n" +
                        "換気中はエアコンはつけっぱなしでも大丈夫\n" +
                        "エアコンから離れた窓を開けると、効率よく換気できるよ！\n",

                "この部屋ちょっと暑そうだね\nエアコンは換気後につけるといいよ！",

                "外との気温差が大きいみたい\n" +
                        "換気中はエアコンはつけっぱなしでも大丈夫\n" +
                        "エアコンから離れた窓を開けると、効率よく換気できるよ！\n",

                "外はちょうどいい気候だよ\nのびのびと換気できるよ！",



                "室内は快適だけど\n外との気温差が大きいみたい\n" +
                        "　5分より短めの換気を\n2回行うのがおすすめ！\n",

                "この部屋ちょっと寒そうだね\n" +
                        "　部屋を暖かくした後に換気するのがおすすめ！\n",

                "この部屋ちょっと寒そうだね\n" +
                        "　部屋を暖かくした後に換気するのがおすすめ！\n",




        };

        float tempture_sub = Math.abs(In_status[0]-Out_status[1]);
        float in_temp = In_status[0];
        float out_temp = Out_status[1];
        float pollen = Out_status[3];

        //時刻から季節を識別

        int[] Season = GetCalender();

        //閾値を決定
        int[] th = DecideTh(User_status);

        //花粉によるリコメンド
        if(Season[0] == 1 && pollen > th[2]) pollen_text = template_text[0];
        else if(Season[0] == 2&& pollen > th[2]) pollen_text = template_text[1];

        if(Season[1] == 0){//summer
            if(25<=in_temp && in_temp <= 28){//適温のとき

                if(tempture_sub > th[0]) tempture_text = template_text[2];
                else tempture_text = template_text[3];

            }else{

                if(tempture_sub > th[0]) tempture_text = template_text[4];
                else tempture_text = template_text[5];


            }


        }else if(Season[1] == 1){//winter

            if(18<=in_temp && in_temp <= 22){//適温のとき

                if(tempture_sub > th[1]) tempture_text = template_text[6];
                else tempture_text = template_text[7];

            }else{

                if(tempture_sub > th[1]) tempture_text = template_text[8];
                else tempture_text = template_text[5];

            }


        }else if(Season[1] == 2){//other
            if(out_temp>28){

                if(25<=in_temp && in_temp <= 28){//適温のとき

                    if(tempture_sub > th[0]) tempture_text = template_text[2];
                    else tempture_text = template_text[3];

                }else{

                    if(tempture_sub > th[0]) tempture_text = template_text[4];
                    else tempture_text = template_text[5];


                }

            }

            else if(out_temp<18){

                if(18<=in_temp && in_temp <= 22){//適温のとき

                    if(tempture_sub > th[1]) tempture_text = template_text[6];
                    else tempture_text = template_text[7];

                }else{

                    if(tempture_sub > th[1]) tempture_text = template_text[8];
                    else tempture_text = template_text[5];

                }

            }else{

                tempture_text = template_text[5];

            }




        }





        dst = pollen_text + "\n" + tempture_text;



        return dst;

    }

    public int[] GetCalender(){

        //1つ目の引数　花粉0：該当外１：10時前２：10時後
        //2つ目の引数　季節 0:夏1：冬2：それ以外

        int[] dst = new int[2];

        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        int month = time.month +1;
        int hour = time.hour;;

        if(2<= month && month<= 6) {
            if(hour <= 10) dst[0] = 1;
            else dst[0] = 2;
        }else {

            dst[0] = 0;
        }

        if(7<= month && month<= 9) {
            dst[1] = 0;
        }else if((1<= month && month<= 2) || month==12){

            dst[1] = 1;
        }else {
            dst[1] = 2;
        }

        return dst;
    }


    public int[] DecideTh(int[] User_Status){

        int[] th = new int[3];

        //気温差閾値
        if(User_Status[0] == 0) {
            th[0] = 4;
            th[1] = 0;

        }
        else if (User_Status[0] == 1) {
            th[0] = 4;
            th[1] = 4;

        }
        else if (User_Status[0] == 2) {
            th[0] = 4;
            th[1] = 8;

        }
        else if (User_Status[0] == 3) {
            th[0] = 2;
            th[1] = 8;

        }
        else if (User_Status[0] == 4) {
            th[0] = 0;
            th[1] = 8;

        }






        //花粉閾値
        if(User_Status[1] == 0) th[2] = 50;
        else if (User_Status[1] == 1) th[2] = 40;
        else if (User_Status[1] == 2) th[2] = 30;
        else if (User_Status[1] == 3) th[2] = 20;
        else if (User_Status[1] == 4) th[2] = 10;

        return th;




    }








}
