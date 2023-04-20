package com.example.work2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);
        newGameButton = findViewById(R.id.NewGamebutton);

        newGameButton.setOnClickListener(v -> {
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(radioButtonID);
            int index = radioGroup.indexOfChild(radioButton);

            if (index == 0) {
                Intent intent = new Intent(MainActivity.this, PvPActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, PvCActivity.class);
                startActivity(intent);
            }
        });
    }
}