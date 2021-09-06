package com.example.android.arkanull;

import java.io.Serializable;

public class BrickData implements Serializable {
    private float x;
    private float y;
    private int type;

    public BrickData(float x, float y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float getX() {
        return x;
    }
    public int getType(){
        return type;
    }

    public float getY() {
        return y;
    }
}
