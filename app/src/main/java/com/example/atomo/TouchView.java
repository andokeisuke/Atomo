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

public class TouchView extends View {

    private float xZahyou = 0;
    private float yZahyou = 0;
    private List<float[]> points = new ArrayList<>();

    public TouchView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        xZahyou = event.getX();
        yZahyou = event.getY();

        float[] point = new float[]{xZahyou,yZahyou,0};
        points.add(point);

        this.invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawARGB(0,0,0,0);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLUE);

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
}
