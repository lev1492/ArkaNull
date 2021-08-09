package com.example.android.arkanull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Brick extends View {

    private Bitmap brick;
    private float x;
    private float y;
    private int hp;

    public Brick(Context context, float x, float y) {
        super(context);
        this.x = x;
        this.y = y;
        brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
        skin();
    }

    // priradi random obrazok tehlicke

    private void skin() {
        int a = (int) (Math.random() * 2);
        switch (a) {
            case 0:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
                this.hp = 0;
                break;
            case 1:
                brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_red);
                this.hp = 1;
                break;
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

    public void hit(){
            this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_aqua);
            this.hp--;

    }

    public Bitmap getBrick() {
        return brick;
    }


}
