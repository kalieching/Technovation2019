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
        speech_btn = (Button) findViewById(R.id.btnTextSpeech);

        text_speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    int result = text_speech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        System.out.println("Language not supported");
                    } else {
                        speech_btn.setEnabled(true);
                    }
                }
            }
        });

        /** TEXT TO SPEECH */
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

        /** Takes name inputted on MAIN page and adds it to welcome message on the HOME page */
        TextView textView = findViewById(R.id.welcome);
        if (message != null) {
            textView.setText("Welcome, " + message + "!");
        } else {
            textView.setText("Welcome!");
        }

        /** Opens the CAMERA to scan a QR code */
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ScanCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called when the user taps the CONTACT US button
     */
    public void switchToMedWastePage(View view) {
        Intent intent = new Intent(this, MedWasteActivity.class);
        startActivity(intent);
    }

    public void onPause() {
        if (text_speech != null) {
            text_speech.stop();
            text_speech.shutdown();
        }
        super.onPause();
    }

    /**
     * Called when the user taps the CALENDAR button
     */
    public void switchToCalendarPage(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}