package edu.truman.emroskicrane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * A ball shape. 
 * @author Arber Emroski and Brandon Crane
 * @version December 6, 2015
 */
public class Ball implements Runnable{

   //Attributes
   private int x;
   private int y;
   private int xDirection; 
   private int yDirection; 
   private int previousX;
   private int previousY; 
   private int difficulty;
   private int p1Score;
   private int p2Score;
   private Rectangle ball;
   
   //Constants
   public static final int EASY = 0;
   public static final int MEDIUM = 1;
   public static final int HARD = 2;
   public static final int POS_VELOCITY = 1;
   public static final int NEG_VELOCITY = -1;
   public static final int NO_VELOCITY = 0;
   public static final int INITIAL_SCORE = 0;
   
   /**
    * Constructs the rectangle ball object. 
    * @param x top left coordinate of the ball
    * @param y top left coordinate of the ball
    */
   public Ball(int x,int y)
   {
      
      this.x = x; 
      this.y = y; 
      setP1Score(INITIAL_SCORE);
      setP2Score(INITIAL_SCORE);


      Random r = new Random();
      int rDir = r.nextInt(1);
      if(rDir == 0)
      {
         rDir--;
         setXDirection(POS_VELOCITY);
      }

      int yrDir = r.nextInt(1);
      if(yrDir == 0)
      {
         yrDir--;
         setYDirection(POS_VELOCITY);
      } 

      final int WIDTH = 10;
      final int HEIGHT = 10;
      ball = new Rectangle(this.x, this.y, WIDTH, HEIGHT); 
   }
   
   /**
    * Resets the ball to the middle of the screen.
    */
   public void reset()
   {
      final int CENTER_X = (600 / 2);
      final int CENTER_Y = (400 / 2);

      ball.x = CENTER_X;
      ball.y = CENTER_Y;
      setXDirection(getXDirection() * NEG_VELOCITY);
   }  
   
   /**
    * Resets the scores for a new game. 
    */
   public void resetScore()
   {
      setP1Score(INITIAL_SCORE);
      setP2Score(INITIAL_SCORE);
   }

   /**
    * Draws the rectangle paddle object. 
    * @param g the graphics context
    */
   public void draw(Graphics g)
   {
      g.setColor(Color.WHITE);
      g.fillRect(ball.x, ball.y, ball.width, ball.height);
   }
   
   /**
    * Stops the ball from traveling. 
    */
   public void pause()
   {
      previousX = getXDirection();
      previousY = getYDirection();
      setXDirection(NO_VELOCITY);
      setYDirection(NO_VELOCITY);
   }
   
   /**
    * Moves ball in direction it was traveling after a before a pause break.
    */
   public void resume()
   {

      setXDirection(previousX);
      setYDirection(previousY);
   }

   /**
    * Moves the the ball object.
    */
   public void move()
   {
      final int PANEL_LEFT_BORDER = 0;
      final int PANEL_RIGHT_BORDER = 590;
      final int PANEL_TOP_BORDER = 5;
      final int PANEL_BOTTOM_BORDER = 330;
      final int INC_SCORE = 1; 

      ball.x += xDirection;
      ball.y += yDirection;

      if(ball.x <= PANEL_LEFT_BORDER)
      { 
         setP2Score(getP2Score() + INC_SCORE);
         reset();

      }
      if(ball.x >= PANEL_RIGHT_BORDER)
      {
         setP1Score(getP1Score() + INC_SCORE);
         reset();
      }

      if(ball.y <= PANEL_TOP_BORDER)
      {
         setYDirection(POS_VELOCITY);
      }
      if(ball.y >= PANEL_BOTTOM_BORDER)
      {
         setYDirection(NEG_VELOCITY);
      }
   }

   /**
    * Runs the thread of ball.
    */
   @Override
   public void run() 
   {
      try
      {
         final int SLEEP_E = 6;
         final int SLEEP_M = 5;
         final int SLEEP_H = 4;
         while(true)
         {
            move();
            if(difficulty == EASY)
            {
               Thread.sleep(SLEEP_E);
            }
            else if (difficulty == MEDIUM)
            {
               Thread.sleep(SLEEP_M);
            }
            else if (difficulty == HARD)
            {
               Thread.sleep(SLEEP_H);
            }
         }
      }
      catch(Exception e)
      {
         System.err.println(e.getMessage());
      } 
   }
   
   /**
    * Sets the level of difficulty
    * @param dif the level difficulty
    */
   public void setDifficulty(int dif)
   {
      difficulty = dif;
   }
   
   /**
    * Sets the horizontal direction of the ball
    * @param xdir the horizontal direction of the ball
    */
   public void setXDirection(int xdir)
   {
      xDirection = xdir;
   }
   
   /**
    * Accesses the value of xDirection.
    * @return the value of xDirection
    */
   public int getXDirection()
   {
      return xDirection;
   }

   /**
    * Accesses the ball object
    * @return the ball object
    */
   public Rectangle getBall()
   {
      return ball;
   }
   
   /**
    * Accesses the value of yDirection
    * @return the value of yDirection
    */
   public int getYDirection()
   {
      return yDirection;
   }

   /**
    * Sets the vertical direction of the ball
    * @param ydir the vertical direction 
    */
   public void setYDirection(int ydir)
   {
      yDirection = ydir;
   }
   
   /**
    * Accesses the value of y.
    * @return the value of y
    */
   public int getY()
   {
      return y;
   }
   
   /**
    * Accesses the value of x
    * @return the value of x
    */
   public int getX()
   {
      return x;
   }

   /**
    * Sets the x-coordinate of the ball
    * @param x the x-coordinate of the ball
    */
   public void setX(int x)
   {
      this.x = x;
   }
   
   /**
    * Accesses the value or score of p1Score.
    * @return the value or score of p1Score
    */
   public int getP1Score()
   {
      return p1Score;
   }
   
   /**
    * Sets score of left paddle player.
    * @param p1Score the score of the left paddle player
    */
   public void setP1Score(int p1Score)
   {
      this.p1Score = p1Score;
   }

   /**
    * Accesses the value or score of p2Score.
    * @return the value or score of p2Score
    */
   public int getP2Score()
   {
      return p2Score;
   }

   /**
    * Sets score of right paddle player.
    * @param p2Score the score of the right paddle player
    */
   public void setP2Score(int p2Score)
   {
      this.p2Score = p2Score;
   }
}
