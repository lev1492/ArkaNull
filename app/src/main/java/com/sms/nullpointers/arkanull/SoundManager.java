package com.sms.nullpointers.arkanull;

import android.content.Context;
import android.media.MediaPlayer;

import com.sms.nullpointers.arkanull.R;

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

    public void playHrtSound(boolean t){
        if(t){
            heartHit.seekTo(0);
            heartHit.start();
        }
    }

    public void playBrickHit(boolean t){
        if(t){
            brickHit.seekTo(0);
            brickHit.start();
        }

    }

    public void playBounce(boolean t){
        if(t){
            bounce.seekTo(0);
            bounce.start();
        }

    }

    public void playPwrUp(boolean t){
        if(t){
            pwrUpSound.seekTo(0);
            pwrUpSound.start();
        }
    }

    public MediaPlayer getGameMusic(){
        return gameMusic;
    }

}
