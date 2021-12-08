package com.example.atomo;



import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;
import static java.security.AccessController.getContext;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class LetterActivity extends AppCompatActivity {

    private MyValue myValue;
    private int[] user_status;
    private SeekBar[] User_Bar;
    private Application letter;
    private DialogFragment dialogFragment;
    private FragmentManager flagmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        letter = getApplication();

        setContentView(R.layout.activity_letter);
        myValue = (MyValue) this.getApplication();
        user_status = myValue.getUser_status();

        ImageButton send_Button = findViewById(R.id.send_button);


        SeekBar hot_Bar = findViewById(R.id.hot);
        SeekBar cold_Bar = findViewById(R.id.hot);
        SeekBar pollen_Bar = findViewById(R.id.pollen);
        SeekBar humidity_Bar = findViewById(R.id.humidity);
        SeekBar dry_Bar = findViewById(R.id.humidity);
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

            flagmentManager = getSupportFragmentManager();

            dialogFragment = new AlertDialogFragment(getApplication());
            dialogFragment.show(flagmentManager, "test alert dialog");

            /*new AlertDialog.Builder(this)
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
                    .show();*/






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


    public static class AlertDialogFragment extends DialogFragment {

        AlertDialog dialog ;
        AlertDialog.Builder alert;
        View alertView;
        Application letter;


        public AlertDialogFragment(Application Letter){

            letter = Letter;

        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());

            // カスタムレイアウトの生成
            if(getActivity() != null) {
                alertView = getActivity().getLayoutInflater().inflate(R.layout.alert_lyout, null);
            }

            // alert_layout.xmlにあるボタンIDを使う
            ImageView bag1 = alertView.findViewById(R.id.ok_button);
            bag1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Log.d("debug","bag1 clicked");

                    // Dialogを消す
                    getDialog().dismiss();
                    Intent intent = new Intent(letter, BoardActivity.class);
                    startActivity(intent);


                }
            });


            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }

    }
}
