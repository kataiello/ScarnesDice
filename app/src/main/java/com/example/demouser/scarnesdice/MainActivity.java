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

        random = new Random();
        whosTurn = Players.PLAYER;


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
        ((TextView) findViewById(R.id.rollText)).setText(Integer.toString(roll));
        //TODO implement with images instead of text
        



        //if the roll is 1, automatically change players, don't add the score
        if(roll == 1)
        {
            changePlayers();
            String lostRound = String.format("You rolled a 1! Your score was \n reset and the players switched. \n It is now %s's turn.", whosTurn.toString());
            ((TextView) findViewById(R.id.turnScoreText)).setText(lostRound);
        }
        //else, add it to the score
        else
        {
            currentTurn += roll;
            //show it in turnScoreText
            ((TextView) findViewById(R.id.turnScoreText)).setText(Integer.toString(currentTurn));
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

    /**
     * Method to change between the two players
     */
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
            //computerTurn();
        }
    }

    /**
     * Method to add to the player score
     * @param turnTotal total score from the turn
     */
    private void playerScores(int turnTotal)
    {
        //add the turn score to the total
        playerTotal += turnTotal;
        //update player1Score
        ((TextView) findViewById(R.id.player1Score)).setText(Integer.toString(playerTotal));
    }

    /**
     * Method to add to the computer score
     * @param turnTotal total score from the turn
     */
    private void computerScores(int turnTotal)
    {
        //add the turn score to the total
        computerTotal += turnTotal;
        //update player2Score
        ((TextView) findViewById(R.id.player2Score)).setText(Integer.toString(computerTotal));
    }

    /**
     * Reset all of the scores to 0
     */
    private void resetScores()
    {
        currentTurn = 0;
        playerTotal = 0;
        computerTotal = 0;
    }

    //TODO implement
    private void computerTurn()
    {
        //TODO roll first
        //TODO timer -- wait
        while(whosTurn.equals(Players.COMPUTER))
        {
            //TODO each time randomly choose to roll or hold
            //TODO timer -- wait
        }
    }

}
