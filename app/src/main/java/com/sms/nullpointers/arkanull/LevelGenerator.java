package com.sms.nullpointers.arkanull;

import android.content.Context;
import android.graphics.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {
    private ArrayList<Brick> bricks;
    private Random rand;
    private final String FILE_NAME = "customLevel.tmp";

    public LevelGenerator(){
        this.bricks = new ArrayList<Brick>();
        this.rand = new Random();
    }
    //TODO da eliminare se non serve
    private ArrayList<Brick> createBreak (Context context, Point size, int brick_Type, int i) {
        int x = size.x / 6;
        int y = size.y / 17;

        for (int j = 1; j < 6; j++) {
            bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
        }
        return bricks;
    }

    public ArrayList<Brick> generateEditorLevel(Context context, Point size){
     bricks.clear();
     ArrayList<BrickData> brickDatas = new ArrayList<>();
        try{
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            brickDatas = (ArrayList<BrickData>) is.readObject();
            is.close();
            fis.close();
        }catch(FileNotFoundException e){
            int x = size.x / 6;
            int y = size.y / 17;
            int brick_Type = 0;
            for (int i = 3; i < 7; i++) {
                for (int j = 1; j < 6; j++) {
                    bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
                }
            }
            return bricks;

        }catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i < brickDatas.size(); i++){
            BrickData bd = brickDatas.get(i);
            bricks.add(new Brick(context, bd.getX(), bd.getY(), bd.getType()));
        }
        return bricks;
    }


    public ArrayList<Brick> generateEasy(Context context, int level, Point size){
        bricks.clear();
        int x = size.x / 6;
        int y = size.y / 17;
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
                bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
            }

        }
        return bricks;
    }

    public ArrayList<Brick> generateNormal(Context context, int level, Point size){
        bricks.clear();
        int x = size.x / 6;
        int y = size.y / 17;
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
                bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
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
        int x = size.x / 6;
        int y = size.y / 17;

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
                bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
            }

        }
        return bricks;
    }

    public ArrayList<Brick> generateBoss(Context context, Point size, int phase) {
        int x = size.x / 6;
        int y = size.y / 50;

        bricks.clear();
        for (int i = 3; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y * 2, phase));
            }
        }
        return bricks;
    }

    public ArrayList<Brick> generateEndless(Context context, int level, Point size, int difficulty) {
        int brick_Type;
        int x = size.x / 6;
        int y = size.y / 17;

        for (int i = 3; i < 7; i++) {
            brick_Type = rand.nextInt(2) + difficulty;
            for (int j = 1; j < 6; j++) {
                bricks.add(new Brick(context, size.x - j * x , size.y /  2 - i * y , brick_Type));
            }
        }
        return bricks;
    }
}
