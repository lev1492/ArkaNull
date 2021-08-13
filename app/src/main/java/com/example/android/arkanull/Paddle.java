package com.example.android.arkanull;

public class Paddle {
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private float x;
    private float y;


    // Is the paddle moving and in which direction
    private int paddleMoving = STOPPED;

    public Paddle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    // This method will be used to change/set if the paddle is going left, right or nowhere
    public void setMovementState(int state){
        paddleMoving = state;
    }
}