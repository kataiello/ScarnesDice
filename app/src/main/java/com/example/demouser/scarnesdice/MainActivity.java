package com.example.demouser.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        //show it in rollText
        ((TextView) findViewById(R.id.rollText)).setText(roll);
        //if the roll is 1, automatically change players, don't add the score
        if(roll == 1)
        {
            changePlayers();
            ((TextView) findViewById(R.id.rollText)).setText("0");
        }
        //else, add it to the score
        else
        {
            currentTurn += roll;
            //show it in turnScoreText
            ((TextView) findViewById(R.id.turnScoreText)).setText(currentTurn);
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
        ((TextView) findViewById(R.id.player1Score)).setText("0");
        ((TextView) findViewById(R.id.player2Score)).setText("0");
        ((TextView) findViewById(R.id.turnScoreText)).setText("0");
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
        //add the turn score to the total
        playerTotal += turnTotal;
        //update player1Score
        ((TextView) findViewById(R.id.player1Score)).setText(playerTotal);
    }

    private void computerScores(int turnTotal)
    {
        //add the turn score to the total
        computerTotal += turnTotal;
        //update player2Score
        ((TextView) findViewById(R.id.player2Score)).setText(computerTotal);
    }

    private void resetScores()
    {
        currentTurn = 0;
        playerTotal = 0;
        computerTotal = 0;
    }


}
