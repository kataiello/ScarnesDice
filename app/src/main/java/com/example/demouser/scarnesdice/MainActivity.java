package com.example.demouser.scarnesdice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int currentTurn = 0;
    public int playerTotal = 0;
    public int computerTotal = 0;
    public int round = 0;
    public TextView computerScoreText;
    public TextView playerScoreText;
    public TextView turnScoreText;
    public TextView rollText;
    public ImageView dieView;
    public final Handler timerHandler = new Handler();
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

        dieView = (ImageView) findViewById(R.id.dieView);
        dieView.setContentDescription("one face to start");
        playerScoreText = (TextView) findViewById(R.id.player1Score);
        computerScoreText = (TextView) findViewById(R.id.player2Score);
        turnScoreText = (TextView) findViewById(R.id.turnScoreText);
        rollText = (TextView) findViewById(R.id.rollText);



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


        resetNoView();
    }

    public void roll(View view)
    {
        rollNoView();
    }

    public void rollNoView()
    {
        //generate a random number between 1 and 6 inclusive
        int roll = rollDice();
        //show it in rollText
        //rollText.setText(Integer.toString(roll));
        //implemented with images instead of text
        switch(roll)
        {
            case 1: dieView.setImageResource(R.drawable.dice1);
                    dieView.setContentDescription("one face");
                break;
            case 2: dieView.setImageResource(R.drawable.dice2);
                dieView.setContentDescription("two face");
                break;
            case 3: dieView.setImageResource(R.drawable.dice3);
                    dieView.setContentDescription("three face");
                break;
            case 4: dieView.setImageResource(R.drawable.dice4);
                dieView.setContentDescription("four face");
                break;
            case 5: dieView.setImageResource(R.drawable.dice5);
                dieView.setContentDescription("five face");
                break;
            case 6: dieView.setImageResource(R.drawable.dice6);
                    dieView.setContentDescription("six face");
                break;
        }


        //if the roll is 1, automatically change players, don't add the score
        if(roll == 1)
        {
            changePlayers();
            String lostRound = String.format(getString(R.string.change_players), whosTurn.toString());
            turnScoreText.setText(lostRound);
        }
        //else, add it to the score
        else
        {
            currentTurn += roll;
            //show it in turnScoreText
            turnScoreText.setText(Integer.toString(currentTurn));
        }
    }

    public void hold(View view)
    {
        holdNoView();
    }

    public void holdNoView()
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
        resetNoView();
    }

    public void resetNoView()
    {
        resetScores();
        playerScoreText.setText("0");
        computerScoreText.setText("0");
        turnScoreText.setText("0");
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
        checkWin();
        
        currentTurn = 0;
        if(whosTurn.equals(Players.COMPUTER))
        {
            whosTurn = Players.PLAYER;
        }
        else
        {
            whosTurn = Players.COMPUTER;
            round = 0;
            computerTurnIn500();
        }

        String turnText = String.format(getString(R.string.turn_text), whosTurn.toString());
        turnScoreText.setText(turnText);

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
        playerScoreText.setText(Integer.toString(playerTotal));
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
        computerScoreText.setText(Integer.toString(computerTotal));
    }

    /**
     * Reset all of the scores to 0
     */
    private void resetScores()
    {
        currentTurn = 0;
        playerTotal = 0;
        computerTotal = 0;
        round = 0;
    }


    private void computerTurn()
    {
        rollNoView();
        if(shouldHold(round))
        {
            holdNoView();
        }
        else
        {
            rollNoView();
        }
    }

    private boolean shouldHold(int roundNum)
    {
        //the expected sum for this many turns
        double expect = roundNum * 3.5;

        //if we have averaged one more than the expectation each round, hold
        if(roundNum > 3 && currentTurn > expect + roundNum)
        {
            return true;
        }
        else
        {
            //hold if we have had more than 6 rounds
            if(roundNum > 6)
            {
                return true;
            }
            else
            {
                //otherwise, hold only if we have more than 15 points (sizeable)
                return (currentTurn > 15);
            }
        }
    }

    private void computerTurnIn500()
    {
        timerHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                round++;
                computerTurn();
                if(whosTurn == Players.COMPUTER)
                {
                    computerTurnIn500();
                }
            }
        }, 500);
    }

    private void checkWin()
    {
        if(playerTotal >= 50)
        {
            playerWins();
        }
        else if(computerTotal >= 50)
        {
            computerWins();
        }
    }

    private void computerWins()
    {
        startActivity(new Intent(this, LoseActivity.class));
        resetNoView();
    }

    private void playerWins()
    {
        Intent intent = new Intent(this, WinActivity.class);
        startActivity(intent);
        resetNoView();
    }

}
