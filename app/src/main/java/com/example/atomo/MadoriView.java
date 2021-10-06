package com.example.atomo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MadoriView extends View{

    private List<float[]> points = new ArrayList<>();
    public MadoriView(Context context, AttributeSet attrs){
        super(context,attrs);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawARGB(0,0,0,0);


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);

        for(int i = 0;i<points.size();i++){

            canvas.drawCircle(points.get(i)[0]/3, points.get(i)[1]/3, 40, paint);


        }

    }

    public void setPoint(List<float[]> Points){

        for(int i = 0;i<Points.size();i++){

            points.add(Points.get(i));

        }


    }
}