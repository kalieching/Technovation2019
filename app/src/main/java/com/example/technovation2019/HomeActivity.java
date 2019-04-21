package com.example.technovation2019;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class HomeActivity extends AppCompatActivity {

    public static TextView result_text;
    private Button scan_btn;
    private Button speech_btn;
    TextToSpeech text_speech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        result_text = (TextView) findViewById(R.id.result);
        scan_btn = (Button) findViewById(R.id.btnScanCode);
        speech_btn= (Button) findViewById(R.id.btnTextSpeech);

        text_speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    text_speech.setLanguage(Locale.UK);
                }
            }
        });

        speech_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = result_text.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                text_speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Intent intentMessage = getIntent();
        String message = intentMessage.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.welcome);
        if (message != null) {
            textView.setText("Welcome, " + message + "!");
        } else {
            textView.setText("Welcome!");
        }

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ScanCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onPause(){
        if(text_speech !=null){
            text_speech.stop();
            text_speech.shutdown();
        }
        super.onPause();
    }
}