package com.example.demouser.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int currentTurn;
    public int playerTotal;
    public int computerTotal;
    public Players whosTurn;
    public Random random;
    public enum Players {
        PLAYER,
        COMPUTER,
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.rollButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                roll(view);
            }
        });

        ((Button)findViewById(R.id.holdButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                hold(view);
            }
        });

        ((Button)findViewById(R.id.resetButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                reset(view);
            }
        });
    }

    public void roll(View view)
    {
        //generate a random number between 1 and 6 inclusive
        int roll = rollDice();
        //TODO: do something to show it in rollText
        //if the roll is 1, automatically change players, don't add the score
        if(roll == 1)
        {
            changePlayers();
        }
        //else, add it to the score
        else
        {
            currentTurn += roll;
            //TODO do something to show it in turnScoreText
        }

    }

    public void hold(View view)
    {
        //add to appropriate player's score
        if(whosTurn.equals(Players.PLAYER))
        {
            playerScores(currentTurn);
            //make it computer's turn
            changePlayers();
        }
        else
        {
            computerScores(currentTurn);
            //make it player's turn
            changePlayers();
        }
    }

    public void reset(View view)
    {
        resetScores();
    }

    private int rollDice()
    {
        return random.nextInt(6) + 1;
    }

    private void changePlayers()
    {
        currentTurn = 0;
        if(whosTurn.equals(Players.COMPUTER))
        {
            whosTurn = Players.PLAYER;
        }
        else
        {
            whosTurn = Players.COMPUTER;
        }
    }

    private void playerScores(int turnTotal)
    {
        playerTotal += turnTotal;
        //TODO update player1Score
    }

    private void computerScores(int turnTotal)
    {
        computerTotal += turnTotal;
        //TODO update player2Score
    }

    private void resetScores()
    {
        currentTurn = 0;
        playerTotal = 0;
        computerTotal = 0;
    }


}
