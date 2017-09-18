package edu.truman.emroskicrane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A program that edits a frame with a game board.
 * @author Arber Emroski and Brandon Crane
 * @version December 6, 2015
 */
public class Pong {
	
	//Attributes
   private JFrame frame; 
   private GamePanel gamePanel;
   private JPanel borderPanel;
   private JPanel buttonPanel;
   private JButton pauseButton;
   
   //Constants
   public static final int WIDTH = 650;
   public static final int HEIGHT = 450;
   public static final int GAME_PANEL_WIDTH = 600;
   public static final int GAME_PANEL_HEIGHT = 400;
   public static final int BLACK_BORDER_WIDTH = 20;
   public static final int WHITE_BORDER_WIDTH = 5;
   
   /**
    * Constructs a Pong game. 
    */
   public Pong()
   {
      frame = new JFrame();
      gamePanel = new GamePanel();
      borderPanel = new JPanel();
      buttonPanel = new JPanel();
      
      frame.setTitle("Pong");
      frame.setSize(WIDTH, HEIGHT);
      gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      borderPanel.setLayout(new BorderLayout());


      // Draw the borders
      frame.setBackground(Color.BLACK);
      borderPanel.setBackground(Color.BLACK);
      gamePanel.setBackground(Color.BLACK);
      buttonPanel.setBackground(Color.BLACK);

      borderPanel.setBorder(BorderFactory.createMatteBorder(
            BLACK_BORDER_WIDTH, BLACK_BORDER_WIDTH,
            BLACK_BORDER_WIDTH, BLACK_BORDER_WIDTH, Color.BLACK));

      gamePanel.setBorder(BorderFactory.createMatteBorder(
            WHITE_BORDER_WIDTH, WHITE_BORDER_WIDTH,
            WHITE_BORDER_WIDTH, WHITE_BORDER_WIDTH, Color.WHITE));
      
     

      borderPanel.add(gamePanel);
      frame.add(borderPanel, BorderLayout.CENTER);
      frame.add(buttonPanel, BorderLayout.SOUTH);

      frame.setVisible(true);
   }
   
   /**
    * Creates a button to stop and resume the pong game.
    */
   public void addButton()
   {
      pauseButton = new JButton();
      buttonPanel.add(pauseButton);
      
      final String PAUSE_MSG = "Pause";
      final String RESUME_MSG = "Resume";

      
      pauseButton.setText(PAUSE_MSG);
      
      pauseButton.addActionListener(new 
            ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            if(!gamePanel.getPaused())
            {
               gamePanel.pausePanel();
               gamePanel.setPaused(true);
               pauseButton.setText(RESUME_MSG);
            }
            
            else
            {
               gamePanel.resumeAll(); 
               gamePanel.setPaused(false);
               pauseButton.setText(PAUSE_MSG);
            }
         }
         
      });
   }
   
   public static void main(String[] args)
   {
      Pong pongGame =  new Pong();
      pongGame.addButton();
   }
}