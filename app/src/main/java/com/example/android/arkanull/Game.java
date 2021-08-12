package com.example.android.arkanull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class Game extends View implements SensorEventListener, View.OnTouchListener {

    private Bitmap pozadie;
    private Bitmap redBall;
    private Bitmap roztiahnuty;
    private Bitmap paddle_p;
    private Bitmap pwrUp;
    private Point point;
    private Display display;
    private Point size;
    private Paint paint;
    private int screenX;

    private Ball lopticka;
    private ArrayList<Brick> zoznam;
    private ArrayList<PowerUp> pList;
    private ArrayList<Heart> bossLife;
    private Paddle paddle;
    private PowerUp pUp;
    private Bossfight boss;
    private boolean vulnerable;

    private RectF r;

    private SensorManager sManager;
    private Sensor accelerometer;

    private int lifes;
    private int score;
    private int level;
    private int mode;


    private boolean start;
    private boolean gameOver;
    private Context context;
    boolean paused = true;
    private Random rand = new Random();

    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;
    private int difficulty;


    public Game(Context context, int lifes, int score, int g_Mode, int diff) {
        super(context);
        difficulty = diff;
        paint = new Paint();
        mode = g_Mode;

        // set context, lives, scores and levels
        this.context = context;
        this.lifes = lifes;
        this.score = score;
        level = 0;

        // start a gameOver to find out if the game is stopped and if the player has lost it
        start = false;
        gameOver = false;

        // creates accelerometer and SensorManager
        sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        nacitajPozadie(context);

        // create a bitmap for the ball and paddle
        redBall = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
        paddle_p = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        //pwrUp = BitmapFactory.decodeResource(getResources(), R.drawable.pwrup);

        // create a new ball, paddle, and list of bricks
        lopticka = new Ball(size.x / 2, size.y - 480 );
        paddle = new Paddle(size.x / 2, size.y - 400);
        pUp = new PowerUp(context);
        boss = new Bossfight(context, size.x / 2 - 300, size.y - 1900);




        zoznam = new ArrayList<Brick>();


        bossLife = new ArrayList<Heart>();
        phase = 0;
        for(int i = 0; i < 3 ; i++) {
            switch (i) {
                case 0:
                    bossLife.add(new Heart(context, size.x / 2 - 500, size.y - 1800));
                    break;
                case 1:
                    bossLife.add(new Heart(context, size.x / 2 - 130, size.y - 1300));
                    break;
                case 2:
                    bossLife.add(new Heart(context, size.x / 2 + 250, size.y - 1800));
                    break;
            }
        }

        vygenerujBricks(context, difficulty);
        this.setOnTouchListener(this);


    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        point = new Point();
        display.getSize(point);
        screenX = point.x;

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if(motionEvent.getX() > screenX / 2){
                    paddle.setMovementState(paddle.RIGHT);
                    paddle.setX(paddle.getX() + 100);

                }
                else{
                    paddle.setMovementState(paddle.LEFT);
                    paddle.setX(paddle.getX() - 100);

                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                paddle.setMovementState(paddle.STOPPED);
                break;
        }
        return true;
    }

    // naplni zoznam tehlickami
    private void vygenerujBricks(Context context, int difficulty) {

        if(level == 2){
            bossfight(context);
        }
        else if(level > 5){
            generateEndless(context);
        }
        else{
            switch(difficulty){
                case EASY:
                    generateEasy(context);
                    break;
                case NORMAL:
                    generateNormal(context);
                    break;
                case HARD:
                    generateHard(context);
                    break;
            }
        }
    }


    private int phase;
    private void changePhase(){
        if(phase < 4){
            for (int i = 3; i < 5; i++) {
                for (int j = 1; j < 6; j++) {
                    zoznam.add(new Brick(context, j * 150, i * 250, phase + 1));
                }
            }
            phase++;
        }
    }

    private void bossfight(Context context){
        if(phase == 0){
            changePhase();
        }
        bossWon();
        vulnerable = isVulnerable();

        if(vulnerable){
            for(int i = 0; i < bossLife.size(); i++){
                Heart d = bossLife.get(i);
                if(d.isHit(lopticka.getX(),lopticka.getY())){
                    lopticka.zmenSmer();
                    bossLife.remove(i);
                    vulnerable = false;
                    if(phase < 3){
                        changePhase();
                    }
                }
            }
        }
        skontrolujOkraje(false);
        lopticka.NarazPaddle(paddle.getX(), paddle.getY());
        for (int i = 0; i < zoznam.size(); i++) {
            Brick b = zoznam.get(i);
            if(b.getType() == 3){
                b.move(size);
            }
            if (lopticka.NarazBrick(b.getX(), b.getY(), false) ) {
                if(b.getHp() == 0){
                    zoznam.remove(i);
                    score = score + 80;
                }
                else{
                    b.hit();
                    score = score + 20;
                }
            }
        }
        lopticka.pohni();
    }

    private boolean isVulnerable(){
        if(zoznam.isEmpty()){
            return true;
        }
        return false;
    }

    private void bossWon(){
        if(bossLife.isEmpty()){
            score = score + 5000;
            ++level;
            resetLevel();
            lopticka.zvysRychlost(level);
            start = false;
        }
    }

    private void generateEasy(Context context){
        int b = 0;

        if(level == 2 || level == 3){
            b = 1;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (b + 1) < 4){
                b++;
            }

            if( i == 6 && level > 2 && (b + 1 ) < 4){
                b++;
            }


            for (int j = 1; j < 6; j++) {
                zoznam.add(new Brick(context, j * 150, i * 100, b));
            }

        }
    }

    private void generateNormal(Context context){
        int b = 0;

        if(level == 2){
            b = 1;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (b + 1) < 4){
                b++;
            }

            if( i == 6 && level > 2 && (b + 1 ) < 4){
                b++;
            }
            if(i == 3 && level > 0 && (b + 1) < 4){
                b++;
            }

            for (int j = 1; j < 6; j++) {
                zoznam.add(new Brick(context, j * 150, i * 100, b));
            }

            if(i == 3 && level > 0 && (b - 1) >= 0){
                b--;
            }

        }
    }

    private void generateHard(Context context){
        int b = 1;

        if(level == 3){
            b = 2 ;
        }

        for (int i = 3; i < 7; i++) {

            if( i == 5 && level > 3 && (b + 1) < 4){
                b++;
            }

            if( i == 6 && level > 2 && (b + 1 ) < 4){
                b++;
            }


            for (int j = 1; j < 6; j++) {
                zoznam.add(new Brick(context, j * 150, i * 100, b));
            }

        }
    }

    private void generateEndless(Context context){
        int b;
        for (int i = 3; i < 7; i++) {
            b = rand.nextInt(2) + difficulty;
            for (int j = 1; j < 6; j++) {
                zoznam.add(new Brick(context, j * 150, i * 100, b));
            }

        }
    }

    // nastavi pozadie
    private void nacitajPozadie(Context context) {
        pozadie = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pozadie_score));
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    protected void onDraw(Canvas canvas) {
        // creates a background only once
        if (roztiahnuty == null) {
            roztiahnuty = Bitmap.createScaledBitmap(pozadie, size.x, size.y, false);
        }
        canvas.drawBitmap(roztiahnuty, 0, 0, paint);

        // draw the ball
        paint.setColor(Color.RED);
        canvas.drawBitmap(redBall, lopticka.getX(), lopticka.getY(), paint);

        //Disegna power up (temporaneo)
        if(pUp.getSpawned() && mode == 1 && !pUp.getPwrUp().isRecycled()){
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(pUp.getPwrUp(), pUp.getX(), pUp.getY(), paint );
        }

        if(level == 0){
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(boss.getBoss(), boss.getX(), boss.getY(), paint );
            if(vulnerable){
                for (int i = 0; i < bossLife.size(); i++) {
                    Heart b = bossLife.get(i);
                    //r = new RectF(b.getX(), b.getY(), b.getX() + 100, b.getY() + 80); //pixel width
                    canvas.drawBitmap(b.getHeart(), b.getX(), b.getY(), paint);
                }
            }
        }


        // draw paddle
        paint.setColor(Color.WHITE);
        r = new RectF(paddle.getX(), paddle.getY(), paddle.getX() + 200, paddle.getY() + 40);
        canvas.drawBitmap(paddle_p, null, r, paint);

        //draw bricks
        paint.setColor(Color.GREEN);
        for (int i = 0; i < zoznam.size(); i++) {
            Brick b = zoznam.get(i);
            r = new RectF(b.getX(), b.getY(), b.getX() + 100, b.getY() + 80);
            canvas.drawBitmap(b.getBrick(), null, r, paint);
        }

        // vykresli text
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("" + lifes, 400, 100, paint);
        canvas.drawText("" + score, 700, 100, paint);

        // v pripade prehry vykresli "Game over!"
        if (gameOver) {
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            canvas.drawText("Game over!", size.x / 4, size.y / 2, paint);
        }
    }

    // skontroluje či sa lopticka nedotkla okraju
    private void skontrolujOkraje(boolean b) {
        if (lopticka.getX() + lopticka.getxRychlost() >= size.x - 60) {
            lopticka.zmenSmer("prava");
        } else if (lopticka.getX() + lopticka.getxRychlost() <= 0) {
            lopticka.zmenSmer("lava");
        } else if (lopticka.getY() + lopticka.getyRychlost() <= 150) {
            lopticka.zmenSmer("hore");
        } else if (lopticka.getY() + lopticka.getyRychlost() >= size.y - 200 && b == false) {
            skontrolujZivoty();
        } else if(lopticka.getY() + lopticka.getyRychlost() >= size.y - 200 && b == true) {
            lopticka.zmenSmer();
        }
    }

    // skontroluje stav hry. či ma životy alebo či hra konči
    private void skontrolujZivoty() {
        if (lifes == 1 ) {
            gameOver = true;
            start = false;
            invalidate();
        } else {
            lifes--;
            lopticka.setX(size.x / 2);
            lopticka.setY(size.y - 480);
            lopticka.vytvorRychlost();
            lopticka.zvysRychlost(level);
            start = false;
        }
    }

    int timer = 0;
    // kazdy krok kontroluje ci nedoslo ku kolizii, k prehre alebo k vyhre atd
    public void update() {
        if (start) {
            if(level == 0){
                bossfight(context);
            }
            else{
                switch(mode){
                    case 0:
                        classic();

                    case 1:
                        arkanull();

                    case 2:
                        //ROGUE

                }
            }
        }
    }

    public void classic(){
        vyhra();
        skontrolujOkraje(false);
        lopticka.NarazPaddle(paddle.getX(), paddle.getY());
        for (int i = 0; i < zoznam.size(); i++) {
            Brick b = zoznam.get(i);
                if (lopticka.NarazBrick(b.getX(), b.getY(), false)) {
                    zoznam.remove(i);
                    score = score + 80;

                }
        }
        lopticka.pohni();
    }

    public void arkanull(){

        vyhra();
        if(timer != 0){
            skontrolujOkraje(true);
        }
        else{
            skontrolujOkraje(false);
        }

        lopticka.NarazPaddle(paddle.getX(), paddle.getY());
        for (int i = 0; i < zoznam.size(); i++) {
            Brick b = zoznam.get(i);
            if(b.getType() == 3){
                b.move(size);
            }
            if(timer != 0 ) {
                if (lopticka.NarazBrick(b.getX(), b.getY(), true)) {
                    if(b.getHp() == 0){
                        zoznam.remove(i);
                        score = score + 80;
                    }
                    else{
                        zoznam.remove(i);
                        score = score + 100;
                    }

                }
                timer--;
            }


            else if (lopticka.NarazBrick(b.getX(), b.getY(), false) ) {
                if(b.getHp() == 0){
                    if(rand.nextInt(10 + (2 * difficulty)) == 0 && !pUp.getSpawned()){
                        pUp = new PowerUp(context);
                        pUp.spawn(b.getX(), b.getY());
                    }
                    zoznam.remove(i);

                    score = score + 80;
                }
                else{
                    b.hit();
                    score = score + 20;
                }

            }
        }


        lopticka.pohni();
        if(pUp.getPwrUp() != null){
            if(!(pUp.getPwrUp().isRecycled())){
                updatePwrUp();
            }
        }

    }

    public void updatePwrUp(){
        pUp.fall();
        if(pUp.touchPaddle(paddle.getX(),paddle.getY())){
            pUp.getPwrUp().recycle();
            timer = 3000;
            pUp.setSpawned(false);
        }
        if(pUp.touchLimit(pUp.getX(), size.y)){
            pUp.getPwrUp().recycle();
            pUp.setSpawned(false);
        }

    }

    public void zastavSnimanie() {
        sManager.unregisterListener(this);
    }

    public void spustiSnimanie() {
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    // zmena akcelerometera
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            paddle.setX(paddle.getX() - event.values[0] - event.values[0]);

            if (paddle.getX() + event.values[0] > size.x - 240) {
                paddle.setX(size.x - 240);
            } else if (paddle.getX() - event.values[0] <= 20) {
                paddle.setX(20);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // sluzi na pozastavenie hry v pripade novej hry
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameOver == true && start == false) {
            score = 0;
            lifes = 3;
            level = 0;
            resetLevel();
            gameOver = false;

        } else {
            start = true;
        }
        return false;
    }

    // nastavi hru na zaciatok
    private void resetLevel() {
        lopticka.setX(size.x / 2);
        lopticka.setY(size.y - 480);
        lopticka.vytvorRychlost();
        zoznam = new ArrayList<Brick>();
        vygenerujBricks(context, difficulty);
    }

    // zisti ci hrac vyhral alebo nie
    private void vyhra() {
        if (zoznam.isEmpty()) {
            if(pUp.getSpawned()){
                pUp.pwrUp().recycle();
                pUp.setSpawned(false);
            }
            timer = 0;
            ++level;
            resetLevel();
            lopticka.zvysRychlost(level);
            start = false;
        }
    }
}
