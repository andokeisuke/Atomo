package com.example.atomo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                        board_Text.setText(String.valueOf(new_OutState[1])+String.valueOf(new_OutState[2])+String.valueOf(new_OutState[3]));


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






}
