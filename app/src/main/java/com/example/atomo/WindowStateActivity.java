package com.example.atomo;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.text.format.Time;
import java.util.ArrayList;
import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;


public class WindowStateActivity extends AppCompatActivity {

    private MyValue myValue;
    private int SelectNum = 0;
    private LinearLayout madoriList;
    private List<View> SeekBars = new ArrayList<>();
    private int[] buttonState;
    private DialogFragment dialogFragment;
    private FragmentManager flagmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windowstate);

        myValue = (MyValue) this.getApplication();

        ImageButton start_Button = findViewById(R.id.start_button);
        ImageView background = findTheViewById(R.id.background);
        background.setImageBitmap(myValue.getMadori_picture());


        start_Button.setOnClickListener(v -> {

            ConstraintLayout window = findTheViewById(R.id.windowList);
            window.setDrawingCacheEnabled(true);
            window.buildDrawingCache();
            Bitmap bm= window.getDrawingCache();
            myValue.setDiagnose_window(bm);



            String madorinum = String.valueOf(SelectNum+1);
            Intent intent = new Intent(getApplication(), ScoreActivity.class);
            intent.putExtra("madorinum", madorinum);
            startActivity(intent);


        });
        ConstraintLayout window = findTheViewById(R.id.windowList);

        buttonState = new int[myValue.getMadori_list().get(0).size()];

        for(int i = 0;i<myValue.getMadori_list().get(0).size();i++){

            View custombutton = getLayoutInflater().inflate(R.layout.custom_button, null);
            window.addView(custombutton);
            custombutton.setTranslationX(myValue.getMadori_list().get(0).get(i)[1]);
            custombutton.setTranslationY(myValue.getMadori_list().get(0).get(i)[2]);
            TextView percent = custombutton.findViewById(R.id.percent);
            ImageButton icon = custombutton.findViewById(R.id.icon);


            if(myValue.getMadori_list().get(0).get(i)[0]==0){

                icon.setImageResource(R.drawable.window_icon);
                icon.setOnClickListener(v -> {

                    flagmentManager = getSupportFragmentManager();

                    dialogFragment = new WindowStateActivity.AlertDialogFragment(getApplication(),percent);

                    dialogFragment.show(flagmentManager, "test alert dialog");
                });





            }
            else if(myValue.getMadori_list().get(0).get(i)[0]==1){

                icon.setImageResource(R.drawable.door_icon);

                icon.setOnClickListener(v -> {

                    View vg = (View) v.getParent();
                    int index = window.indexOfChild(vg)-1;
                    if(buttonState[index] == 0){
                        icon.setImageResource(R.drawable.door_open);
                        buttonState[index] = 1;

                    }else if(buttonState[index] == 1){
                        icon.setImageResource(R.drawable.door_icon);
                        buttonState[index] = 0;

                    }

                });

            }else if(myValue.getMadori_list().get(0).get(i)[0]==2){

                icon.setImageResource(R.drawable.fan_icon);
                icon.setOnClickListener(v -> {

                    View vg = (View) icon.getParent();
                    int index = window.indexOfChild(vg)-1;
                    if(buttonState[index] == 0){
                        icon.setImageResource(R.drawable.fan_on);
                        buttonState[index] = 1;

                    }
                    else if(buttonState[index] == 1){
                        icon.setImageResource(R.drawable.fan_icon);
                        buttonState[index] = 0;

                    }

                });

            }







        }



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

    @Override
    protected void onResume() {

        super.onResume();





    }

    @SuppressWarnings("unchecked")
    private <T> T findTheViewById(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    public static class AlertDialogFragment extends DialogFragment {

        AlertDialog dialog ;
        AlertDialog.Builder alert;
        View alertView;
        Application letter;
        TextView percent;


        public AlertDialogFragment(Application Letter,TextView Percent){

            letter = Letter;
            percent = Percent;

        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());

            // カスタムレイアウトの生成
            if(getActivity() != null) {
                alertView = getActivity().getLayoutInflater().inflate(R.layout.seekbar_input, null);
            }

            SeekBar seekBar = alertView.findViewById(R.id.seek_bar);

            // alert_layout.xmlにあるボタンIDを使う
            ImageView bag1 = alertView.findViewById(R.id.ok_button);
            bag1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Log.d("debug","bag1 clicked");

                    // Dialogを消す
                    getDialog().dismiss();

                    float seekbar_value = seekBar.getProgress();

                    percent.setText(String.valueOf(seekbar_value)+"%");


                }
            });


            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }

        public void setPercent(TextView Percent){
            percent = Percent;

        }

    }

}
