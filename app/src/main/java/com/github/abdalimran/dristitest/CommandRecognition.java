package com.github.abdalimran.dristitest;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class CommandRecognition implements RecognitionListener{

    private Context context;
    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;
    private String commandTxt;
    private static final String LOG_TAG = "Logs";

    public CommandRecognition(Context context) {
        this.context=context;
        initSpeechRecognizer();
        initRecognizerIntent();
    }

    public String getCommandTxt() {
        return commandTxt;
    }

    private void initRecognizerIntent(){
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        Log.i(LOG_TAG,"initRecognizerIntent");
    }


    private void initSpeechRecognizer(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(this);
        Log.i(LOG_TAG,"initSpeechRecognizer");
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(LOG_TAG,"onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG,"onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float v) {
        Log.i(LOG_TAG,"onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOG_TAG,"onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG,"onEndOfSpeech");
    }

    @Override
    public void onError(int i) {
        Log.i(LOG_TAG,"onError");
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        this.commandTxt=result.get(0).toString();
        Log.i(LOG_TAG,"onResults");
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(LOG_TAG,"onPartialResults");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG,"onEvent");
    }

    public void SpeakNow() {
        speechRecognizer.startListening(recognizerIntent);
        Log.i(LOG_TAG,"SpeakNow");
    }
}
