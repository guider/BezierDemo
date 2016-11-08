package com.guider.bezierdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, ActivityBezier1.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ActivityBezier2.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, ActivityBezier3.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, ActivityBezier4.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, ActivityBezier5.class));

                break;
            case R.id.button6:
                break;
            case R.id.button7:
                break;
        }
    }
}
