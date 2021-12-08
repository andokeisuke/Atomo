package com.example.atomo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EditView extends View {

    private float xZahyou = 0;
    private float yZahyou = 0;
    private List<float[]> icon_points = new ArrayList<>();
    private int icon_state = 0;
    private Bitmap background = null;
    private Bitmap window_icon = null;
    private Bitmap door_icon = null;
    private Bitmap fan_icon = null;
    private int icon_size = 100;
    private Bitmap dst = null;

    public EditView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setFocusable(true);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.madori);
        window_icon = BitmapFactory.decodeResource(getResources(), R.drawable.window_icon);
        door_icon = BitmapFactory.decodeResource(getResources(), R.drawable.door_icon);
        fan_icon = BitmapFactory.decodeResource(getResources(), R.drawable.fan_icon);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
            xZahyou = event.getX();
            yZahyou = event.getY();

            float[] icon_point = new float[]{icon_state,xZahyou,yZahyou};
            icon_points.add(icon_point);

            this.invalidate();



        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawARGB(0,0,0,0);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLUE);

        // 半径10の円を描画する
        int width = background.getWidth();
        int height = background.getHeight();

        canvas.drawBitmap(background, new Rect(0, 0, width, height),
        new Rect(0, 0, canvas.getHeight()*width/height, canvas.getHeight()), null);

        for(int i = 0;i<icon_points.size();i++){

            if(icon_points.get(i)[0]==0){
                width = window_icon.getWidth();
                height = window_icon.getHeight();
                canvas.drawBitmap(window_icon, new Rect(0, 0, width, height),
                        new Rect((int)icon_points.get(i)[1]-icon_size/2, (int)icon_points.get(i)[2]-icon_size/2,
                                (int)icon_points.get(i)[1]+icon_size, (int)icon_points.get(i)[2]+icon_size), null);

            }else if(icon_points.get(i)[0]==1){
                width = door_icon.getWidth();
                height = door_icon.getHeight();
                canvas.drawBitmap(door_icon, new Rect(0, 0, width, height),
                        new Rect((int)icon_points.get(i)[1]-icon_size/2, (int)icon_points.get(i)[2]-icon_size/2,
                                (int)icon_points.get(i)[1]+icon_size, (int)icon_points.get(i)[2]+icon_size), null);
            }else if(icon_points.get(i)[0]==2){
                width = fan_icon.getWidth();
                height = fan_icon.getHeight();
                canvas.drawBitmap(fan_icon, new Rect(0, 0, width, height),
                        new Rect((int)icon_points.get(i)[1]-icon_size/2, (int)icon_points.get(i)[2]-icon_size/2,
                                (int)icon_points.get(i)[1]+icon_size, (int)icon_points.get(i)[2]+icon_size), null);
            }


        }


    }

    public void clearPoints(){

        icon_points = new ArrayList<>();
        invalidate();
    }

    public List<float[]> getPoints(){

        return icon_points;
    }

    public void getIcon_state(int state){

        icon_state = state;
    }

    public void setBackground(Bitmap Background){

        background = Background;
    }


}
