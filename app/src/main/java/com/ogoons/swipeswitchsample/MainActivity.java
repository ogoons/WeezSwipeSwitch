package com.ogoons.swipeswitchsample;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ogoons.swipeswitchsample.widget.WeezSwipeSwitch;

public class MainActivity extends AppCompatActivity {
    private WeezSwipeSwitch mSwipeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeSwitch = (WeezSwipeSwitch) findViewById(R.id.swipe_switch);
        mSwipeSwitch.setSwipeOverlayColor(100, ContextCompat.getColor(this, R.color.colorAccent));
        mSwipeSwitch.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSwipeSwitch.setOnSwipeSwitchListener(new WeezSwipeSwitch.OnSwipeSwitchListener() {
            @Override
            public void onSwitchOn() {
                Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
