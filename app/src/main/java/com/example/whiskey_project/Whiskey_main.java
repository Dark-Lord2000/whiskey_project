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
    private TextView counterText,groupText, trialText;
    private Handler handler = new Handler();
    int counterTemp = 50;
    int trialNum = 1;
    private boolean isVolumeDownPressed;
    private boolean isVolumeUpPressed;
    private boolean firstKeyEvent = true;

    // parameters from Setup dialog
    String participantCode, sessionCode, groupCode, hand;

    int[] resultNum = {0, 0, 0, 0};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();

        // get parameters from setup
        Bundle b  = getIntent().getExtras();
        participantCode = b.getString("participantCode");
        sessionCode= b.getString("sessionCode");
        groupCode = b.getString("groupCode");
        hand = b.getString("hand");

        Log.d("groupCode", "groupCode: " + groupCode);
        if (groupCode.equals("G01")){
            groupText.setText("Group 1");
            resultNum[0] = 23;
            resultNum[1] = 94;
            resultNum[2] = 36;
            resultNum[3] = 19;

        } else {
            groupText.setText("Group 2");
            resultNum[0] = 85;
            resultNum[1] = 12;
            resultNum[2] = 61;
            resultNum[3] = 43;
        }
        Log.d("resultNum", "resultNum: " + resultNum[0] + ", " + resultNum[1] + ", " + resultNum[2] + ", "+ resultNum[3]);

        trialText.setText("Trial " + trialNum + ": Get the number " + resultNum[0]);

    }

    private void initialize() {
        counterText = (TextView) findViewById(R.id.counterText);
        groupText = (TextView) findViewById(R.id.groupNum);
        trialText = (TextView) findViewById(R.id.trialNum);

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
            Log.d("Seek", "Seekbar Progress: " + seekBar.getProgress());
            Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " milliseconds");

            handler.postDelayed(() -> {
                for (int i = 0; i < resultNum.length; i++){
                    if (seekBar.getProgress() == resultNum[i]) { //Replace second counterTemp with actual test value
                        trialNum++;
                        trialText.setText("Trial " + trialNum + ": Get the number " + resultNum[i + 1]);
                        whiskeyTimer.stop();
                        Log.d("Result", "Success!");

                        Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " milliseconds");
                        if (i == resultNum.length){
                            break;
                        }
                    }
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
                    Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " milliseconds");
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
                    Log.d("TimerOutput", "Elapsed time: " + whiskeyTimer.elapsedTime() + " milliseconds");
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