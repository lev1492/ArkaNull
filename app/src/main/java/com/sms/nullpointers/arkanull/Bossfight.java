package com.sms.nullpointers.arkanull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.sms.nullpointers.arkanull.R;

public class Bossfight extends View {
    private Bitmap boss;
    private float x;
    private float y;
    private int hp;
    private boolean moving_Right;
    public Bossfight(Context context, float x, float y){
        super(context);
        this.boss = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
        this.hp = 3;
        this.x = x;
        this.y = y;
        moving_Right = true;
    }

    private boolean isClose(float ax, float ay, float bx, float by) {
        bx += 12;
        by += 11;
        if ((Math.sqrt(Math.pow((ax + 50) - bx, 2) + Math.pow(ay - by, 2))) < 80) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 100) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 150) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        }
        return false;
    }

    public void move(Point size){
        if (size.x == 1080) {
            if(moving_Right){
                setX(this.x + 8);
                if(this.x >= size.x / 2){
                    moving_Right = false;
                }
            }
            else{
                setX(this.x - 8);
                if(this.x <= size.x / 15){
                    moving_Right = true;
                }
            }
        }

        else if(size.x == 1800){
            if(moving_Right){
                setX(this.x + 8);
                if(this.x >= size.x / 2){
                    moving_Right = false;
                }
            }
            else{
                setX(this.x - 8);
                if(this.x <= size.x / 4){
                    moving_Right = true;
                }
            }
        }

    }


    public Bitmap getBoss() {
        return boss;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
