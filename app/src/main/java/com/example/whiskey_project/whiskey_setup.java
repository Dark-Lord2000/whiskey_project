package com.example.whiskey_project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Spinner;

public class whiskey_setup extends Activity implements TextWatcher {
    String[] participantCode = {"P99", "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10"};
    String[] sessionCode = {"S99", "S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08"};
    String[] groupCode = {"G99", "G01", "G02"};
    private Spinner spinParticipantCode;
    private Spinner spinSessionCode, spinGroupCode, spinHand;
    SharedPreferences sp;
    String[] hand = {"left","Right"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.setup);

        groupCode[0] = sp.getString("groupCode", groupCode[0]);

        spinParticipantCode = (Spinner) findViewById(R.id.spinParticipantCode);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
