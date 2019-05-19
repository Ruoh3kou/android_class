package com.cbj.sv123022016027;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    public MediaPlayer mediaPlayer;

    class MyBinder extends Binder {
        public void Play(int mp3) {
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), mp3);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                } else {
                    int position = getCurrentProgress();
                    mediaPlayer.seekTo(position);
                    try {
                        mediaPlayer.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void Pause() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else if (mediaPlayer != null && (!mediaPlayer.isPlaying())) {
                mediaPlayer.start();
            }
        }

        public void Check(int mp3) {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            } else {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), mp3);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
        }
    }

    public int getCurrentProgress() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        } else if (mediaPlayer != null && (!mediaPlayer.isPlaying())) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
