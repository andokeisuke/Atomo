package com.example.atomo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<MyData> {

    private LayoutInflater mLayoutInflater;

    public MyAdapter(Context context, int resourceId, List<MyData> objects) {
        super(context, resourceId, objects);
        // getLayoutInflater()メソッドはActivityじゃないと使えない
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // getView()は各行を表示しようとした時に呼び出される
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // position のデータを取る
        MyData item = (MyData)getItem(position);
        // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) convertView = mLayoutInflater.inflate(R.layout.list, null);

        // MyDataのデータをViewの各ウィジェットにセットする
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(item.getTextData());

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setVisibility(View.VISIBLE);
        if(item.getTitle() == true)checkBox.setVisibility(View.GONE);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(item.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyData MyData = getItem(position);
                MyData.setChecked(b);
            }


        });


        return convertView;
    }
}
