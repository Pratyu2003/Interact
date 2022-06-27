package com.pratyush.interact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard extends AppCompatActivity {

    EditText codeBox;
    Button joinButton, shareButton;
    private String RoomCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        codeBox = findViewById(R.id.codeBox);
        joinButton = findViewById(R.id.joinButton);
        shareButton = findViewById(R.id.shareButton);


        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setFeatureFlag("invite.enable", false)
                            .setFeatureFlag("chat.enabled", true)
                            .setFeatureFlag("raise-hand.enabled", true)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(codeBox.getText().toString())
                        .setFeatureFlag("invite.enable", false)
                        .setFeatureFlag("chat.enabled", true)
                        .setFeatureFlag("raise-hand.enabled", true)
                        .build();

                JitsiMeetActivity.launch(Dashboard.this, options);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomCode = codeBox.getText().toString();

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Join with this Room Code on Interact: "+RoomCode);
                    startActivity(intent);
                }
        });
    }

}