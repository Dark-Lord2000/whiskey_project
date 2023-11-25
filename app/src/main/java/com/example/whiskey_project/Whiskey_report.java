package com.example.whiskey_project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;

public class Whiskey_report extends Activity {
    String participantCode, sessionCode, groupCode, volSide, hand;
    // get parameters selected by user from setup dialog
    // get parameters from setup
    long trialTimes[];


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        Bundle b = getIntent().getExtras();
        participantCode = b.getString("participantCode");
        sessionCode = b.getString("sessionCode");
        groupCode = b.getString("groupCode");
        volSide= b.getString("volSide");
        hand = b.getString("hand");
        trialTimes = b.getLongArray("trialTimes");
        Log.d("Trial times", "Trial times: " + trialTimes[0] + ", " + trialTimes[1] + ", " + trialTimes[2] + ", " + trialTimes[3]);


        TextView resultID = findViewById(R.id.paramID);
        resultID.setText(String.format(participantCode + "|" + sessionCode + "|" + groupCode + "|" + volSide + "|" + hand));
        TextView result1View = findViewById(R.id.paramResult1);
        result1View.setText((String.valueOf(trialTimes[0])) + " milliseconds");


        TextView result2View = findViewById(R.id.paramResult2);
        result2View.setText((String.valueOf(trialTimes[1])) + " milliseconds");
        TextView result3View = findViewById(R.id.paramResult3);
        result3View.setText((String.valueOf(trialTimes[2])) + " milliseconds");
        TextView result4View = findViewById(R.id.paramResult4);
        result4View.setText((String.valueOf(trialTimes[3])) + " milliseconds");

        String content = buildtxt(participantCode, sessionCode, groupCode, volSide, hand, trialTimes);// need to build
        String filename = String.format("%s-%s-%s", participantCode, sessionCode, groupCode);
        // make a working directory (if necessary) to store data files
        try {
            ContentValues values = new ContentValues();

            values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);       //file name
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");        //file extension, will automatically add to file

            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/WhiskeyProject/");     //end "/" is not mandatory


            Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!

            OutputStream outputStream = getContentResolver().openOutputStream(uri);

            outputStream.write(content.getBytes());

            outputStream.close();


        } catch (
                IOException e) {

        }
    }

    // called when the "Setup" button is tapped
    public void clickSetup(View view) {
        Intent i = new Intent(getApplicationContext(), Whiskey_setup.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public String buildtxt(String participantCode, String sessionCode, String groupCode, String volSide, String hand, long[] trialTimes) {


        String txt = String.format("PartcipantCode: %s Group Code: %s session: code %s hand: %s \nTrial 1 %s \nTrial 2 %s \nTrial 3 %s \nTrial 4 %s \n ", participantCode, groupCode, sessionCode, volSide, hand, trialTimes[0], trialTimes[1], trialTimes[2], trialTimes[3]);


        return txt;


    }
}
