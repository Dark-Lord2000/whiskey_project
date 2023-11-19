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

public class Whiskey_setup extends Activity implements TextWatcher {
    String[] PARTICIPANT_CODE = {"P99", "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10"};
    String[] SESSION_CODE = {"S99", "S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08"};
    String[] GROUP_CODE= {"G99", "G01", "G02"};
    Spinner spinParticipantCode;
    Spinner spinSessionCode, spinGroupCode, spinHand;
    SharedPreferences sp;
    final static String[] HAND = {"left","Right"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.setup);

        GROUP_CODE[0] = sp.getString("GROUP_CODE", GROUP_CODE[0]);


        spinParticipantCode = (Spinner) findViewById(R.id.spinParticipantCode);
        spinSessionCode = (Spinner) findViewById(R.id.spinSessionCode);
        spinGroupCode = (Spinner) findViewById(R.id.spinGroupCode);
        spinHand = (Spinner) findViewById(R.id.spinHand);

    }

    public void clickOK(View view){
        String participantCode = PARTICIPANT_CODE[spinParticipantCode.getSelectedItemPosition()];
        String sessionCode = SESSION_CODE[spinSessionCode.getSelectedItemPosition()];
        String groupCode = GROUP_CODE[spinGroupCode.getSelectedItemPosition()];
        String hand = HAND[spinHand.getSelectedItemPosition()];

        Bundle b = new Bundle();
        b.putString("participantCode", participantCode);
        b.putString("sessionCode", sessionCode);
        b.putString("groupCode", groupCode);
        b.putString("hand", hand);

        Intent i = new Intent(getApplicationContext(), Whiskey_main.class);
        i.putExtras(b);
        startActivity(i);

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
