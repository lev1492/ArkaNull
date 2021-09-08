package com.sms.nullpointers.arkanull.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.sms.nullpointers.arkanull.R;

public class Heart extends View {
    private Bitmap heart;
    private float x;
    private float y;


    public Heart(Context context, float x, float y){
        super(context);
        this.heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        this.x = x;
        this.y = y;

    }

    /**
     * Checks if the heart is close to the coordinates given
     * @param ax x object 1
     * @param ay y object 1
     * @param bx x object 2
     * @param by y object 2
     * @return
     */
    private boolean isClose(float ax, float ay, float bx, float by) {
        bx += 150;
        by += 150;
        if ((Math.sqrt(Math.pow((ax + 50) - bx, 2) + Math.pow(ay - by, 2))) < 80) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 100) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 150) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        }
        return false;
    }

    /**
     * If the ball is close to the heart, returns true
     * @param ballX x coordinate of the ball
     * @param ballY y coordinate of the ball
     * @return
     */
    public boolean isHit(float ballX, float ballY){
        if(isClose(ballX, ballY, this.x, this.y)){
            return true;
        }
        return false;
    }

    public Bitmap getHeart() {
        return heart;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
