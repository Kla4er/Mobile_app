package com.example.work2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PvPActivity extends AppCompatActivity {
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;
    private final Button[][] buttons = new Button[3][3];
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private int scorePlayer1;
    private int scorePlayer2;
    private int currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp);

        buttons[0][0] = findViewById(R.id.button_00);
        buttons[0][1] = findViewById(R.id.button_01);
        buttons[0][2] = findViewById(R.id.button_02);
        buttons[1][0] = findViewById(R.id.button_10);
        buttons[1][1] = findViewById(R.id.button_11);
        buttons[1][2] = findViewById(R.id.button_12);
        buttons[2][0] = findViewById(R.id.button_20);
        buttons[2][1] = findViewById(R.id.button_21);
        buttons[2][2] = findViewById(R.id.button_22);

        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        Button resetButton = findViewById(R.id.ResetButton);

        resetButton.setOnClickListener(v -> resetGame(true));

        Button mainButton = findViewById(R.id.ToMain);

        mainButton.setOnClickListener(v -> goToMain());
        currentPlayer = PLAYER_X;
    }

    private void goToMain() {
        Intent intent = new Intent(PvPActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        button.setEnabled(false);

        if (currentPlayer == PLAYER_X) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        if (checkForWin()) {
            // Increment the current player's score
            if (currentPlayer == PLAYER_X) {
                scorePlayer1++;
                textViewPlayer1.setText("Player 1: " + scorePlayer1);
                Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
            } else {
                scorePlayer2++;
                textViewPlayer2.setText("Player 2: " + scorePlayer2);
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
            }
            resetGame(false);
            return;
        }

        if (checkForDraw()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
            resetGame(false);
            return;
        }

        if (currentPlayer == PLAYER_X) currentPlayer = PLAYER_O;
        else currentPlayer = PLAYER_X;
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
                if (field[i][j].equals("")) allButtonsClicked = false;
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
        currentPlayer = PLAYER_X;

        if (resetScore) {
            textViewPlayer1.setText("Player 1: 0" );
            textViewPlayer2.setText("Player 2: 0");
        }

    }
}