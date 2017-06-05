package com.example.justyna.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class GameActivity extends AppCompatActivity {
    static int [] gameboard = new int [9];
    static int capacity_of_pressed_buttons=0;
    static String remis_turn="";
    static int winCross=0, winNought=0, round=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Arrays.fill(gameboard, 0);
        loadContent();
    }

    private void loadContent()
    {
        //Intent i = getIntent();
        //String user_name2=i.getExtras().getString("username2");

        TextView result_firstUsername = (TextView) findViewById(R.id.resultCross_textView);
        TextView result_secondUsername = (TextView) findViewById(R.id.resultNought_textView);
        final TextView round_TextView = (TextView) findViewById(R.id.capacityOfRounds_textView);

        final TextView points_firstUser = (TextView) findViewById(R.id.pointsCross_textView);
        final TextView points_secondUser = (TextView) findViewById(R.id.pointsNought_textView);

        //default points
        points_firstUser.setText(String.valueOf(winCross));
        points_secondUser.setText(String.valueOf(winNought));
        round_TextView.setText(String.valueOf(round));
        //

        SharedPreferences game_preferences = getSharedPreferences("usernames", 0);
        result_firstUsername.setText(game_preferences.getString("userText1", "First User")+":");
        result_secondUsername.setText(game_preferences.getString("userText2", "Second User")+":");


        final ImageButton[] tab_buttons = new ImageButton[9];
        tab_buttons[0] = (ImageButton) findViewById(R.id.imageButton1);
        tab_buttons[1] = (ImageButton) findViewById(R.id.imageButton2);
        tab_buttons[2] = (ImageButton) findViewById(R.id.imageButton3);
        tab_buttons[3] = (ImageButton) findViewById(R.id.imageButton4);
        tab_buttons[4] = (ImageButton) findViewById(R.id.imageButton5);
        tab_buttons[5] = (ImageButton) findViewById(R.id.imageButton6);
        tab_buttons[6] = (ImageButton) findViewById(R.id.imageButton7);
        tab_buttons[7] = (ImageButton) findViewById(R.id.imageButton8);
        tab_buttons[8] = (ImageButton) findViewById(R.id.imageButton9);



        {
            TurnAtGame turnAtGame = new TurnAtGame();
            int tmp = turnAtGame.getTurn();
            int first = 0;
            if (tmp % 2 != 0) first = 1;
            if (tmp % 2 == 0) first = 2;
            ImageView whoseTurn = (ImageView) findViewById(R.id.whoseTurn_imageView);
            if (first == 1) whoseTurn.setImageResource(R.drawable.tic1);
            if (first == 2) whoseTurn.setImageResource(R.drawable.tic2);
        }

        Button endButton = (Button) findViewById(R.id.end_button);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                capacity_of_pressed_buttons=0;
                TurnAtGame turnAtGame = new TurnAtGame();
                turnAtGame.setTurn(1);

                new AlertDialog.Builder(GameActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Finishing the game")
                        .setMessage(R.string.finish_game_alert)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                winCross = winNought = 0;
                                round = 1;
                                finish();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .create()
                        .show();

            }
                //Intent finish = new Intent(GameActivity.this, MainActivity.class);
                //finish();
                //winCross=winNought=0; round=1;
                //startActivity(finish);
        });

         for(int j=0; j<tab_buttons.length; j++) {
            final int finalJ = j;

             tab_buttons[j].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int which_turn = set_a_user_choice(finalJ);
                    if (which_turn==0){
                        end_of_game(R.string.end_of_game_remis);
                        if(remis_turn=="cross") tab_buttons[finalJ].setImageResource(R.drawable.tic1);
                        if(remis_turn=="nought") tab_buttons[finalJ].setImageResource(R.drawable.tic2);
                        round++;
                       // round_TextView.setText(String.valueOf(round));

                    }
                    if (which_turn == 1) tab_buttons[finalJ].setImageResource(R.drawable.tic1);
                    if (which_turn == 2) tab_buttons[finalJ].setImageResource(R.drawable.tic2);
                    if (which_turn == 3) {
                        tab_buttons[finalJ].setImageResource(R.drawable.tic1);
                        winCross++;
                        points_firstUser.setText(String.valueOf(winCross));
                        round++;
                       // round_TextView.setText(String.valueOf(round));
                        for (int i = 0; i < 9; i++) {
                            tab_buttons[i].setEnabled(false);
                            capacity_of_pressed_buttons=0;
                        }
                    }

                    if (which_turn == 4) {
                        tab_buttons[finalJ].setImageResource(R.drawable.tic2);
                        winNought++;
                        points_secondUser.setText(String.valueOf(winNought));
                        round++;
                       // round_TextView.setText(String.valueOf(round));
                        for (int i = 0; i < 9; i++) {
                            tab_buttons[i].setEnabled(false);
                            capacity_of_pressed_buttons=0;
                        }
                    }
                    tab_buttons[finalJ].setEnabled(false);


                }


                });

            }
    }

    private int set_a_user_choice(int position){

        TurnAtGame turnAtGame = new TurnAtGame();
        int turn = turnAtGame.getTurn();
        int whose = 0;
        if(turn%2!=0) whose=1;
        if(turn%2==0) whose=2;
        int who = 0;
        if(whose==1) who= R.string.end_of_game_cross;
        if(whose==2) who=R.string.end_of_game_nought;
        turnAtGame.changeTurn();

        gameboard[position]=whose;
        ImageView whoseTurn = (ImageView) findViewById(R.id.whoseTurn_imageView);
        if(whose==1) whoseTurn.setImageResource(R.drawable.tic2);
        if(whose==2) whoseTurn.setImageResource(R.drawable.tic1);

        if(checkIfIsWinning(whose)) {
            /*Toast.makeText(GameActivity.this, "Koniec gry!\n"+who, Toast.LENGTH_LONG).show();
            Button restartButton = (Button) findViewById(R.id.restart_button);
            restartButton.setVisibility(View.VISIBLE);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent refresh = new Intent(GameActivity.this, GameActivity.class);
                    finish();
                    startActivity(refresh);
                }
            }); */

            end_of_game(who);
            return whose+2;  // if whose equals 3 or 4 somebody is a winner
        }

        if(!checkIfIsWinning(whose)){
            capacity_of_pressed_buttons++;
            if(capacity_of_pressed_buttons==9) {
                capacity_of_pressed_buttons=0;
                if(whose==1) remis_turn="cross";
                if(whose==2) remis_turn="nought";
                //round++;
                return 0;
            }
        }

        return whose;
    }

        private void end_of_game(int who){
            Toast.makeText(GameActivity.this, who, Toast.LENGTH_LONG).show();
            Arrays.fill(gameboard, 0);
            Button restartButton = (Button) findViewById(R.id.restart_button);
            restartButton.setVisibility(View.VISIBLE);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent refresh = new Intent(GameActivity.this, GameActivity.class);
                    finish();
                    startActivity(refresh);
                }
            });


        }

        private boolean checkIfIsWinning(int mark){
            if(gameboard[0]==mark&&gameboard[1]==mark&&gameboard[2]==mark ||
               gameboard[3]==mark&&gameboard[4]==mark&&gameboard[5]==mark ||
               gameboard[6]==mark&&gameboard[7]==mark&&gameboard[8]==mark ||
               gameboard[0]==mark&&gameboard[3]==mark&&gameboard[6]==mark ||
               gameboard[1]==mark&&gameboard[4]==mark&&gameboard[7]==mark ||
               gameboard[2]==mark&&gameboard[5]==mark&&gameboard[8]==mark ||
               gameboard[0]==mark&&gameboard[4]==mark&&gameboard[8]==mark ||
               gameboard[2]==mark&&gameboard[4]==mark&&gameboard[6]==mark) return true;

            else return false;
        }

    @Override
    public void onBackPressed() {
        capacity_of_pressed_buttons=0;
        TurnAtGame turnAtGame = new TurnAtGame();
        turnAtGame.setTurn(1);
        winCross=0;
        winNought=0;
        round=1;
        //super.onBackPressed();  tego być nie może, bo inaczej alert się nie pojawia
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
    .setTitle("Closing Activity")
    .setMessage(R.string.alert_back_activity)
    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    })
            .setNegativeButton("NO", null)
    .create()
    .show();
}


}
