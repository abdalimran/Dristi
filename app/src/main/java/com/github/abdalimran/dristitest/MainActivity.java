package com.github.abdalimran.dristitest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private SpeakText st;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView commandView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandView= (TextView) findViewById(R.id.commandView);
        st=new SpeakText(this);
        Log.i("Logs","onCreate");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                st.Speak("Hello Sir. I am Dristee. What can I do for you?");
                Log.i("Logs","Speak 1");
            }
        }, 400);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                st.Speak("I am Dristee!");
//                Log.i("Logs","Speak 2");
//            }
//        }, 4000);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                st.Speak("What can I do for you?");
//                Log.i("Logs","Speak 3");
//            }
//        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                promptSpeechInput();
            }
        }, 8000);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.lang_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).trim().toLowerCase().equals("write a message")) {
                        commandView.setText(result.get(0).trim().toLowerCase());
                        st.Speak("You have said to write a message.");
                    }
                    else if(result.get(0).trim().toLowerCase().equals("make a call")) {
                        commandView.setText(result.get(0).trim().toLowerCase());
                        st.Speak("You have said to make a call.");
                    }
                    else if(result.get(0).trim().toLowerCase().equals("what is the time and date")) {
                        commandView.setText(result.get(0).trim().toLowerCase());
                        st.Speak("You have asked what is the time and date.");
                    }
                    else if(result.get(0).trim().toLowerCase().equals("perform a google search")) {
                        commandView.setText(result.get(0).trim().toLowerCase());
                        st.Speak("You have said to perform a google search.");
                    }
                    else{
                        commandView.setText(result.get(0).trim().toLowerCase());
                        st.Speak("Sorry couldn't recognize command.");
                    }
                }
                break;
            }
        }
    }
}
