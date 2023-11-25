package com.example.whiskey_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//base code for setup page implementation taken from Demo_TiltBall
public class Whiskey_setup extends Activity {
    String[] PARTICIPANT_CODE = {"P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10"};
    String[] SESSION_CODE = {"S01", "S02", "S03", "S04"};
    String[] GROUP_CODE = {"G01", "G02"};
    String[] VOL_SIDE= {"N/A", "Vol. Left", "Vol. Right"};
    Spinner spinParticipantCode;
    Spinner spinSessionCode, spinGroupCode, spinVolSide, spinHand;
    SharedPreferences sp;
    final static String[] HAND = {"Left", "Right"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        spinParticipantCode = (Spinner) findViewById(R.id.spinParticipantCode);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.spinnerstyle, PARTICIPANT_CODE);
        spinParticipantCode.setAdapter(adapter);

        spinGroupCode = (Spinner) findViewById(R.id.spinGroupCode);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<>(this, R.layout.spinnerstyle, GROUP_CODE);
        spinGroupCode.setAdapter(adapter2);

        spinSessionCode = (Spinner) findViewById(R.id.spinSessionCode);
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<>(this, R.layout.spinnerstyle, SESSION_CODE);
        spinSessionCode.setAdapter(adapter1);

        spinVolSide = (Spinner) findViewById(R.id.spinVolSide);
        ArrayAdapter<CharSequence> adapter4 = new ArrayAdapter<>(this, R.layout.spinnerstyle, VOL_SIDE);
        spinVolSide.setAdapter(adapter4);

        spinHand = (Spinner) findViewById(R.id.spinHand);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<>(this, R.layout.spinnerstyle, HAND);
        spinHand.setAdapter(adapter3);
        spinHand.setSelection(1);

    }

    // called when the "OK" button is tapped
    public void clickOK(View view) {

        String participantCode = PARTICIPANT_CODE[spinParticipantCode.getSelectedItemPosition()];
        String sessionCode = SESSION_CODE[spinSessionCode.getSelectedItemPosition()];
        String groupCode = GROUP_CODE[spinGroupCode.getSelectedItemPosition()];
        String volSide = VOL_SIDE[spinVolSide.getSelectedItemPosition()];
        String hand = HAND[spinHand.getSelectedItemPosition()];

        Bundle b = new Bundle();
        b.putString("participantCode", participantCode);
        b.putString("sessionCode", sessionCode);
        b.putString("groupCode", groupCode);
        b.putString("volSide", volSide);
        b.putString("hand", hand);

        Intent i = new Intent(Whiskey_setup.this, Whiskey_main.class);
        i.putExtras(b);
        startActivity(i);

    }

    // Called when the "Exit" button is pressed.
    public void clickExit(View view) {
        super.onDestroy(); // cleanup
        this.finish(); // terminate
    }

    /*@Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }*/
}
