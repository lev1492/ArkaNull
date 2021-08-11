package com.example.android.arkanull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class PowerUp extends View{
    private Bitmap pwrUp;
    private float x;
    private float y;
    private boolean spawned;

    public PowerUp( Context context){
        super(context);
        this.spawned = false;


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

    // Check if the power up touches the paddle
    protected boolean touchPaddle(float xPaddle, float yPaddle) {
        if (isClose(xPaddle, yPaddle, getX(), getY())) return true;
        return false;
    }

    protected boolean touchLimit(float limitX, float limitY) {
        if (isClose(limitX,limitY, getX(), getY())) return true;
        return false;
    }


    public void spawn(float x, float y){
        this.pwrUp = BitmapFactory.decodeResource(getResources(), R.drawable.pwrup);
        this.x = x;
        this.y = y;
        this.spawned = true;
    }

    public boolean getSpawned(){return spawned;}
    public Bitmap pwrUp(){return pwrUp;}

    public void fall(){
        y = y + 10;
    }

    public void setSpawned(boolean b){this.spawned = b;}

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

    public Bitmap getPwrUp() {
        return pwrUp;
    }
}
