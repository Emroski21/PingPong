package edu.truman.emroskicrane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * A game panel with paddles and a ball.
 * @author Arber Emroski and Brandon Crane
 * @version December 6, 2015
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
   
   //Attribute
   private Ball ball; 
   private Paddle leftPaddle; 
   private Paddle rightPaddle; 
   private boolean gamePaused;
   private boolean gameStarted;
   private boolean gameOver;
   private int resetGameCheck;
   private int difficulty;
   
   //Constants
   private static final int MAX_SCORE = 7;
   
   /**
    * Constructs a GamePanel object
    */
   public GamePanel()
   {
         final int BALL_STARTING_X = 600 / 2;
         final int BALL_STARTING_Y = 400 / 2;
         ball = new Ball(BALL_STARTING_X, BALL_STARTING_Y);

         final int PADDLE_L_STARTING_X = 15;
         final int PADDLE_L_STARTING_Y = 160; 
         final int PADDLE_L_STARTING_ID = 1;
         leftPaddle = new Paddle(PADDLE_L_STARTING_X, PADDLE_L_STARTING_Y,
               PADDLE_L_STARTING_ID,
               KeyEvent.VK_W, KeyEvent.VK_S, ball, this);

         final int PADDLE_R_STARTING_X = 575;
         final int PADDLE_R_STARTING_Y = 160; 
         final int PADDLE_R_STARTING_ID = 2;
         rightPaddle = new Paddle(PADDLE_R_STARTING_X, PADDLE_R_STARTING_Y,
               PADDLE_R_STARTING_ID,
               KeyEvent.VK_UP, KeyEvent.VK_DOWN, ball, this);

      addKeyListener(this);
      setFocusable(true);
      Thread gameThread = new Thread(this);
      gameThread.start();
      resetGameCheck = 1;
      difficulty = 0;
      gamePaused = false;
      gameOver = false;
   }

   /**
    * Paints the panel
    * @param g the Graphics object
    */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      final int BACKGROUND_WIDTH = 600;
      final int BACKGROUND_HEIGHT = 400;
      final int PANELX = 0;
      final int PANELY = 0;
      g2.fillRect(PANELX, PANELY, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
      

      if (gameStarted  && gameOver)
      {
         final int WIN_STRING_X = 80;
         final int WIN_STRING_Y = 150;
         
         g2.setColor(Color.WHITE);
         g2.setFont(new Font("Courier", Font.BOLD, 50));

         if(ball.getP1Score() == MAX_SCORE)
         {
            g2.drawString("Player One Wins", WIN_STRING_X, WIN_STRING_Y ); 
            ball.pause();
         }
         else if(ball.getP2Score() == MAX_SCORE)
         {
            g2.drawString("Player Two Wins", WIN_STRING_X, WIN_STRING_Y );
            ball.pause();
         }
      }

      if (gameStarted == false && gameOver == false)
      {
         g2.setColor(Color.WHITE);
         g2.setFont(new Font("Courier", Font.BOLD, 40));

         g2.drawString("Press Space To Start", 50, 110 );
         
         String s  = difficulty == 0? "<  Easy  >" :difficulty == 1?
               "< Medium >" : "<  Hard  >";
         g2.drawString("Difficulty: " + s, 50, 150);

         final int FONT_SIZE = 20;
         g2.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
         
         final int TEXT_X_COORD = 50;
         final int DIFLT_MSG_Y = 195; 
         g2.drawString("Select difficulty with right/left arrow key.",
               TEXT_X_COORD, DIFLT_MSG_Y);
         
         final int PTS_INST_Y = 215;
         g2.drawString("First player to 7 points wins!",
               TEXT_X_COORD, PTS_INST_Y);
         
         final int P1_INST_Y = 255;
         g2.drawString("Player One use W & S Keys",
               TEXT_X_COORD, P1_INST_Y);
         
         final int P2_INST_Y = 275; 
         g2.drawString("Player Two use Up & Down Keys",
               TEXT_X_COORD, P2_INST_Y);
      }


      if(gameStarted && gameOver == false)
      {
         if(resetGameCheck == 1)
         {
            ball.reset();
            ball.resetScore();
            leftPaddle.resetPaddle();
            rightPaddle.resetPaddle();
            resetGameCheck++;
         }
         
         ball.draw(g2); 
         leftPaddle.draw(g2);
         rightPaddle.draw(g2);

         g2.setColor(Color.WHITE);

         Stroke thindashed = new BasicStroke(
               5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
               1.0f, new float[] { 5.0f, 5.0f}, 0.0f);
         g2.setStroke(thindashed);

         final int HALF_LINE_X_PT = 300;
         final int Y_TOP_PT = 0;
         final int Y_BOTTOM_PT = 400;
         g2.drawLine(HALF_LINE_X_PT, Y_TOP_PT, HALF_LINE_X_PT, Y_BOTTOM_PT);

         g2.setColor(Color.WHITE);
         final int FONT_SIZE = 25;
         g2.setFont(new Font("Courier", Font.BOLD, FONT_SIZE));
         
         final int SCORE_Y = 40; 
         final int P1_SCORE_X = 260;
         final int P2_SCORE_X = 325; 
         g2.drawString("" + ball.getP1Score(), P1_SCORE_X, SCORE_Y);
         g2.drawString("" + ball.getP2Score(), P2_SCORE_X, SCORE_Y);

         
         if(ball.getP1Score() >= MAX_SCORE || ball.getP2Score() >= MAX_SCORE)
         {
            gameOver = true;
         }
      }

      repaint();
   }

   /**
    * Sets the state of gamePaused to the value of the parameter
    * @param paused whether or not the game is paused
    */
   public void setPaused(boolean paused)
   {
      gamePaused = paused; 
   }

   /**
    * Return the state of gamePaused
    * @return gamePaused
    */
   public boolean getPaused()
   {
      return gamePaused;
   }

   /**
    * Pause the GamePannel
    */
   public void pausePanel()
   {
      ball.pause();  
   }

   /**
    * Resume the game
    */
   public void resumeAll()
   {
      ball.resume();
      requestFocusInWindow();
   }

   /**
    * Start the Game
    */
   public void startGame()
   {
      gameStarted = true;
   }

   /**
    * Return the state of gameStarted
    * @return gameStarted
    */
   public boolean getGameStarted()
   {
      return gameStarted;
   }

   /**
    * Set the difficulty level for all components of the UI
    * @param dif difficulty level
    */
   public void setDifficultyAll(int dif)
   {
      ball.setDifficulty(dif);
      leftPaddle.setDifficulty(dif);
      rightPaddle.setDifficulty(dif);
   }

   @Override
   /**
    * Runs the thread of the GamePanel
    */
   public void run() 
   {
      Thread threadBall = new Thread(ball);
      Thread threadLPaddle = new Thread(leftPaddle);
      Thread threadRPaddle = new Thread(rightPaddle);

      threadBall.start();
      threadLPaddle.start();
      threadRPaddle.start();
   }
   
   /**
    * Listens for a key press 
    * @param e a key press
    */
   public void keyPressed(KeyEvent e)
   {
      int k = e.getKeyCode();

      if(k == KeyEvent.VK_SPACE)
      {
         gameStarted = true;
      }
      else if(k == KeyEvent.VK_RIGHT)
      {
         if(difficulty < 2)
         {
            difficulty++;
            setDifficultyAll(difficulty);
         }
         else
         {
            difficulty = 0;
            setDifficultyAll(difficulty);
         }
      }
      else if(k == KeyEvent.VK_LEFT)
      {
         if(difficulty > 0)
         {
            difficulty--;
            setDifficultyAll(difficulty);
         }
         else
         {
            difficulty = 2;
            setDifficultyAll(difficulty);
         }
      }
      leftPaddle.keyPressed(e);
      rightPaddle.keyPressed(e);
   }

   /**
    * Listens for a keyRelease
    * @param e the KeyEvent
    */
   public void keyReleased(KeyEvent e)
   {
      leftPaddle.keyReleased(e);
      rightPaddle.keyReleased(e);
   }

   /**
    * Listens for a key to be typed
    * @param e the KeyEvent
    */
   public void keyTyped(KeyEvent e){}
}


