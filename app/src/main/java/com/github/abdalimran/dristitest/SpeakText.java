package com.github.abdalimran.dristitest;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class SpeakText implements TextToSpeech.OnInitListener{

    private TextToSpeech engine;

    public SpeakText(Context context) {
        engine = new TextToSpeech(context, this);
        Log.i("Logs","Constructor");
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            engine.setLanguage(Locale.US);
        }
        Log.i("Logs","onInit");
    }

    public void SpeakDefault(String text) {
        engine.setSpeechRate((float) 1.0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        Log.i("Logs","Speak");
    }

    public void Speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> myHash = new HashMap<>();
        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");

        String[] splitspeech = text.split("\\.");

        for (int i=0; i < splitspeech.length; i++)
        {
            if (i==0){
                engine.speak(splitspeech[i].toString().trim(),TextToSpeech.QUEUE_FLUSH, myHash);
            }
            else{
                engine.speak(splitspeech[i].toString().trim(), TextToSpeech.QUEUE_ADD,myHash);
            }
            PlaySilence();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

        String[] splitspeech = text.split("\\.");

        for (int i=0; i < splitspeech.length; i++)
        {
            if (i==0){
                engine.speak(splitspeech[i].toString().trim(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            }
            else{
                engine.speak(splitspeech[i].toString().trim(), TextToSpeech.QUEUE_ADD, null, utteranceId);
            }
            PlaySilence();
        }
    }

    public void PlaySilence(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            engine.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, null);
        }
        else
            engine.playSilence(750, TextToSpeech.QUEUE_ADD, null);
    }
}
