package com.example.atomo;

import android.content.Context;
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



public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private String[] missionlist;
    private int[] missionstatuslist;
    private Bitmap[] box;
    private Bitmap[] var;


    static class ViewHolder {
        TextView text;
        ImageView present;
        ImageView goal_var;
    }

    MyAdapter(Context context, int itemLayoutId,
                String[] mission_text,int[] mission_status, int[] box_id,int[] var_id){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;

        missionlist = mission_text;
        missionstatuslist = mission_status;

        box = new Bitmap[2];
        var = new Bitmap[5];

        for( int i = 0; i< 2; i++){
            box[i] = BitmapFactory.decodeResource(context.getResources(), box_id[i]);
        }
        for( int i = 0; i< 5; i++){
            var[i] = BitmapFactory.decodeResource(context.getResources(), var_id[i]);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.present = convertView.findViewById(R.id.present);
            holder.text = convertView.findViewById(R.id.mission_text);
            holder.goal_var = convertView.findViewById(R.id.goal_var);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        int status = missionstatuslist[position];

        if(status == 0){
            holder.goal_var.setImageBitmap(var[status]);
            holder.present.setImageBitmap(box[1]);
        }else if(status == 1){
            holder.goal_var.setImageBitmap(var[status]);
            holder.present.setImageBitmap(box[1]);
        }else if(status == 2){
            holder.goal_var.setImageBitmap(var[status]);
            holder.present.setImageBitmap(box[1]);
        }else if(status == 3){
            holder.goal_var.setImageBitmap(var[status]);
            holder.present.setImageBitmap(box[1]);
        }else if(status == 4){
            holder.goal_var.setImageBitmap(var[status]);
            holder.present.setImageBitmap(box[1]);
            holder.present.setTag(position);
        }

        holder.text.setText(missionlist[position]);





        return convertView;
    }

    @Override
    public int getCount() {
        return missionlist.length;
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
