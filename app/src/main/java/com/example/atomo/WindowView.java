package com.example.atomo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WindowView extends View {

    private List<float[]> points = new ArrayList<>();

    public WindowView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawARGB(0,0,0,0);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.GREEN);

        // 半径10の円を描画する
        for(int i = 0;i<points.size();i++){

            canvas.drawCircle(points.get(i)[0], points.get(i)[1], 30, p);
        }

    }

    public void clearPoints(){

        points = new ArrayList<>();
        invalidate();
    }

    public List<float[]> getPoints(){



        return points;
    }

    public void setPoint(List<float[]> Points){

        points = new ArrayList<>();

        for(int i = 0;i<Points.size();i++){

            points.add(Points.get(i));

        }

        invalidate();


    }

}
