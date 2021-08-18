package com.example.android.arkanull;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {
    private ArrayList<Brick> bricks;
    private Random rand;

    public LevelGenerator(){
        this.bricks = new ArrayList<Brick>();
        this.rand = new Random();
    }

    public ArrayList<Brick> generateEasy(Context context, int level, Point size){
        bricks.clear();
        int brick_Type = 0;

        if(level == 2 || level == 3){
            brick_Type = 1;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (brick_Type + 1) < 4){
                brick_Type++;
            }

            if( i == 6 && level > 2 && (brick_Type + 1 ) < 4){
                brick_Type++;
            }


            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * 200 , size.y /  2 - i * 100 - 150, brick_Type));
            }

        }
        return bricks;
    }

    public ArrayList<Brick> generateNormal(Context context, int level, Point size){
        bricks.clear();
        int brick_Type = 0;

        if(level == 2){
            brick_Type = 1;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (brick_Type + 1) < 4){
                brick_Type++;
            }

            if( i == 6 && level > 2 && (brick_Type + 1 ) < 4){
                brick_Type++;
            }
            if(i == 3 && level > 0 && (brick_Type + 1) < 4){
                brick_Type++;
            }

            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * 200 , size.y /  2 - i * 100 - 150, brick_Type));
            }

            if(i == 3 && level > 0 && (brick_Type - 1) >= 0){
                brick_Type--;
            }

        }
        return bricks;
    }

    public ArrayList<Brick> generateHard(Context context, int level, Point size){
        bricks.clear();
        int brick_Type = 1;

        if(level == 3){
            brick_Type = 2 ;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (brick_Type + 1) < 4){
                brick_Type++;
            }

            if( i == 6 && level > 2 && (brick_Type + 1 ) < 4){
                brick_Type++;
            }


            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * 200 , size.y /  2 - i * 100 - 150, brick_Type));
            }

        }
        return bricks;
    }

    public ArrayList<Brick> generateBoss(Context context, Point size, int phase) {
        bricks.clear();
        for (int i = 3; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * 200 , size.y /  2 - i * 100 + 200, phase));
            }
        }
        return bricks;
    }

    public ArrayList<Brick> generateEndless(Context context, int level, Point size, int difficulty) {
        int brick_Type;
        for (int i = 3; i < 7; i++) {
            brick_Type = rand.nextInt(2) + difficulty;
            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, j * 150, i * 100, brick_Type));
            }
        }
        return bricks;
    }
}
