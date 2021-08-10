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

    public Brick(Context context, float x, float y) {
        super(context);
        this.x = x;
        this.y = y;
        brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
        skin();
    }

    // priradi random obrazok tehlicke

    private void skin() {
        int a = (int) (Math.random() * 3);
        switch (a) {
            case 0:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
                this.hp = 0;
                this.type = 0;
                break;
            case 1:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_red);
                this.hp = 1;
                this.type = 1;
                break;
            case 2:
                brick = brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_yellow);
                this.hp = 0;
                this.type = 2;
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
            this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
            this.hp--;

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
