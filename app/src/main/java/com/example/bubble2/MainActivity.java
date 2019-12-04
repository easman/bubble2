package com.example.bubble2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;
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
                Bubble.makeBubble(MainActivity.this, b2, "这是一个测试的标题2222222222", null).show();
            }
        });


        final Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bubble.makeBubble(MainActivity.this, b3, "这是一个测试的标题3", null).show();
            }
        });
    }
}
