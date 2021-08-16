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

    //Sets the hit points of the brick
    private int hp;

    //Sets the type of the brick
    private int type;

    //Check if the brick is currently moving to the right
    boolean moving_Right = true;

    public Brick(Context context, float x, float y, int d) {
        super(context);
        this.x = x;
        this.y = y;
        skin(d);
    }


    /**
     * Assign a type and a skin to the brick specified by the parameter passed to this method
     * @param d
     */
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
                this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_yellow);
                this.hp = 0;
                this.type = 3;
                break;
            case 3:
                this.brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick_red);
                this.hp = 2;
                this.type = 2;
                break;
        }
    }

    /**
     * If a brick gets hit by the ball, this method lowers is hp and changes his skin accordingly
     */
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

    /**
     * This method allows the brick to move to the left or right according to the screen size of the display, when it reaches a limit, it changes direction
     * @param size the size of the display
     */
    public void move(Point size){
        if(moving_Right){
            setX(this.x + 5);
            if(this.x >= size.x - 100){
                moving_Right = false;
            }
        }
        else{
            setX(this.x - 5);
            if(this.x <= 0){
                moving_Right = true;
            }
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



    public Bitmap getBrick() {
        return brick;
    }


}
