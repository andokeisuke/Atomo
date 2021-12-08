package com.example.atomo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileDescriptor;
import java.io.IOException;

public class SettingWindowActivity extends AppCompatActivity {


    private MyValue myValue;
    private Button import_Button;
    private static final int READ_REQUEST_CODE = 42;
    private EditView editView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settingwindow);
        myValue = (MyValue) this.getApplication();

        Button reset_Button = findViewById(R.id.reset_button);
        Button register_Button = findViewById(R.id.register_button);
        import_Button = findViewById(R.id.import_button);
        editView = findViewById(R.id.editview);
        if(myValue.getMadori_picture()!=null) editView.setBackground(myValue.getMadori_picture());
        if(myValue.getMadori_picture()==null) myValue.setMadori_picture(BitmapFactory.decodeResource(getResources(), R.drawable.madori));
        ImageButton window_icon = findViewById(R.id.window_icon);
        ImageButton door_icon = findViewById(R.id.door_icon);
        ImageButton fan_icon = findViewById(R.id.fan_icon);

        reset_Button.setOnClickListener(view -> {

            editView.clearPoints();

        });

        register_Button.setOnClickListener(view -> {

            myValue.addMadori(editView.getPoints());

            editView.setDrawingCacheEnabled(true);
            editView.buildDrawingCache();
            Bitmap bm= editView.getDrawingCache();
            myValue.setMadori_window(bm);


            Intent intent = new Intent(getApplication(), SettingActivity.class);
            startActivity(intent);

        });

        window_icon.setOnClickListener(view -> {

            editView.getIcon_state(0);

        });

        door_icon.setOnClickListener(view -> {

            editView.getIcon_state(1);

        });

        fan_icon.setOnClickListener(view -> {

            editView.getIcon_state(2);

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        import_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);  //READ_REQUSET_CODE = 42*/
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            uri = null;
            if (resultData != null) {
                uri = resultData.getData();//画像データのuri
                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    editView.setBackground(bmp);
                    myValue.setMadori_picture(bmp);
                } catch (IOException e) {
                    //TODO:例外処理
                }
            }
        }
    }

    //公式Docからそのまま転用
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}




