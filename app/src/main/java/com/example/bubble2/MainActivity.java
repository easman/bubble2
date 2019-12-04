package com.example.bubble2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CYLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button b1 = findViewById(R.id.button1);
        Bubble.makeBubble(MainActivity.this, b1, "这是一个测试的标题1111", null).show();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bubble.makeBubble(MainActivity.this, b1, "这是一个测试的标题1111", null).show();
            }
        });


        final Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bubble.makeBubble(MainActivity.this, b2, "这是一个测试的标题2222222222这是一个测试的标题2222222222这是一个测试的标题2222222222这是一个测试的标题2222222222", null).show();
            }
        });


        final Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bubble.makeBubble(MainActivity.this, b3, "这是一个测试的标题3", null).show();
                Paint paint = new Paint();
                Rect rect = new Rect();
                paint.getTextBounds("这是一个测试的标题3", 0, "这是一个测试的标题3".length(), rect);

                paint.setTextSize(16);
                float strWidth = paint.measureText("这是一个测试的标题3");

                int w = rect.width();
                int h = rect.height();

                Log.d(TAG, "text strWidth:" + strWidth + " h:" + h);
            }
        });
    }

    private int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
