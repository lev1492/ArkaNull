package com.example.android.arkanull;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer heartHit;
    private MediaPlayer brickHit;
    private MediaPlayer pwrUpSound;
    private MediaPlayer bounce;
    private MediaPlayer bossMusic;
    private MediaPlayer gameMusic;

    public SoundManager(Context context){
        this.heartHit = MediaPlayer.create(context, R.raw.cuorecolpito);
        this.brickHit = MediaPlayer.create(context, R.raw.mattoncinodistrutto);
        this.pwrUpSound = MediaPlayer.create(context, R.raw.powerup);
        this.bounce = MediaPlayer.create(context, R.raw.rimbalzo);
        this.bossMusic = MediaPlayer.create(context, R.raw.megalovania);
        this.gameMusic = MediaPlayer.create(context, R.raw.deviltrigger);

        this.bossMusic.setLooping(true);
        this.gameMusic.setLooping(true);
        this.heartHit.setLooping(false);
        this.brickHit.setLooping(false);
        this.pwrUpSound.setLooping(false);
        this.bounce.setLooping(false);
    }

    public void stopMusic(){
        if(gameMusic.isPlaying()) gameMusic.pause();
        if(bossMusic.isPlaying()) bossMusic.pause();
    }


    public void playGameMusic(boolean t){
        if(t){
            gameMusic.seekTo(0);
            gameMusic.start();
        }
        else gameMusic.pause();
    }

    public void playBossMusic(boolean t){
        if(t){
            bossMusic.seekTo(0);
            bossMusic.start();
        }
        else bossMusic.pause();
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
