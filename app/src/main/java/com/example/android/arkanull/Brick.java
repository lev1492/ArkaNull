package com.example.android.arkanull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

public class Brick extends View {

    private Bitmap brick;
    private float x;
    private float y;
    private int hp;
    private int type;
    boolean moving_Right = true;

    public Brick(Context context, float x, float y, int d) {
        super(context);
        this.x = x;
        this.y = y;
        skin(d);
    }

    // priradi random obrazok tehlicke

    private void skin(int d) {
        switch (d) {
            case 0:
                this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
                this.hp = 0;
                this.type = 0;
                break;
            case 1:
                this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_blue);
                this.hp = 1;
                this.type = 1;
                break;
            case 2:
                this.brick = brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_red);
                this.hp = 2;
                this.type = 2;
            case 3:
               this.brick = brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_yellow);
                this.hp = 0;
                this.type = 3;
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getHp(){return hp;}

    public int getType(){return type;}

    public void hit(){
        this.hp--;
        switch(this.hp){
            case 0:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
                break;
            case 1:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_blue);
                break;
        }

    }

    public void move(Point size){
        if(moving_Right){
            setX(this.x + 5);
            if(this.x == size.x - 100){
                moving_Right = false;
            }
        }
        else{
            setX(this.x - 5);
            if(this.x == 0){
                moving_Right = true;
            }
        }

    }

    public Bitmap getBrick() {
        return brick;
    }


}
