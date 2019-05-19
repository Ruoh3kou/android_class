package com.cbj.sv123022016027;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView btn_last;
    private ImageView btn_next;
    private ImageView btn_play;
    private TextView t_mp3;

    private myConn conn;
    private Intent intent;
    MusicService.MyBinder binder;

    private int[] musics = new int[3];
    private int curMp3 = 0;
    private boolean curPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_last = (ImageView) findViewById(R.id.btn_last);
        btn_next = (ImageView) findViewById(R.id.btn_next);
        btn_play = (ImageView) findViewById(R.id.btn_play);
        t_mp3 = (TextView) findViewById(R.id.t_mp3);
        conn = new myConn();
        intent = new Intent(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

        musics[0] = R.raw.m01;
        musics[1] = R.raw.m02;
        musics[2] = R.raw.m03;

        t_mp3.setText(musics[0]);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPause) {
                    curMp3 %= 3;
                    binder.Play(musics[curMp3]);
                    curPause = false;
                    btn_play.setImageResource(R.drawable.pause);
                } else {
                    curPause = true;
                    binder.Pause();
                    btn_play.setImageResource(R.drawable.play);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curMp3 += 1;
                curMp3 %= 3;
                binder.Check(musics[curMp3]);
                curPause = false;
                btn_play.setImageResource(R.drawable.pause);
                t_mp3.setText(musics[curMp3]);
            }
        });

        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curMp3 += 2;
                curMp3 %= 3;
                binder.Check(musics[curMp3]);
                curPause = false;
                btn_play.setImageResource(R.drawable.pause);
                t_mp3.setText(musics[curMp3]);
            }
        });
    }

    private class myConn implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.MyBinder) service;
        }

        public void onServiceDisconnected(ComponentName name) {
        }

    }

    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
