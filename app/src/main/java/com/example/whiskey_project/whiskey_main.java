package com.example.whiskey_project;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;


public class whiskey_main extends Activity{

    private SeekBar seekBar;
    private TextView counterText;
    private Handler handler = new Handler();
    int counterTemp = 50;
    private boolean isVolumeDownPressed;
    private boolean isVolumeUpPressed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();
    }

    private void initialize() {
        counterText = (TextView) findViewById(R.id.counterText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        // setup seekBar listener
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    // SeekBar/Slider function
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Update the TextView with the current value of the SeekBar
            counterText.setText(progress + "%");
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Make a call to start timer
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Start 500ms timer, when timer finishes check if progress == trial value
        }
    };

    // Volume Key function
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (counterTemp > 0) {
                counterTemp--;
                isVolumeDownPressed = true;
                counterText.setText(counterTemp + "%");
                handler.postDelayed(counterUpdater, 100); // Initial delay before acceleration starts
            }
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (counterTemp < 100) {
                counterTemp++;
                isVolumeUpPressed = true;
                counterText.setText(counterTemp + "%");
                handler.postDelayed(counterUpdater, 100); // Initial delay before acceleration starts
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Stop the acceleration when the button is released
            isVolumeDownPressed = false;
            handler.removeCallbacks(counterUpdater);
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Stop the acceleration when the button is released
            isVolumeUpPressed = false;
            handler.removeCallbacks(counterUpdater);
        }
        return super.onKeyUp(keyCode, event);
    }

    private Runnable counterUpdater = new Runnable() {
        @Override
        public void run() {
            if (isVolumeDownPressed && counterTemp > 0) {
                counterTemp--;
                counterText.setText(counterTemp + "%");
                // Adjust the delay to control the acceleration speed
                handler.postAtTime(this, SystemClock.uptimeMillis() + 100);
            }

            if (isVolumeUpPressed && counterTemp < 100) {
                counterTemp++;
                counterText.setText(counterTemp + "%");
                // Adjust the delay to control the acceleration speed
                handler.postAtTime(this, SystemClock.uptimeMillis() + 100);
            }
        }
    };

}