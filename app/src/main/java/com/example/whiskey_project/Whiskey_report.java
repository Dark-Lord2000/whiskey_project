package com.example.whiskey_project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;

public class Whiskey_report extends Activity {
    String participantCode, sessionCode, groupCode, hand;
    // get parameters selected by user from setup dialog
    // get parameters from setup
int trial;
int speed;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b  = getIntent().getExtras();
        participantCode = b.getString("participantCode");
        sessionCode= b.getString("sessionCode");
        groupCode = b.getString("groupCode");
        hand = b.getString("hand");


        setContentView(R.layout.report);


        TextView resultID = findViewById(R.id.paramID);
        resultID.setText(String.format(participantCode+"" + sessionCode +""+groupCode));
        TextView result1View = findViewById(R.id.paramResult1);
        result1View.setText(String.valueOf(speed));


        TextView result2View = findViewById(R.id.paramResult2);
        result2View.setText(String.valueOf(speed));
        TextView result3View = findViewById(R.id.paramResult3);
        result3View.setText(String.valueOf(speed));
        TextView result4View = findViewById(R.id.paramResult4);
        result4View.setText(String.valueOf(speed));

        String content = "poop";// need to build
        String filename= String.format("%s-%s-%s-%s",participantCode,sessionCode,groupCode );
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
    public void clickSetup(View view)
    {
        Intent i = new Intent(getApplicationContext(), Whiskey_setup.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


}
