package com.example.android.arkanull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.logging.Level;

public class LevelEditor extends View {
    private Bitmap background;
    private Bitmap extended;
    private Point point;
    private Display display;
    private Point size;
    private Paint paint;
    private int screenY;
    private RectF r;
    private Canvas canvas;
    private ArrayList<Brick> list = new ArrayList<Brick>();
    Context context;
    Button myButton;
    int x;
    int y;
    Brick brick;
    int type;

    public LevelEditor(Context context) {
        super(context);
        type = 0;
        this.context = context;
        setBackground(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Creates a background only once
        this.canvas = canvas;
        if (extended == null) {
            extended = Bitmap.createScaledBitmap(background, size.x, size.y, false);
        }

        canvas.drawBitmap(extended, 0, 0, paint);

        paint.setColor(Color.GREEN);
            for (int i = 0; i < list.size(); i++) {
                Brick b = list.get(i);
                r = new RectF(b.getX(), b.getY(), b.getX() + 100, b.getY() + 80);
                canvas.drawBitmap(b.getBrick(), null, r, paint);
            }
            paint.setColor(Color.WHITE);
            canvas.drawCircle(0, size.y - size.y / 10, 300, paint);
            canvas.drawCircle(size.x, size.y - size.y / 10, 300, paint);
            brick = new Brick(context, size.x / 18, size.y - size.y / 6 , type);
            r = new RectF(brick.getX(), brick.getY(), brick.getX() + 100, brick.getY() + 80);
            canvas.drawBitmap(brick.getBrick(), null, r, paint);
    }

    // set the background
    private void setBackground(@NonNull Context context) {
        background = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.background_score));
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }


    public void update(){

    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(list.size() < 30 && motionEvent.getY() <= size.y / 2 && motionEvent.getY() > size.y / 12){
            Brick b = new Brick(context, motionEvent.getX(), motionEvent.getY(), type);
            list.add(b);
        }

        if(motionEvent.getY() >= size.y - 350 && motionEvent.getX() <= 250){
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_UP:
                    if(type < 3){
                        type++;
                    }
                    else{
                        type = 0;
                    }
                    break;
            }
        }

        if(motionEvent.getY() >= size.y - 350 && motionEvent.getX() >= size.x - 250){
            if(!list.isEmpty()){
                list.remove(list.size() - 1);
            }
        }

        return true;
    }
}
