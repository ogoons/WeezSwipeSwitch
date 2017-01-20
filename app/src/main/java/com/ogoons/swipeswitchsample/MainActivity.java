package com.ogoons.swipeswitchsample;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ogoons.swipeswitchsample.widget.WeezSwipeSwitch;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeezSwipeSwitch swipeSwitch = (WeezSwipeSwitch) findViewById(R.id.swipe_switch);
        swipeSwitch.setSwipeOverlayColor(100, ContextCompat.getColor(this, R.color.colorAccent));
        swipeSwitch.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        swipeSwitch.setOnSwipeSwitchListener(new WeezSwipeSwitch.OnSwipeSwitchListener() {
            @Override
            public void onSwitchOn() {
                Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
