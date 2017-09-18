package edu.truman.emroskicrane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point; 
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * A paddle shape. 
 * @author Arber Emroski and Brandon Crane
 * @version December 6, 2015
 */
public class Paddle implements Runnable {
   
   //Attributes
   private int x;
   private int y;
   private int yDirection; 
   private int id;
   private int upKey;
   private int downKey; 
   private int height;
   private Ball ball;
   private Rectangle paddle;
   private boolean up;
   private boolean down;
   private int paddleX;
   private int paddleY;
   private int difficulty;
   private GamePanel panel;
   
   //Constants
   public static final int paddleWidth = 10;
   public static final int tempHeight = 50;
   public static final int EASY = 0;
   public static final int MEDIUM = 1;
   public static final int HARD = 2;
   
   /**
    * Constructs the rectangle paddle object. 
    * @param x top left coordinate of a paddle
    * @param y top left coordinate of a paddle
    * @param id identification number of a paddle
    * @param upKey respective control key of paddle
    * @param downKey respective control key of paddle
    * @param ball a rectangle ball object 
    * @param panel a GamePanel object
    */
   public Paddle(int x, int y, int id, int upKey, int downKey,
         Ball ball, GamePanel panel)
   {
      this.setX(x);
      this.setY(y);
      this.setHeight(height);
      this.id = id;
      this.upKey = upKey;
      this.downKey = downKey;
      this.ball = ball;
      this.panel = panel;
      up = false;
      down = false;

      paddle = new Rectangle(x, y, paddleWidth, tempHeight);
   }

   /**
    * Sets the difficulty of the game by changing the paddle size.
    * @param dif the difficulty level
    */
   public void setDifficulty(int dif)
   {
      final int EASY_HEIGHT = 50;
      final int MEDIUM_HEIGHT = 40;
      final int HARD_HEIGHT = 30;

      difficulty = dif;
      if(!panel.getGameStarted())
      {
         if (difficulty == EASY)
         {
            paddle.setRect(x, y, paddleWidth, EASY_HEIGHT);
         }
         else if (difficulty == MEDIUM)
         {
            paddle.setRect(x, y, paddleWidth, MEDIUM_HEIGHT);
         }
         else if (difficulty == HARD)
         {
            paddle.setRect(x, y, paddleWidth, HARD_HEIGHT);
         }
      }
   }

   /**
    * Sets the height of a paddle.
    * @param height the height of a paddle
    */
   public void setHeight(int height)
   {
      this.height = height; 
   }

   /**
    * Resets the paddle to original location. 
    */
   public void resetPaddle()
   {
      x = paddleX;
      y = paddleY;
   }

   /**
    * Listens for a key press to move paddle object. 
    * @param event a key press
    */
   public void keyPressed(KeyEvent event)
   {
      if (event.getKeyCode() == upKey)
      {
         up = true;
      }
      if (event.getKeyCode() == downKey)
      {
         down = true;
      }
   }

   /**
    * Listens for when a key press is released to to stop moving paddle
    * object. 
    * @param event a key release
    */
   public void keyReleased(KeyEvent event){

      if (event.getKeyCode()== upKey)
      {
         up = false;
      }
      if (event.getKeyCode() == downKey)
      {
         down = false;
      }
   }

   /**
    * Moves the the paddle object by key press.
    */
   public void move()
   {
      if(up)
      {
         paddle.y--;
      }

      if(down)
      {
         paddle.y++;
      }

      if(paddle.y <= 0)
      {
         paddle.y = 0;
      }


      if(difficulty == EASY)
      {
         final int PADDLE_OFFSET_TO_BOTTOM = (50/2);
         final int EASY_BOTTOM_BOUND = 320;
         
         if(paddle.y + PADDLE_OFFSET_TO_BOTTOM >= EASY_BOTTOM_BOUND)
         {
            paddle.y = EASY_BOTTOM_BOUND - PADDLE_OFFSET_TO_BOTTOM;
         }
      }
      else if(difficulty == MEDIUM)
      {
         final int PADDLE_OFFSET_TO_BOTTOM = (40/2);
         final int MEDIUM_BOTTOM_BOUND = 325;
         
         if(paddle.y + PADDLE_OFFSET_TO_BOTTOM >= MEDIUM_BOTTOM_BOUND)
         {
            paddle.y = MEDIUM_BOTTOM_BOUND - PADDLE_OFFSET_TO_BOTTOM;
         }
      }
      else if(difficulty == HARD)
      {
         final int PADDLE_OFFSET_TO_BOTTOM = (30/2);
         final int HIGH_BOTTOM_BOUND = 330;
         
         if(paddle.y + PADDLE_OFFSET_TO_BOTTOM >= HIGH_BOTTOM_BOUND)
         {
            paddle.y = HIGH_BOTTOM_BOUND - PADDLE_OFFSET_TO_BOTTOM;
         }
      }

      
      if(checkCollision(new Point(ball.getX(), ball.getY() - 5))
            || checkCollision(new Point(ball.getX() + 10, ball.getY() - 5)))
      {
         ball.setXDirection(ball.getXDirection() * -1);
      }
   }

   /**
    * Checks for a collision and returns whether one occurs. 
    * @param p a single point
    * @return true if a collision between paddle and ball occur,
    *  false otherwise
    */
   public boolean checkCollision(Point p)
   {
      boolean colliding = false;
      if(paddle.intersects(ball.getBall()))
      {
         colliding = true;
      }
      else
      {
         colliding = false;
      }

      return colliding;
   }

   /**
    * Draws the rectangle paddle object. 
    * @param g the graphics context
    */
   public void draw(Graphics g)
   {
      switch(id){
         default:
         {
            break;
         }
         case 1: 
         {
            g.setColor(Color.WHITE);
            g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
            break;
         }
         case 2:
         {
            g.setColor(Color.WHITE);
            g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
            break;
         }

      }
   }
   
   /**
    * Runs the thread of Paddle. 
    */
   @Override
   public void run() 
   {
      try
      {
         while(true)
         {
            move();
            final int SLEEP_LESS = 4;
            final int SLEEP_REG = 6;



            if(difficulty == HARD)
            {
               Thread.sleep(SLEEP_LESS);
            }
            else
            {
               Thread.sleep(SLEEP_REG);
            }
         }
      }
      catch(Exception e)
      {
         System.err.println(e.getMessage());
      } 
   }

   /**
    * Sets the vertical velocity of a paddle.
    * @param ydir the vertical velocity of a paddle
    */
   public void setYDirection(int ydir)
   {
      this.yDirection = ydir;
   }

   /**
    * Accesses the value of yDirection.
    * @return the value of yDirection
    */
   public int getYDirection()
   {
      return yDirection;
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
    * Sets the y-coordinate of a paddle.
    * @param y the y-coordinate of a paddle
    */
   public void setY(int y)
   {
      this.y = y;
   }

   /**
    * Accesses the value of x. 
    * @return the value of x
    */
   public int getX()
   {
      return x;
   }

   /**
    * Sets the x-coordinate of a paddle.
    * @param x the x-coordinate of a paddle
    */
   public void setX(int x)
   {
      this.x = x;
   }
}


