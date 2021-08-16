package com.example.android.arkanull;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer heartHit;
    private MediaPlayer brickHit;
    private MediaPlayer pwrUpSound;
    private MediaPlayer bounce;

    public SoundManager(Context context){
        this.heartHit = MediaPlayer.create(context, R.raw.cuorecolpito);
        this.brickHit = MediaPlayer.create(context, R.raw.mattoncinodistrutto);
        this.pwrUpSound = MediaPlayer.create(context, R.raw.powerup);
        this.bounce = MediaPlayer.create(context, R.raw.rimbalzo);

        this.heartHit.setLooping(false);
        this.brickHit.setLooping(false);
        this.pwrUpSound.setLooping(false);
        this.bounce.setLooping(false);
    }

    public void playHrtSound(){
        heartHit.seekTo(0);
        heartHit.start();
    }

    public void playBrickHit(){
        brickHit.seekTo(0);
        brickHit.start();
    }

    public void playBounce(){
        bounce.seekTo(0);
        bounce.start();
    }

    public void playPwrUp(){
        pwrUpSound.seekTo(0);
        pwrUpSound.start();
    }

}
