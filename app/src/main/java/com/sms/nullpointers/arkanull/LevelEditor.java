package com.sms.nullpointers.arkanull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.sms.nullpointers.arkanull.R;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LevelEditor extends View {
    private Bitmap background;
    private Bitmap extended;
    private Point point;
    private Display display;
    private Point size;
    private Paint paint;
    private int screenY;
    private RectF r;
    private Canvas canvas;
    private ArrayList<Brick> list = new ArrayList<Brick>();
    private final String FILE_NAME = "customLevel.tmp";
    Context context;
    Bitmap undo;
    Button myButton;
    boolean saved;
    int x;
    int y;
    Brick brick;
    int type;

    public LevelEditor(Context context) {
        super(context);
        type = 0;
        this.context = context;
        setBackground(context);
        paint = new Paint();
        this.undo = BitmapFactory.decodeResource(getResources(), R.drawable.undo);
        this.saved = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Creates a background only once
        this.canvas = canvas;
        if (extended == null) {
            extended = Bitmap.createScaledBitmap(background, size.x, size.y, false);
        }

        canvas.drawBitmap(extended, 0, 0, paint);

        paint.setColor(Color.GREEN);
            for (int i = 0; i < list.size(); i++) {
                Brick b = list.get(i);
                r = new RectF(b.getX(), b.getY(), b.getX() + 100, b.getY() + 80);
                canvas.drawBitmap(b.getBrick(), null, r, paint);
            }
            paint.setColor(Color.WHITE);
            canvas.drawCircle(0, size.y - size.y / 10, 300, paint);
            canvas.drawCircle(size.x, size.y - size.y / 10, 300, paint);

            paint.setColor(Color.RED);
            canvas.drawCircle(size.x / 2, size.y - size.y / 4, 200, paint);

            brick = new Brick(context, size.x / 18, size.y - size.y / 6 , type);
            r = new RectF(brick.getX(), brick.getY(), brick.getX() + 100, brick.getY() + 80);
            canvas.drawBitmap(brick.getBrick(), null, r, paint);

            canvas.drawBitmap(undo, size.x / 2 + size.x / 3, size.y - size.y / 6 - size.y / 50, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(84);
            canvas.drawText("SAVE", size.x / 2 - 100,size.y - size.y / 4 , paint);

            if(saved){
                canvas.drawText("SAVED", size.x / 2, size.y / 2, paint);
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


    public void update(){
        //Empty
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(list.size() < 30 && motionEvent.getY() <= size.y / 2 && motionEvent.getY() > size.y / 12 && motionEvent.getX() < size.x - size.x / 12){
            Brick b = new Brick(context, motionEvent.getX(), motionEvent.getY(), type);
            list.add(b);
        }

        if(motionEvent.getY() >= size.y - size.y / 6 && motionEvent.getX() <= size.x / 6){
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_UP:
                    if(type + 1 < 4){
                        type++;
                    }
                    else{
                        type = 0;
                    }
                    break;
            }
        }



        if(motionEvent.getY() > size.y - size.y / 3 && motionEvent.getY() < size.y - size.y / 6  && motionEvent.getX() >= size.x / 6 && motionEvent.getX() <= size.x - size.x / 4){

            ArrayList<BrickData> bd = new ArrayList<>();

            if(!list.isEmpty()){
                for(int i = 0; i < list.size(); i++){
                    Brick b = list.get(i);
                    bd.add(new BrickData(b.getX(), b.getY(), b.getType()));
                }
                try{
                    FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(bd);
                    os.close();
                    fos.close();
                    Log.i("Entra nel try", "Salva nel file");
                    saved = true;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }

        if(motionEvent.getY() >= size.y - 350 && motionEvent.getX() >= size.x - 250){
            if(!list.isEmpty()){
                list.remove(list.size() - 1);
            }

        }

        return true;
    }
}
