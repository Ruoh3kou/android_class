package com.cbj.pic12322016027;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btn_guess;

    private ImageView img_answer;

    private RelativeLayout[] rls = new RelativeLayout[4];

    private ImageView[] imgs = new ImageView[4];
    // 随机显示四个图
    private int[] randomNums = new int[4];
    // 答案
    private int answer = 0;

    // 三种状态
    enum State {
        begin,
        guess,
        again
    }

    State state = State.begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCtrl();
    }

    // 得到随机答案
    private int getAnswer() {
        Random random = new Random();
        return random.nextInt(4);
    }

    // 生成随机排列的1 2 3 4.
    private void getRandom() {
        Random random = new Random();
        randomNums[0] = random.nextInt(4);
        for (int i = 1; i <= 3; ) {
            int x = random.nextInt(4);
            for (int j = 0; j <= i - 1; j++) {
                if (randomNums[j] == x) {
                    break;
                }
                if (j + 1 == i) {
                    randomNums[i] = x;
                    i++;
                }
            }
        }
    }

    // 初始化控件
    private void initCtrl() {
        btn_guess = findViewById(R.id.btn_guess);
        rls[0] = findViewById(R.id.rl_1);
        rls[1] = findViewById(R.id.rl_2);
        rls[2] = findViewById(R.id.rl_3);
        rls[3] = findViewById(R.id.rl_4);

        imgs[0] = findViewById(R.id.img_1);
        imgs[1] = findViewById(R.id.img_2);
        imgs[2] = findViewById(R.id.img_3);
        imgs[3] = findViewById(R.id.img_4);


        for (RelativeLayout rl : rls) {
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 仅在竞猜下可以点击
                    if (state == State.guess) {
                        if (v.getId() == rls[answer].getId()) {
                            InsertMark((RelativeLayout) v, true);
                        } else {
                            InsertMark((RelativeLayout) v, false);
                        }
                        btn_guess.setText("再来一次");
                        state = State.again;
                        img_answer.setImageResource((Integer) imgs[answer].getTag());
                    }
                }
            });
        }

        img_answer = findViewById(R.id.img_answer);

        // 竞猜按钮
        btn_guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_guess.setText("竞猜...");
                ClearMark();
                state = State.guess;
                getRandom();
                answer = getAnswer();
                img_answer.setImageDrawable(null);
                imgs[randomNums[0]].setImageResource(R.drawable.deer);
                imgs[randomNums[0]].setTag(R.drawable.deer);
                imgs[randomNums[1]].setImageResource(R.drawable.santa);
                imgs[randomNums[1]].setTag(R.drawable.santa);
                imgs[randomNums[2]].setImageResource(R.drawable.snowman);
                imgs[randomNums[2]].setTag(R.drawable.snowman);
                imgs[randomNums[3]].setImageResource(R.drawable.tree);
                imgs[randomNums[3]].setTag(R.drawable.tree);
            }
        });
    }

    // 插入对/错图标
    private void InsertMark(RelativeLayout rl, boolean right) {
        ClearMark();
        ImageView imgMark = new ImageView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 20, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imgMark.setLayoutParams(lp);
        if (right)
            imgMark.setImageResource(R.drawable.dui);
        else
            imgMark.setImageResource(R.drawable.cuowu);
        rl.addView(imgMark);
    }

    // 清空已有对/错图标
    private void ClearMark() {
        for (int i = 0; i < 4; i++) {
            if (rls[i].getChildCount() > 1)
                rls[i].removeView(rls[i].getChildAt(rls[i].getChildCount() - 1));
        }

    }
}
