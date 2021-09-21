package com.example.atomo;


import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private List<String> date_labels = new ArrayList<String>();

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);

        date_labels.add("0:0:0");
        int temp_h = 0;
        int temp_m = 0;
        int temp_s = 0;


        for (int i = 0; i<5800; i++) {

            temp_s = temp_s + 15;

            if(temp_s == 60){

                temp_m++;
                temp_s = 0;
            }

            if(temp_m == 60){

                temp_h++;
                temp_m = 0;
            }

            if(temp_h == 24){
                temp_h = 0;
            }


            date_labels.add( temp_h + ":" + temp_m + ":" + temp_s);

        }
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {



        tvContent.setText(date_labels.get((int) e.getX()) +"\n" + Utils.formatNumber(e.getY(), 0, true)+"ppm");


        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight()- 20f);
    }
}

