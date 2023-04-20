package com.example.work2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PvCActivity extends AppCompatActivity {
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;
    private final Button[][] buttons = new Button[3][3];
    private String playerName = "Player";
    private TextView textViewPlayer;
    private TextView textViewCPU;
    private int scorePlayer;
    private int scoreCPU;
    private int userSide;
    boolean nameEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvc);

        buttons[0][0] = findViewById(R.id.cbutton_00);
        buttons[0][1] = findViewById(R.id.cbutton_01);
        buttons[0][2] = findViewById(R.id.cbutton_02);
        buttons[1][0] = findViewById(R.id.cbutton_10);
        buttons[1][1] = findViewById(R.id.cbutton_11);
        buttons[1][2] = findViewById(R.id.cbutton_12);
        buttons[2][0] = findViewById(R.id.cbutton_20);
        buttons[2][1] = findViewById(R.id.cbutton_21);
        buttons[2][2] = findViewById(R.id.cbutton_22);

        textViewPlayer = findViewById(R.id.text_view_player);
        textViewCPU = findViewById(R.id.text_view_cpu);

        Button resetButton = findViewById(R.id.cResetButton);

        resetButton.setOnClickListener(v -> resetGame(true));

        Button mainButton = findViewById(R.id.cToMain);

        mainButton.setOnClickListener(v -> goToMain());
        userSide = PLAYER_X;
        AskUsersName();
    }

    private void AskUsersName() {
        final EditText input = new EditText(PvCActivity.this);
        input.setHint("Enter your name");

        new AlertDialog.Builder(PvCActivity.this)
                .setTitle("Player Name")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    playerName = input.getText().toString();
                    textViewPlayer.setText(playerName + ": " + scorePlayer);
                }).show();
    }


    private void DetermineStep() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Who will start the game?");

        builder.setPositiveButton("Player", (dialog, which) -> userSide = PLAYER_X);
        builder.setNegativeButton("CPU", (dialog, which) -> {
            userSide = PLAYER_O;
            makeCPUturn("X");
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void OnClickStep(View view) {
        DetermineStep();
    }

    private void goToMain() {
        Intent intent = new Intent(PvCActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        button.setEnabled(false);

        if (userSide == PLAYER_X) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        if (checkForWin()) {
            scorePlayer++;
            textViewPlayer.setText(playerName + ": " + scorePlayer);
            Toast.makeText(this, "Player wins!", Toast.LENGTH_SHORT).show();
            resetGame(false);
            return;
        }

        if (checkForDraw()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
            resetGame(false);
            return;
        }

        if (userSide == PLAYER_X) makeCPUturn("O");
        else makeCPUturn("X");
    }

    private void makeCPUturn(String curStep) {
        ArrayList<Button> availableButtons = new ArrayList<>();
        for (Button[] button : buttons) {
            for (int j = 0; j < buttons.length; j++) {
                if (button[j].isEnabled()) {
                    availableButtons.add(button[j]);
                }
            }
        }

        int randomIndex = (int) (Math.random() * availableButtons.size());
        Button randomButton = availableButtons.get(randomIndex);
        randomButton.setText(curStep);
        randomButton.setEnabled(false);

        if (checkForWin()) {
            scoreCPU++;
            textViewCPU.setText("CPU: " + scoreCPU);
            Toast.makeText(this, "CPU wins!", Toast.LENGTH_SHORT).show();
            resetGame(false);
        }

        if (checkForDraw()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
            resetGame(false);
        }

    }

    private Boolean checkForDraw() {
        boolean allButtonsClicked = true;

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                if (field[i][j].equals("")) {
                    allButtonsClicked = false;
                    break;
                }
            }
        }
        return allButtonsClicked;
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Checking rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // Checking columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // Checking diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void resetGame(boolean resetScore) {

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }

        if (resetScore) {
            textViewPlayer.setText(playerName + ": 0" );
            textViewCPU.setText("CPU: 0");
        }
    }
}