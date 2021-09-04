package com.example.android.arkanull;

import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game extends View implements SensorEventListener, View.OnTouchListener {

    private Bitmap background;
    private Bitmap redBall;
    private Bitmap extended;
    private Bitmap paddle_p;
    private Bitmap pwrUp;
    private Point point;
    private Display display;
    private Point size;
    private Paint paint;
    private int screenX;
    private DatabaseReference reference;
    private Ball ball;
    private ArrayList<Brick> list;
    private ArrayList<PowerUp> pList;
    private ArrayList<Heart> bossLife;
    private Paddle paddle;
    private PowerUp pUp;
    private Bossfight boss;
    private boolean vulnerable;

    private RectF r;

    private SensorManager sManager;
    private Sensor accelerometer;
    private int STATE_INPUT = -1;
    private boolean SOUND;
    private int scoreUpdate = 0;
    private int lifes;
    private int score;
    private int level;
    private int mode;

    //This variable serves as timer for the power-up effects
    int timer = 0;

    private boolean start;
    private boolean gameOver;
    private Context context;
    boolean paused = true;
    private Random rand = new Random();

    public final static int EASY = 0;
    public final static int NORMAL = 1;
    public final static int HARD = 2;

    public final static String[] GAME_MODE = {"Classic", "Ranking", "Challange", "Career"};

    //Classic is the original game without modifications
    public final static int CLASSIC = 0;

    //Arkanull is the main game, with power-ups and different levels
    public final static int ARKANULL = 1;

    //Career is the game that stops the game when the bricks end.
    public final static int MULTIPLAYER = 2;

    //Career is the game that stops the game when the bricks end.
    public final static int CAREER = 3;

    private final int BOSS_LVL = 1;
    private int difficulty;
    private int phase;
    private SoundManager soundManager;
    Record record = new Record();
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Record> records = new ArrayList<>();
    private LevelGenerator levelGenerator = new LevelGenerator();


    /**
     *
     * @param context Context is needed for passing it to classes that extends View (Brick, PowerUp,Hearts..)
     * @param lifes The current lifes of the player
     * @param score The current score of the player
     * @param g_Mode Indicates the game mode selected
     * @param diff Indicates the difficulty selected
     */
    public Game(Context context, int lifes, int score, int g_Mode, int diff) {
        super(context);
        difficulty = diff;
        paint = new Paint();
        mode = g_Mode;
        soundManager = new SoundManager(context);
        // set context, lives, scores and levels
        this.context = context;
        this.lifes = lifes;
        this.score = score;
        level = 0;


        // start a gameOver to find out if the game is stopped and if the player has lost it
        start = false;
        gameOver = false;

        // get input type from impostazioni's class
        STATE_INPUT = ImpostazioniActivity.getTypeInput();
        SOUND =  ImpostazioniActivity.isSound();

        if (STATE_INPUT == ImpostazioniActivity.getAccelerometerInput()) {
            accelerometerInput(context);
        }

        setBackground(context);

        // create a bitmap for the ball and paddle
        redBall = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
        paddle_p = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        //pwrUp = BitmapFactory.decodeResource(getResources(), R.drawable.pwrup);

        // create a new ball, paddle, power up and list of bricks
        ball = new Ball(size.x / 2, size.y - 480 );
        paddle = new Paddle(size.x / 2, size.y - 400);
        pUp = new PowerUp(context);
        boss = new Bossfight(context, size.x / 3 , size.y / 12);
        list = new ArrayList<Brick>();

        //Creates an array with hearts for the bossfight
        bossLife = new ArrayList<Heart>();
        phase = 0;
        //If the level isn't the boss one, generates bricks
        if(level != BOSS_LVL){
            generateBricks(context, difficulty);
        }
        this.setOnTouchListener(this);

    }

    private boolean accelerometerInput(Context context) {
        // creates accelerometer and SensorManager
        this.sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return true;
    }

    public void detectionStop() {
        if(STATE_INPUT == ImpostazioniActivity.getAccelerometerInput()){
            sManager.unregisterListener(this);
        }
    }

    public void detectionStart() {
        if(STATE_INPUT == ImpostazioniActivity.getAccelerometerInput()) {
            sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // per rendere il movimento del paddle fluido quando si mantiene il dito premuto sul touch...on developement
    /*AtomicBoolean actionDownFlag = new AtomicBoolean(true);

    Thread MoveAtLeftThread = new Thread(new Runnable(){
        public void run(){
            while(actionDownFlag.get()){
                if ( paddle.getX() - 100 >= 20 ) {
                    paddle.setMovementState(paddle.LEFT);
                    paddle.setX(paddle.getX() - 1);
                }
            }
            paddle.setMovementState(paddle.STOPPED);

        }
    });

    Thread MoveAtRightThread = new Thread(new Runnable(){
        public void run(){
            while(actionDownFlag.get()){
                if ( paddle.getX() + 100 < size.x - 200) {
                    paddle.setMovementState(paddle.RIGHT);
                    paddle.setX(paddle.getX() + 1);
                }
            }
            paddle.setMovementState(paddle.STOPPED);
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        point = new Point();
        display.getSize(point);
        screenX = point.x;
        if (STATE_INPUT == ImpostazioniActivity.getTouchInput()) {
            //switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                // Player has touched the screen
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {

                paused = false;

                if (motionEvent.getX() > screenX / 2) {
                    if (paddle.getX() + 100 < size.x - 200) {
                        MoveAtRightThread.start();
                    }

                } else {
                    if (paddle.getX() - 100 >= 20) {
                        MoveAtLeftThread.start();
                    }

                }
            }

                // Player has removed finger from screen
                //case MotionEvent.ACTION_UP:
            if(motionEvent.getAction()==MotionEvent.ACTION_UP){
               // paddle.setMovementState(paddle.STOPPED);
                actionDownFlag.set(false);
            }
        }
        return true;
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        point = new Point();
        display.getSize(point);
        screenX = point.x;
        if (STATE_INPUT == ImpostazioniActivity.getTouchInput()) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    paused = false;

                    if (motionEvent.getX() > screenX / 2) {
                        paddle.setMovementState(paddle.RIGHT);
                        if ( paddle.getX() + 100 < size.x - 200) {
                            paddle.setX(paddle.getX() + 100);
                        } else {
                            paddle.setX(size.x-200);
                        }

                    } else {
                        paddle.setMovementState(paddle.LEFT);
                        if ( paddle.getX() - 100 >= 20 ) {
                            paddle.setX(paddle.getX() - 100);
                        } else {
                            paddle.setX(20);
                        }

                    }

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    paddle.setMovementState(paddle.STOPPED);
                    break;
            }
        }
        return true;
    }

    // change accelerometer
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

    // suspend the game in case of a new game
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameOver == true && start == false) {
            score = 0;
            lifes = 3;
            level = 0;
            resetLevel();
            gameOver = false;
            bossLife.clear();

        } else {
            start = true;
        }
        return false;
    }

    /**
     * This method spawn bricks according to the level reached and the difficulty selected
     * @param context
     * @param difficulty
     */
    private void generateBricks(Context context, int difficulty) {
        if (level != BOSS_LVL) {
            if (level > 5) {
                levelGenerator.generateEndless(context, level, size, difficulty);
            } else {
                switch (difficulty) {
                    case EASY:
                        list = levelGenerator.generateEasy(context, level, size);
                        break;
                    case NORMAL:
                        list = levelGenerator.generateNormal(context, level, size);
                        break;
                    case HARD:
                        list = levelGenerator.generateHard(context, level, size);
                        break;
                }
            }
        }
    }

    /**
     * This method generates the bossfight
     * @param context
     */
    private void bossfight(Context context){
        boss.move(size);

        if(phase == 0){
            phase++;
            list = levelGenerator.generateBoss(context, size, phase);
            soundManager.playBossMusic(SOUND);
            for(int i = 0; i < 3 ; i++) {
                switch (i) {
                    case 0:
                        bossLife.add(new Heart(context, size.x / 25 , size.y / 8));
                        break;
                    case 1:
                        bossLife.add(new Heart(context, size.x / 2 - size.x / 10 , size.y / 3));
                        break;
                    case 2:
                        bossLife.add(new Heart(context, size.x - (size.x / 4)  , size.y / 8));
                        break;
                }
            }
        }

        bossWon();
        vulnerable = isVulnerable();

        //If the boss is vulnerable then spawn the hearts
        if(vulnerable){
            for(int i = 0; i < bossLife.size(); i++){
                Heart d = bossLife.get(i);
                if(d.isHit(ball.getX(),ball.getY())){
                    soundManager.playHrtSound(SOUND);
                    //If you hit the heart the ball bounce
                    ball.changeDirection();
                    bossLife.remove(i);
                    vulnerable = false;

                    //If there are lifes left, go to the next phase
                    if(phase < 3){
                        phase++;
                        list = levelGenerator.generateBoss(context, size, phase);
                    }
                }
            }
        }
        checkEdges(false);
        if(ball.ImpactPaddle(paddle.getX(), paddle.getY())) soundManager.playBounce(SOUND);

        //Check for collisions with bricks
        for (int i = 0; i < list.size(); i++) {
            Brick b = list.get(i);

            //Move the brick if it has the right type
            if(b.getType() == 3){
                b.move(size);
            }

            //Detect collision with the bricks if the power up effect is not active
            if (ball.ImpactBrick(b.getX(), b.getY(), false) ) {
                if(b.getHp() == 0){
                    list.remove(i);
                    soundManager.playBrickHit(SOUND);
                    score = score + 80;
                }
                else{
                    b.hit();
                    soundManager.playBounce(SOUND);
                    score = score + 20;
                }
            }
        }
        ball.setSpeed();
    }

    /**
     * This method returns true if the bricks on the screens during the bossfight gets all destroyed
     * @return
     */
    private boolean isVulnerable(){
        if(list.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * If the arrayList containing the hearts of the boss is empty load the next level and raise the score
     */
    private void bossWon(){
        if(bossLife.isEmpty()){
            score = score + 5000;
            ++level;
            resetLevel();
            ball.increaseSpeed(level);
            start = false;
            soundManager.stopMusic();
            soundManager.playGameMusic(SOUND);
        }
    }

    // set the background
    private void setBackground(@NonNull Context context) {
        background = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.background_score));
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Creates a background only once
        if (extended == null) {
            extended = Bitmap.createScaledBitmap(background, size.x, size.y, false);
        }
        canvas.drawBitmap(extended, 0, 0, paint);

        //Draw the ball
        paint.setColor(Color.RED);
        canvas.drawBitmap(redBall, ball.getX(), ball.getY(), paint);

        //Draw power ups
        if(pUp.getSpawned() && (mode == ARKANULL || mode == MULTIPLAYER) && !pUp.getPwrUp().isRecycled()){
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(pUp.getPwrUp(), pUp.getX(), pUp.getY(), paint );
        }

        //If the level is the boss level, draw the boss and if he is vulnerable draw the hearts
        if(level == BOSS_LVL){
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(boss.getBoss(), boss.getX(), boss.getY(), paint );
            if(vulnerable){
                for (int i = 0; i < bossLife.size(); i++) {
                    Heart b = bossLife.get(i);
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
        for (int i = 0; i < list.size(); i++) {
            Brick b = list.get(i);
            r = new RectF(b.getX(), b.getY(), b.getX() + 100, b.getY() + 80);
            canvas.drawBitmap(b.getBrick(), null, r, paint);
        }

        // draw text
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("" + lifes, 400, 100, paint);
        canvas.drawText("" + score, 700, 100, paint);
        if ( mode == MULTIPLAYER) {
            int score2 = ListaSfideActivity.SCORE2;
            canvas.drawText("" + score2, 1000, 100, paint);
        }


        // draw "Game over!" in case of loss
        if (gameOver) {
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            canvas.drawText("Game over!", size.x / 4, size.y / 2, paint);
        }
    }

    /**
     * check that the ball has not touched the edge of the screen, if the parameter is set to true, it will
     * bounce even if it touches the bottom edge
     * @param b
     */
    private void checkEdges(boolean b) {
        if (ball.getX() + ball.getxSpeed() >= size.x - 60) {
            soundManager.playBounce(SOUND);
            ball.changeDirection("prava");
        } else if (ball.getX() + ball.getxSpeed() <= 0) {
            soundManager.playBounce(SOUND);
            ball.changeDirection("lava");
        } else if (ball.getY() + ball.getySpeed() <= 150) {
            soundManager.playBounce(SOUND);
            ball.changeDirection("hore");
        } else if (ball.getY() + ball.getySpeed() >= size.y - 200 && b == false) {
            checkLives();
        } else if(ball.getY() + ball.getySpeed() >= size.y - 200 && b == true) {
            soundManager.playBounce(SOUND);
            ball.changeDirection();
        }
    }



    //If the ball falls, it lower the lifes of the player or sets the game over if the player ran out of lifes
    private void checkLives() {
        if (lifes == 1 ) {
            level = 0;
            gameOver = true;
            start = false;
            phase = 0;
            soundManager.stopMusic();
            invalidate();
            String id = MainActivity.getIdSfida();
            Log.d("idGAmeChallange", "l'id è" + id);
            DAORecord daoRecord = new DAORecord(GAME_MODE[mode]);
            mReference = daoRecord.getDatabaseReference();
            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    FirebaseUser user = LoginActivity.getmFirebaseAuth().getCurrentUser();
                    switch(mode){
                        case 0:
                            break;
                        case 1:
                            daoRecord.saveScore(snapshot.child(DAORecord.PLAYER1), user, score);
                            break;
                        case 2:
                            if(id != null) {
                                Log.d("idGAmeReplyChallange", "l'id è" + id);
                                daoRecord.replyChallange(user, score, id);
                            } else {
                                daoRecord.newChallange(user, score);
                                Log.d("idGAmeNewChallange", "l'id è" + id);
                            }
                            break;
                        case 3:
                            career();
                            break;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            lifes--;
            ball.setX(size.x / 2);
            ball.setY(size.y - 480);
            ball.createSpeed();
            ball.increaseSpeed(level);
            start = false;
        }
    }




    //This method gets called continuously, it organize the screen according to level and game mode
    public void update() {

        if(level == BOSS_LVL){
            if(soundManager.getGameMusic().isPlaying()) soundManager.playGameMusic(false);
            bossfight(context);
        }

        else if (start) {
            switch(mode){
                case 0:
                    classic();
                    break;
                case 1:
                case 2:
                    arkanull();
                    break;
                case 3:
                    career();
                    break;
                default:
                    classic();
                    break;
            }
        }
    }

    /**
     * The classic game modes has no power ups or bricks with different effects
     */
    public void classic(){
        win();
        checkEdges(false);
        ball.ImpactPaddle(paddle.getX(), paddle.getY());
        for (int i = 0; i < list.size(); i++) {
            Brick b = list.get(i);
            if (ball.ImpactBrick(b.getX(), b.getY(), false)) {
                list.remove(i);
                soundManager.playBrickHit(SOUND);
                score = score + 80;
            }
        }
        ball.setSpeed();
    }

    /**
     * This mode has power ups and the bricks have hp or move according to their type
     */
    public void arkanull(){
        if(!soundManager.getGameMusic().isPlaying()) soundManager.playGameMusic(SOUND);

        win();

        //If the timer is different than 0, then the power up is in effect and by passing true we make the ball bounce even if it falls
        if(timer != 0){
            checkEdges(true);
        }
        else{
            checkEdges(false);
        }

        if(ball.ImpactPaddle(paddle.getX(), paddle.getY())) soundManager.playBounce(SOUND);

        //This for cycle detects collision with the bricks
        for (int i = 0; i < list.size(); i++) {
            Brick b = list.get(i);

            //If the brick is the specified type, it moves
            if (b.getType() == 3) {
                b.move(size);
            }

            //The power up is in effect, by passing true to ImpactBrick we don't make the ball bounce off bricks and it pierce through every type of them
            if (timer != 0) {
                if (ball.ImpactBrick(b.getX(), b.getY(), true)) {
                    if (b.getHp() == 0) {
                        list.remove(i);
                        soundManager.playBrickHit(SOUND);
                        score = score + 80;
                    } else {
                        soundManager.playBrickHit(SOUND);
                        list.remove(i);
                        score = score + 100;
                    }
                }
                timer--;
            }

            //If the power up is not in effect, by passing false to .ImpactBrick we make the ball bounce off bricks and the bricks also have hit points before getting destroyed
            else if (ball.ImpactBrick(b.getX(), b.getY(), false)) {
                if (b.getHp() == 0) {

                    //Randomly spawns a power up if there is none already active or on screen
                    if (rand.nextInt(10 + (2 * difficulty)) == 0 && !pUp.getSpawned()) {
                        pUp = new PowerUp(context);
                        pUp.spawn(b.getX(), b.getY());
                    }
                    soundManager.playBrickHit(SOUND);
                    list.remove(i);

                    score = score + 80;
                } else {
                    soundManager.playBounce(SOUND);
                    b.hit();
                    score = score + 20;
                }

            }
        }
        ball.setSpeed();
        if(pUp.getPwrUp() != null){
            if(!(pUp.getPwrUp().isRecycled())){
                updatePwrUp();
            }
        }
    }

    /**
     * The career game modes with different levels
     */

    public void career(){
        if(list.size()==0){
            paused = true;
            CarrieraActivity.nextLevel++;
            Toast.makeText(this.getContext(),"Hai Vinto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getContext(), CarrieraActivity.class);
            intent.putExtra("GameMode", CAREER);
            this.getContext().startActivity(intent);
        }
        win();
        checkEdges(false);
        ball.ImpactPaddle(paddle.getX(), paddle.getY());
        for (int i = 0; i < list.size(); i++) {
            Brick b = list.get(i);
            if (ball.ImpactBrick(b.getX(), b.getY(), false)) {
                list.remove(i);
                soundManager.playBrickHit(SOUND);
                score = score + 80;

            }
        }

        ball.setSpeed();
        /*if(list.size()==0){
            Toast.makeText(this.getContext(),"Hai Vinto", Toast.LENGTH_SHORT).show();
            paused = true;
            Intent intent = new Intent(this.getContext(), CarrieraActivity.class);
            this.getContext().startActivity(intent);
        }*/
   }

    /**
     * If a power-ups spawns, it follow those instruction to fall and detect collision with the paddle (grants power up)
     * or the pit (there is no effect)
     */
    public void updatePwrUp(){
        pUp.fall();
        if(pUp.touchPaddle(paddle.getX(),paddle.getY())){
            pUp.getPwrUp().recycle();
            soundManager.playPwrUp(SOUND);
            timer = 3000;
            pUp.setSpawned(false);
        }
        if(pUp.touchLimit(pUp.getX(), size.y)){
            pUp.getPwrUp().recycle();
            pUp.setSpawned(false);
        }
    }

    /**
     * Reset the level with new bricks
     */
    private void resetLevel() {
        ball.setX(size.x / 2);
        ball.setY(size.y - 480);
        ball.createSpeed();
        list = new ArrayList<Brick>();
        generateBricks(context, difficulty);
    }

    /**
     * If you destroyed every brick in a level, this method destroys power-ups created and reset the timer, then
     * it loads the next level
     */
    private void win() {
        if (list.isEmpty()) {
            if(pUp.getSpawned()){
                pUp.pwrUp().recycle();
                pUp.setSpawned(false);
            }
            timer = 0;
            ++level;
            resetLevel();
            ball.increaseSpeed(level);
            start = false;
        }
    }
}