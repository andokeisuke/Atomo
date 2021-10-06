package com.example.atomo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

import java.util.List;


public class MadoriAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private List<List<float[]>> MadoriList;
    private int SelectMadori;
    private Bitmap[] waku_png = new Bitmap[2];


    static class ViewHolder {
        ImageView waku;
        MadoriView canvas;
    }

    MadoriAdapter(Context context, int itemLayoutId,
                  List<List<float[]>> madoris_list,int SelectNum){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        MadoriList = madoris_list;
        SelectMadori = SelectNum;

        waku_png[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.madori_waku_off);
        waku_png[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.madori_waku_on);



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.waku = convertView.findViewById(R.id.waku);
            holder.canvas = convertView.findViewById(R.id.canvas);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position == SelectMadori){
            holder.waku.setImageBitmap(waku_png[1]);
        }else{
            holder.waku.setImageBitmap(waku_png[0]);
        }

        float[] point = {10,10};
        //holder.canvas.setPoint(point);

        return convertView;
    }

    @Override
    public int getCount() {
        return MadoriList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
