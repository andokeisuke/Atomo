package com.example.atomo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView1 extends View{
    public MyView1(Context context, AttributeSet attrs){
        super(context,attrs);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        float xc = getWidth() / 2;
        float yc = getHeight() / 2;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(xc, yc, 400, paint);
    }
}