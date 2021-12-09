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

import java.util.ArrayList;
import java.util.List;


public class DiagnoseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private List<String[]> diagnose_list = new ArrayList<>();
    private List<Bitmap> diagnose_windows = new ArrayList<>();



    static class ViewHolder {
        TextView date;
        ImageView diagnose_window;
        TextView result;
    }

    DiagnoseAdapter(Context context, int itemLayoutId,
              List<String[]> Diagnose_list,List<Bitmap> Diagnose_windows){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;

        diagnose_list = Diagnose_list;
        diagnose_windows = Diagnose_windows;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.date = convertView.findViewById(R.id.date);
            holder.diagnose_window = convertView.findViewById(R.id.diagnose_window);
            holder.result = convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.date.setText(diagnose_list.get(position)[0]);
        holder.diagnose_window.setImageBitmap(diagnose_windows.get(position));
        holder.result.setText(diagnose_list.get(position)[2]+"m³/h");

        return convertView;
    }

    @Override
    public int getCount() {
        return diagnose_list.size();
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
