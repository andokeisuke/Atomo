package com.example.atomo;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private Button btn_import;
    private ImageView img_picture;
    private EditText people_num;
    private EditText room_size;
    private Spinner address;
    private MyValue myValue;
    private Button complete;

    private String[] spinnerItems = {
            "茨城県　つくば市",
            "茨城県　水戸市",
            "茨城県　日立市",
            "東京都　小平市",
            "東京都　新宿区"
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        myValue = (MyValue) this.getApplication();


        complete = findViewById(R.id.complete);
        people_num = findViewById(R.id.people_num);
        room_size = findViewById(R.id.room_size);
        address = findViewById(R.id.address);
        btn_import = findViewById(R.id.btn_import);
        img_picture = findViewById(R.id.img_picture);
        img_picture.setImageResource(R.drawable.madori);

        people_num.setText(String.valueOf(myValue.getPeople_num()));
        room_size.setText(String.valueOf(myValue.getRoom_size()));

        // ArrayAdapter
        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner に adapter をセット
        address.setAdapter(adapter);

        // リスナーを登録
        address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                myValue.setAPI_URL(makeURL(position));

                //textView.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myValue.setPeople_num(Integer.parseInt(people_num.getText().toString()));
                myValue.setRoom_size(Integer.parseInt(room_size.getText().toString()));

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);


            }
        });;



    }

    private String[] makeURL(int index){

        String[] dst = new String[2];

        String[] List = {
                "08","50810200","36.051525","140.116882",//"茨城県　つくば市",
                "08","50810100","36.390862","140.426270",//"茨城県　水戸市",
                "08","50820100","36.601234","140.653900",//"茨城県　日立市",
                "13","51310200","35.729916","139.516357",//"東京都　小平市",
                "13","51320100","35.694408","139.706635"//"東京都　新宿区"
        };

        dst[0] = "https://kafun.env.go.jp/hanako/api/data_search?Start_YM=202103&End_YM=202103&TDFKN_CD="+List[index*4]+"&SKT_CD="+List[index*4 +1];
        dst[1] = "https://api.openweathermap.org/data/2.5/onecall?lat="+List[index*4 +2]+"&lon="+List[index*4 +3]+"&units=metric&lang=ja&appid=08eff6d88359db7c9777c7fb65d2955e";


        return dst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);  //READ_REQUSET_CODE = 42
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
                    img_picture.setImageBitmap(bmp);
                    myValue.setMadori_url(bmp);
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
