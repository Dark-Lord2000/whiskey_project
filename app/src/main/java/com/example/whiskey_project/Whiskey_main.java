package com.example.whiskey_project;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;


public class Whiskey_main extends Activity{

    private Whiskey_timer whiskeyTimer;
    private SeekBar seekBar;
    private TextView counterText;
    private Handler handler = new Handler();
    int counterTemp = 50;
    private boolean isVolumeDownPressed;
    private boolean isVolumeUpPressed;
    private boolean firstKeyEvent = true;

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
        whiskeyTimer = new Whiskey_timer();
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

            whiskeyTimer.start();
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.postDelayed(() -> {
                if (counterTemp == counterTemp) { //Replace second counterTemp with actual test value
                    whiskeyTimer.stop();
                    Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " seconds");
                }
            }, 800);
        }
    };

    // Volume Key function
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        int action = e.getAction();
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (firstKeyEvent == true) {
                        firstKeyEvent = false;
                        whiskeyTimer.start();
                    }

                    if (counterTemp > 0) {
                        counterTemp--;
                        isVolumeDownPressed = true;
                        counterText.setText(counterTemp + "%");
                        handler.postDelayed(counterUpdater, 100); // Initial delay before acceleration starts
                    }
                    return true;
                }
                break;

            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    if (firstKeyEvent == true) {
                        firstKeyEvent = false;
                        whiskeyTimer.start();
                    }

                    if (counterTemp < 100) {
                        counterTemp++;
                        isVolumeUpPressed = true;
                        counterText.setText(counterTemp + "%");
                        handler.postDelayed(counterUpdater, 100); // Initial delay before acceleration starts
                    }
                    return true;
                }
                break;
        }
        return super.dispatchKeyEvent(e);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Stop the acceleration when the button is released
            isVolumeDownPressed = false;
            handler.removeCallbacks(counterUpdater);
            // Post a delayed callback to check after 500ms
            handler.postDelayed(() -> {
                if (counterTemp == counterTemp) { //Replace second counterTemp with actual test value
                    whiskeyTimer.stop();
                    Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " seconds");
                }
            }, 800);
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Stop the acceleration when the button is released
            isVolumeUpPressed = false;
            handler.removeCallbacks(counterUpdater);
            // Post a delayed callback to check after 500ms
            handler.postDelayed(() -> {
                if (counterTemp == counterTemp) { //Replace second counterTemp with actual test value
                    whiskeyTimer.stop();
                    Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " seconds");
                }
            }, 800);
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