package com.example.atomo;



import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;
import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LetterActivity extends AppCompatActivity {

    private MyValue myValue;
    private int[] user_status;
    private SeekBar[] User_Bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_letter);
        myValue = (MyValue) this.getApplication();
        user_status = myValue.getUser_status();

        ImageButton send_Button = findViewById(R.id.send_button);


        SeekBar hot_Bar = findViewById(R.id.hot);
        SeekBar cold_Bar = findViewById(R.id.cold);
        SeekBar pollen_Bar = findViewById(R.id.pollen);
        SeekBar humidity_Bar = findViewById(R.id.humidity);
        SeekBar dry_Bar = findViewById(R.id.dry);
        User_Bar = new SeekBar[]{hot_Bar, cold_Bar, pollen_Bar, humidity_Bar, dry_Bar};

        for(int i = 0;i<User_Bar.length;i++){
            User_Bar[i].setMax(4);
            User_Bar[i].setProgress(myValue.getUser_status()[i]);
        }



        //送信ボタン
        send_Button.setOnClickListener(v -> {


            for(int i = 0;i<User_Bar.length;i++){
                user_status[i] = User_Bar[i].getProgress();
            }

            myValue.setUser_status(user_status);

            new AlertDialog.Builder(this)
                    .setTitle("送信")
                    .setMessage( "送信しました")
                    .setPositiveButton("OK" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplication(), BoardActivity.class);
                            startActivity(intent);
                        }
                    })
                    .create()
                    .show();






        });


    }
}
