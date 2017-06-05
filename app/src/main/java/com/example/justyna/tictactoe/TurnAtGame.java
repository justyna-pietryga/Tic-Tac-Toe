package com.example.justyna.tictactoe;

/**
 * Created by Justyna on 2016-08-18.
 */
public class TurnAtGame {

    private static int turn=1;

    public int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        TurnAtGame.turn = turn;
    }

    public void changeTurn()
    {
        turn++;
    }


}
