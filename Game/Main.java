/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * @version 1.0
 * @date 06/05/17
 * 
 * Main method for the World War II + Risk game.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Rectangle2D;

public class Main extends Canvas implements KeyListener {
   
   // game settings
   private int currentPlayer = 1;
   
   private static final int soldierDamage = 5; // soldier damage rate
   private static final int tankDamage = 15;  // tank damage rate
   private static final int cavalryDamage = 10; // cavalry damage rate
   
   private final int TANKBALL_SPEED = 30; 
   
   private Integer time = 0;
   private Integer clock = 0;
   private int turns = 1; // this will count the amount of turns within the game; default is 1 for first player's turn.
   
   private ArrayList<Integer> scores_1 = new ArrayList<Integer>();
   private ArrayList<Integer> scores_2 = new ArrayList<Integer>();
   
   // mini-game settings, prior to the battle
   
   private static final int soldierSpace = 1; // space of soldier
   private static final int tankSpace = 10; // space of tank
   private static final int cavalrySpace = 5; // space of cavalry
   
   private static final int battleBonus = 150; // battle bonus value
   
   private Army army1 = new Army(1); // army of first player; default
   private Integer soldiers1 = 0; // amount of soldiers
   private Integer tanks1 = 0; // amount of tanks
   private Integer cavalry1 = 0;// amount of cavalry
   private Integer space1 = 0;
   private Integer score1 = 0;
   
   private Army army2 = new Army(4); // army of second player
   private Integer soldiers2 = 0;
   private Integer tanks2 = 0;
   private Integer cavalry2 = 0;
   private Integer space2 = 0;
   private Integer score2 = 0;
   
   private int textTimerValue; // custom timer, in a sense.
   private int loadUp = 1; // int that dictates which text to print, in a sequence
   
   private boolean spaceError = false;
   
   private final int SCREEN_WIDTH = 500;
   private final int SCREEN_HEIGHT = 520;
   
   // mini-game #1, part 2, settings
   private ArrayList<Tank> enemyTanks = new ArrayList<Tank>();
   private ArrayList<TankBall> tankballs = new ArrayList<TankBall>();
   private int tankXPos = 0;
   private int tankYPos = 260;
   private Integer health = 100; // H.P of player 1
   private String direction = "R";
   private final int INIT_TANKS = 5;
   private int TANK_SPEED = -1;
   
   private Integer tanksDestroyed1 = 0;
   
   private Tank user = new Tank("tank", tankDamage, tankSpace, tankXPos, tankYPos);
   
   // mini-game #2, part 2, settings
   // might store the x-y coordinates somewhere else...
   private int[] x1 = {0,30,60,80,90,130,170,210,230,270,285,290,305,310,315,320,340,360,380,400,420,430,470,490,530,560,580,590,620,630,660,680,700,710,720,740,790,860,940,960,990,1000,1010,1040,1060,1100,1110,1140,1280};
   private int[] y1 = {450,450,410,360,390,410,450,380,300,330,360,380,390,410,445,430,410,430,400,380,430,440,410,380,350,440,430,425,410,430,440,445,410,390,300,330,390,440,410,400,440,430,410,400,390,400,430,450,450};
   private int[] x2 = {30, 1140};
   private int[] y2 = {450, 450};
   
   private int heliX = 10;
   private int heliY = 90;
   private boolean isPressed = false;
   private int closestToLeft;
   
   private Integer fuel = 1000; // initial fuel
   
   private Rectangle2D.Double helicopterOutline;
   private Mountain mountain;
   private Mountain hidden;
   private ArrayList<Tower> towers = new ArrayList<Tower>();
   private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
   private ArrayList<Star> stars = new ArrayList<Star>();
   private ArrayList<TankBall> missiles = new ArrayList<TankBall>();
   private ArrayList<Rectangle2D.Double> missiles_outline = new ArrayList<Rectangle2D.Double>();
   private int MISSILE_SPEED = 0;
   
   private Rectangle2D.Double bonus;
   
   // screen settings
   private String screenName = "home";
   private BufferStrategy strategy;
   
   private String lastClicked;
   private int cursorTrackSelection = 1; // default
   private String currentClicked = "start";
   private String currentCountry = "ussr"; // default
   private final JFXPanel fxPanel = new JFXPanel();
   private boolean musicOn = true;
   private boolean runTimer = true;
   
   private String song1 = "music/bgmusic.mp3"; 
   private String song2 = "music/minigame1.mp3";
   private String song3 = "music/minigame2.mp3";
      
   private Media bgmusic = new Media(new File(song1).toURI().toString());
   private Media minigame1_music = new Media(new File(song2).toURI().toString());
   private Media minigame2_music = new Media(new File(song3).toURI().toString());
   
   private MediaPlayer mediaPlayer = new MediaPlayer(bgmusic); // background music
   private MediaPlayer mediaPlayer2 = new MediaPlayer(minigame1_music); // game 1
   private MediaPlayer mediaPlayer3 = new MediaPlayer(minigame2_music); // game 2
   
   // graphics to be rendered
   private Font font;
   private Font bigFont;
   private Font smallFont;
   private Font smallestFont;
   
   private BufferedImage homebg;
   private BufferedImage selectionbg;
   private BufferedImage defaultbg;
   private BufferedImage aboutbg;
   private BufferedImage troop_selectionbg;
   private BufferedImage howtobg;
   
   private BufferedImage minigame1_bg;
   private BufferedImage minigame1_bg_terrain;
   private BufferedImage bg_overlay;
   
   private BufferedImage arrow;
   private BufferedImage arrow_flipped;
   private BufferedImage clouds;
   private BufferedImage clouds_sunset;
   private BufferedImage healthbar;
   private BufferedImage healthbar_green;
   private BufferedImage tankBall;
   
   private int healthWidth;
   
   private BufferedImage jet;
   private BufferedImage jet_enemy;
   private BufferedImage soldier;
   private BufferedImage soldier_enemy;
   private BufferedImage tank;
   private BufferedImage tank_enemy;
   private BufferedImage tank_top_view; // right view
   private BufferedImage tank_top_view_l;
   private BufferedImage tank_top_view_d;
   private BufferedImage tank_top_view_u;
   private BufferedImage tank_top_view_enemy;
   private BufferedImage tank_top_view_enemy_l;
   private BufferedImage tank_top_view_enemy_u;
   private BufferedImage tank_top_view_enemy_d;
   private BufferedImage star;
   private BufferedImage heli;
   private BufferedImage missile;
   
   private JFrame frame;
   
   private int w; // this value & h should be equal for all backgrounds...
   private int h;
   
   private int arrowWidth;
   private int arrowHeight;
   
   private final int arrowYHome = 268; 
   private int cloudsXPos = 0;
   private int cloudsYPos = 0;
   private int tanksXPos = -200;
   private int tanksYPos = 300;
   
   public Main() throws IOException {
      try { // load up the images here...
        homebg = ImageIO.read(new File("images/open.png"));
        selectionbg = ImageIO.read(new File("images/selectCountry.png"));
        defaultbg = ImageIO.read(new File("images/default.png"));
        aboutbg = ImageIO.read(new File("images/about.png"));
        troop_selectionbg = ImageIO.read(new File("images/selection screen.png"));
        howtobg = ImageIO.read(new File("images/howto.png"));
        minigame1_bg_terrain = ImageIO.read(new File("images/terrain_bg.png"));
        bg_overlay = ImageIO.read(new File("images/blackoverlay.png"));
        
        jet = ImageIO.read(new File("images/jet.png"));
        jet_enemy = ImageIO.read(new File("images/jet_enemy.png"));
        soldier = ImageIO.read(new File("images/soldier.png"));
        soldier_enemy = ImageIO.read(new File("images/enemy_soldier.png"));
        tank = ImageIO.read(new File("images/tank_sunset_normal.png"));
        tank_enemy = ImageIO.read(new File("images/enemy_tank.png"));
        tank_top_view = ImageIO.read(new File("images/tank_top_view.png"));
        tank_top_view_l = ImageIO.read(new File("images/tank_top_view_l.png"));
        tank_top_view_u = ImageIO.read(new File("images/tank_top_view_u.png"));
        tank_top_view_d = ImageIO.read(new File("images/tank_top_view_d.png"));
        tank_top_view_enemy = ImageIO.read(new File("images/enemy_tank_top_view.png"));
        tank_top_view_enemy_l = ImageIO.read(new File("images/enemy_tank_top_view_l.png"));
        tank_top_view_enemy_u = ImageIO.read(new File("images/enemy_tank_top_view_u.png"));
        tank_top_view_enemy_d = ImageIO.read(new File("images/enemy_tank_top_view_d.png"));
        star = ImageIO.read(new File("images/stardot.png"));
        heli = ImageIO.read(new File("images/heli.png"));
        missile = ImageIO.read(new File("images/bullet_l.png"));
        
        arrow = ImageIO.read(new File("images/arrow.png"));
        arrow_flipped = ImageIO.read(new File("images/arrow180.png"));
        clouds = ImageIO.read(new File("images/clouds.png"));
        clouds_sunset = ImageIO.read(new File("images/clouds_sunset.png"));
        tankBall = ImageIO.read(new File("images/tankball.png"));
        minigame1_bg = ImageIO.read(new File("images/sunsetbg.png"));
        healthbar = ImageIO.read(new File("images/healthbar.png"));
        healthbar_green = ImageIO.read(new File("images/healthbar_green.png"));
        healthWidth = healthbar_green.getWidth(null) / 2;
        
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer3.setCycleCount(MediaPlayer.INDEFINITE);
      
      try {
         InputStream is = Main.class.getResourceAsStream("fonts/VCR_OSD_MONO_1.001.ttf");
         font = Font.createFont(Font.TRUETYPE_FONT, is);
         bigFont = font.deriveFont(30f);
         smallFont = font.deriveFont(18f);
         smallestFont = font.deriveFont(12f);
         
      } catch (FontFormatException e) {
         e.printStackTrace();
      }
      
      frame = new JFrame("World War II + Risk Game");
      frame.add(this);
      frame.getContentPane().setBackground(Color.BLACK);
      frame.setVisible(true);
      frame.setResizable(false);
      frame.addWindowListener(
         new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
         });
         
      render();
      addKeyListener(this);
      
      // This is where we create objects that we will not want to be recreated over and over...the m
      // method will handle it wrong if we put it in there.
      
      // Load up initial amount of tanks here.
      for (int j=0; j<INIT_TANKS; j++) {
    	 int randomNum = ThreadLocalRandom.current().nextInt(60, 460 + 1);
    	 int randomNumX = ThreadLocalRandom.current().nextInt(400, 520 + 1);
      	 enemyTanks.add(new Tank("tank", tankDamage, tankSpace, randomNumX, randomNum));
      }
      closestToLeft = 0; // this will store the index value of the leftmost star, which will
      // help with maintaining the perspective and scrolling with the ship. It will be our
      // x (initial value) in our Δx through the user's screen.
      
      // Load up the stars here, for Game 2. These are displayed in the background.
      for (int s=0; s<45; s++) {
  		int randomX = ThreadLocalRandom.current().nextInt(60, 1280 + 1); // generates random X-value for the star
  	  	int randomY = ThreadLocalRandom.current().nextInt(10, 520 + 1); // generates random Y-value for the star
  	  	stars.add(new Star(randomX, randomY));
  	  	if (stars.get(s).getX() < stars.get(closestToLeft).getX()) { // this might cause an IndexOutOfBounds later.
  	  		closestToLeft = s;
  	  	}
  	  }
      // Load up the towers here, for Game 2.
      for (int r=0; r<10; r++) {
    	 int randomNumX = ThreadLocalRandom.current().nextInt(100, 900 + 1);
     	 int randomNumY = ThreadLocalRandom.current().nextInt(380, 390 + 1);
     	 towers.add(new Tower(40, 80, randomNumX, randomNumY, Color.WHITE));
      }
      
      int delay = 50; //milliseconds - started at 1000
      ActionListener taskPerformer = new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
        	// this is where music is handled through the game.
        	// To understand what each media player, its more specific information is provided in where it was instantiated.
            if (!screenName.equals("airfight")) {
               if (musicOn == true) {
                  mediaPlayer.setAutoPlay(true);
               } else {
                  mediaPlayer.stop();
               }
            } else {
               if (musicOn == true) {
                  mediaPlayer.stop();
                  if (currentPlayer == 1) {
                	  mediaPlayer2.setAutoPlay(true);
                  } else {
                	  mediaPlayer3.setAutoPlay(true);
                	  mediaPlayer2.stop();
                  }
               } else {
            	  if (!screenName.equals("airfight")) {
            		  mediaPlayer2.stop();
            	  } 
               }
            }
            if (screenName.equals("airfight")) {
            	if ((musicOn == false) && (currentPlayer == 1))
            		mediaPlayer2.stop();
            	else if ((musicOn == false) && (currentPlayer == 2))
            		mediaPlayer3.stop();
            }
            render();
            // this is where the text and screen sequences are handled for the games.
            if (screenName.equals("airfight")) {
               if (runTimer == true) {
            	   textTimerValue+=10; // serves to act as a timer value, adding on.
               }
               // these conditions here will trigger different sequences when the textTimerValue is equal to a certain value.
               if (textTimerValue < 500) {
                  loadUp = 1;
               } else if ((textTimerValue > 500) && (textTimerValue < 2000)) {
                  loadUp = 2;
               } else if ((textTimerValue > 2000) && (textTimerValue < 3000)) { // CHANGE TIMES.
                  loadUp = 3; 
               } else if (textTimerValue >= 3000 && (loadUp < 4)) {   
            	  runTimer = false;
            	  loadUp = 4;
            	  if ((currentPlayer == 1) && (turns > 1)) { // resets for player 1
            		  score1=0;
            		  clock=0;
            		  time=0;
            		  health=100;
            	  }
            	  if (currentPlayer == 2) { // resets for player 2
            		  clock=0;
            		  time=0;
            		  fuel=1000;
            		  score2 = 0;
            	  }
               } else if (loadUp == 4) {
            	   if (clock < 60) {
            		   time+=20;
            		   if ((time >= 1000) && (time % 1000 == 0)) {
                		   clock++; // clock value; this will be executed after 1000 milliseconds, or 1 second.
                	   }
            		   if (time % 1000 == 0) {
            			   // tankballs.add(new TankBall("", tankDamage, 0, direction, tankXPos, tankYPos));
            			   // Structure of how to add a tank ball is above. 
            			   int randomNumY= ThreadLocalRandom.current().nextInt(100, 300 + 1);
            			   MISSILE_SPEED+=2;
            			   missiles.add(new TankBall("", 50, 0, "L", 1280, randomNumY));
            			   missiles_outline.add(new Rectangle2D.Double(1280, randomNumY, (missile.getWidth(null)), (missile.getHeight(null))));
            		   }
            		   if (currentPlayer == 1) {
            			   if ((healthWidth <= 0) || (health <= 0)) {
            				   // ... to show it's empty.
            				   // fuel statement only applies to Game 2.
            				   loadUp = 5;
            			   }
            		   } else if (currentPlayer == 2) {
            			   if (fuel <= 0) {
            				   loadUp = 5;
            			   }
            		   }
            	   } else {
            		   loadUp = 5;
            	   }
               } else if (loadUp == 5) {
            	   // ...
            	   mediaPlayer.stop();
               }
            }
         }
      };
      new Timer(delay, taskPerformer).start();       
            requestFocusInWindow();
   }
   public void move (int speed, int amount) { // move method for the images in the game. 
	   speed += amount;
   }
   // All the magic happens here for the graphics.
   public void paint (Graphics g) {
      w = defaultbg.getWidth(null) / 2;
      h = defaultbg.getHeight(null) / 2;
      arrowWidth = arrow.getWidth(null) / 2;
      arrowHeight = arrow.getHeight(null) / 2;
      
      frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT); // set the frame's width and height here.
      
      // This is where we handle the screens of the game.

      if (screenName.equals("home")) { // Home screen design.
         g.drawImage(homebg, 0, 0, w, h, null);
         g.setFont(smallFont);
    	 g.setColor(Color.WHITE);
         
         if (currentClicked.equals("start")) {
        	// Making the clouds moves starts right here. It's repeated on all the other pages, minus the games.
            if (cloudsXPos < 600) { 
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
               cloudsXPos++;
            } else {
               cloudsXPos = -750;
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            }
            g.drawImage(arrow, 20, arrowYHome, arrowWidth, arrowHeight, null);
         }
         if (currentClicked.equals("about")) {
            if (cloudsXPos < 600) {
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
               cloudsXPos++;
            } else {
               cloudsXPos = -750;
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            }
            g.drawImage(arrow, 140, arrowYHome, arrowWidth, arrowHeight, null);
         }
         if (currentClicked.equals("howto")) {
            if (cloudsXPos < 600) {
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
               cloudsXPos++;
            } else {
               cloudsXPos = -750;
               g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            }
            g.drawImage(arrow, 255, arrowYHome, arrowWidth, arrowHeight, null);
         }
      } else if (screenName.equals("selection")) { // Selection screen page.
         g.drawImage(selectionbg, 0, 0, w, h, null);
         if (currentCountry.equals("ussr")) {
            g.drawImage(arrow, 0, 350, arrowWidth, arrowHeight, null); 
         } else if (currentCountry.equals("us")) {
            g.drawImage(arrow, 155, 350, arrowWidth, arrowHeight, null); 
         } else if (currentCountry.equals("france")) {
            g.drawImage(arrow, 320, 350, arrowWidth, arrowHeight, null); 
         }
         
         if (cloudsXPos < 600) {
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            cloudsXPos++;
         } else {
            cloudsXPos = -750;
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
         }
      } else if (screenName.equals("default")) { // Default screen page. This actually is never showed in the game, but it is what the
    	  										 // background of the game is, including the moving clouds.
         g.drawImage(defaultbg, 0, 0, w, h, null);   
      } else if (screenName.equals("about")) {
         g.drawImage(aboutbg, 0, 0, w, h, null);
         g.drawImage(arrow_flipped, 75, 425, arrowWidth, arrowHeight, null);
         if (cloudsXPos < 600) {
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            cloudsXPos++;
         } else {
            cloudsXPos = -750;
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
         }
      } else if (screenName.equals("troop_selection")) { // Troop selection page.
         g.drawImage(troop_selectionbg, 0, 0, w, h, null);
         g.setColor(Color.WHITE);
         g.setFont(smallestFont);
  
         g.drawString("Current player: " + Integer.valueOf(currentPlayer), 375, 225);
         
         if ((turns > 1) && (turns % 2 == 1)) {
        	 g.setFont(smallestFont);
        	 g.setColor(Color.WHITE);
        	 if (score1 > score2) {
        		 g.drawString("Player 1 has won the first round.", (SCREEN_WIDTH/2)-200, (SCREEN_HEIGHT/2)+100);
        		 g.drawString("Press U to award yourself with the genetic algorithm.", (SCREEN_WIDTH/2)-200, (SCREEN_HEIGHT/2)+120);
        	 } else if (score2 > score1) {
        		 g.drawString("Player 2 has won the first round.", (SCREEN_WIDTH/2)-125, (SCREEN_HEIGHT/2)+120);
        	 } else if (score1 == score2) {
        		 g.drawString("It was a tie game.", (SCREEN_WIDTH/2)-75, (SCREEN_HEIGHT/2)+120);
        	 }
         }
         if (spaceError == true) { // This is where we handle the errors; the errors are if the army is not equal to 100, and its subcases
        	 					   // are handled here, such as being over the limit of the max army size or being <100.
        	 g.setFont(smallestFont);
        	 g.setColor(Color.WHITE);
        	 if (currentPlayer == 1) {
        		 if ((army1.size() < 0) || (space2 < 0)) {
        			 g.drawString("You are over the limit for a valid army.", 70, 370);
        		 } else if (space1 != 0) {
        			 g.drawString("You need at least 100 troops.", 70, 370);
        		 }
        	 } else if (currentPlayer == 2) {
        		 if ((army2.size() < 0) || (space2 < 0)) {
        			 g.drawString("You are over the limit for a valid army.", 70, 370);
        		 } else if (space2 != 0) {
        			 g.drawString("You need at least 100 troops.", 70, 370);
        		 }
        	 }
         }
         // this variable, cursorTrackSelection, tracks the position of the arrow within the game.
         if (cursorTrackSelection == 1) {
            g.drawImage(arrow, 40, 310, arrowWidth, arrowHeight, null);
         } else if (cursorTrackSelection == 2) {
            g.drawImage(arrow, 180, 310, arrowWidth, arrowHeight, null);
         } else if (cursorTrackSelection == 3) {
            g.drawImage(arrow, 330, 310, arrowWidth, arrowHeight, null);
         } else if (cursorTrackSelection == 4) {
            g.drawImage(arrow_flipped, 85, 434, arrowWidth, arrowHeight, null);
         }
         
         // The values of the soldiers, tanks, and cavalry are displayed here.
         // They are converted into Integers and then into Strings in order for them to be drawn.
         String soldiers1_display = soldiers1.toString();
         String tanks1_display = tanks1.toString();
         String cavalry1_display = cavalry1.toString();
         
         String soldiers2_display = soldiers2.toString();
         String tanks2_display = tanks2.toString();
         String cavalry2_display = cavalry2.toString();
         
         Integer soldiers1_equiv = soldiers1;
         Integer tanks1_equiv = tanks1 * 10;
         Integer cavalry1_equiv = cavalry1 * 5;
         
         Integer soldiers2_equiv = soldiers2;
         Integer tanks2_equiv = tanks2 * 10;
         Integer cavalry2_equiv = cavalry2 * 5;
         
         space1 = 100 - (soldiers1_equiv + tanks1_equiv + cavalry1_equiv);
         space2 = 100 - (soldiers2_equiv + tanks2_equiv + cavalry2_equiv);
         
         g.setColor(Color.WHITE);
         g.setFont(smallestFont);
         
         if (currentPlayer == 1) {
        	 g.drawString("SPACE LEFT: " + space1, 350, 425);
        	 g.setFont(bigFont);
             g.drawString(soldiers1_display, 100, 340);
             g.drawString(tanks1_display, 245, 340);
             g.drawString(cavalry1_display, 385, 340);
             g.setFont(smallestFont);
             g.drawString("TROOPS: " + Integer.valueOf(army1.size()).toString(), 350, 415);
             g.drawString(Integer.valueOf(army1.getTotalAttack()).toString(), 350, 405);
         } else if (currentPlayer == 2) {
        	 g.drawString("SPACE LEFT: " + space2, 350, 425);
        	 g.setFont(bigFont);
             g.drawString(soldiers2_display, 100, 340);
             g.drawString(tanks2_display, 245, 340);
             g.drawString(cavalry2_display, 385, 340);
             g.setFont(smallestFont);
             g.drawString("TROOPS: " + Integer.valueOf(army2.size()).toString(), 350, 415);
             g.drawString(Integer.valueOf(army2.getTotalAttack()).toString(), 350, 405);
         }
         
         if (cloudsXPos < 600) {
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            cloudsXPos++;
         } else {
            cloudsXPos = -850;
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
         }

      } else if (screenName.equals("howto")) {
         g.drawImage(howtobg, 0, 0, w, h, null);
         g.drawImage(arrow_flipped, 170, 455, arrowWidth, arrowHeight, null);
         if (cloudsXPos < 600) {
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
            cloudsXPos++;
         } else {
            cloudsXPos = -850;
            g.drawImage(clouds, cloudsXPos, cloudsYPos, 800, 110, null);
         }
      } else if (screenName.equals("airfight")) {
    	 if (loadUp < 4) {
    		 g.drawImage(minigame1_bg, 0, 0, minigame1_bg.getWidth(null), h, null);
    	 } else {
    		 if (currentPlayer == 1) {
    			 // This is the background of Game 1.
    			 g.drawImage(minigame1_bg_terrain, 0, 0, w, h, null);
    		 } else {
    			 // This is where Game 2 starts.
    			 musicOn = true;
    			 
    			 // This sets the background of the game to be black, so that the stars and mountains are visible.
    			 g.setColor(Color.BLACK);
    			 g.fillRect(0, 0, getWidth(), getHeight());
    			 
    			 for (int p=0; p<stars.size(); p++) { // Handles the painting of stars.
           			g.drawImage(star, stars.get(p).getX(), stars.get(p).getY(), (star.getWidth(null) / 2), (star.getHeight(null) / 2), null);
           		 }
    			 
    			 mountain = new Mountain(x1, y1, Color.WHITE); // Prints the mountain terrain, defined by the X and Y arrays instantiated in the beginning of the game.
          		 hidden = new Mountain(x2, y2, Color.BLACK); // There is a white outline on the mountain terrain, but I wanted it to be hidden so that it "merged" with the ground.
          		 helicopterOutline = new Rectangle2D.Double(heliX, heliY, (heli.getWidth(null) / 18), (heli.getHeight(null) / 18)); // This creates the Rectangle2D.Double outline of the helicopter, so that it can detect collisions.
          		 
          		 for (int u=0; u<towers.size(); u++) { // Painting the towers!!!
          			 towers.get(u).paint(g);
          		 }
          		 
          		 // Draws both the mountain and hidden border.
          		 mountain.drawPolygon(g);
          		 hidden.drawPolygon(g);
          		 
          		 // Draws the hidden helicopter outline; it is black so that it is not visible.
          		 g.setColor(Color.BLACK);
          		 g.drawRect((int)helicopterOutline.getX(), (int)helicopterOutline.getY(), (heli.getWidth(null)/18), (heli.getHeight(null)/18));
          		 
          		 mountain.fillPolygon(g, Color.BLACK);
          		 g.drawImage(heli, heliX, heliY, (heli.getWidth(null) / 18), (heli.getHeight(null) / 18), null);
          		 
          		 g.setFont(smallestFont);
          		 g.setColor(Color.WHITE);
          		 
          		 // Statistics for Game 2 are shown here: score, time, tower, fuels, and missiles.
          		 g.drawString("Score: " + score2.toString(), 15, 30);
          		 g.drawString("Time: " + clock.toString() + "sec", 15, 40);
          		 
          		 g.drawString("Towers: " + Integer.valueOf(towers.size()).toString() + "/10", 350, 30);
          		 g.drawString("Fuel: " + fuel.toString(), 350, 40);
          		 g.drawString("Missiles: " + Integer.valueOf(missiles.size()).toString(), 350, 50);
          		 
          		 // These are where the missiles are painted, along with the collision method between the missile and the helicopter outline.
          		 for (int n=0; n<missiles.size(); n++) {
          			 if (missiles.get(n) == null) {
          				 continue;
          			 }
          			 missiles.get(n).paint(g);
          			 //g.setColor(Color.WHITE);
          			 g.setColor(Color.BLACK);
          			 g.drawRect((int)missiles.get(n).getX(), (int)missiles.get(n).getY(), missiles.get(n).getWidth(), missiles.get(n).getHeight());
          			 missiles.get(n).move(MISSILE_SPEED); // in the TankBall class, when the direction is set as left, 5 will result in -5
          			 
          			 if (helicopterOutline.intersects(missiles.get(n).getX(), missiles.get(n).getY(), missiles.get(n).getWidth(), missiles.get(n).getHeight())) {
          				 fuel-=10;
          				 g.setColor(Color.WHITE);
          				 g.setFont(bigFont);
          				 //g.drawString("COLLISION", (SCREEN_WIDTH/2), (SCREEN_HEIGHT/2));
          			 }
          			 if (missiles.get(n).getX() < -10) {
          				 missiles.remove(n);
          			 }
          			 
          		 }
          		 // These are where the bombs are painted, along with the collision method between the bomb and the towers.
          		 for (int i=0; i<bombs.size(); i++) {
          			 if (bombs.get(i) == null) {
        	 			continue;
          			 } 
          			 bombs.get(i).paint(g);
          			 bombs.get(i).moveY(5);
        	 			
          			 for (int j=0; j<towers.size(); j++) {
          				 //System.out.println("Bomb width: " + bombs.get(i).getWidth());
          				 //System.out.println("Bomb height: " + bombs.get(i).getWidth());
          				 if (towers.get(j).intersects(bombs.get(i).getX(), bombs.get(i).getY(), bombs.get(i).getWidth(), bombs.get(i).getHeight())) {
          					 score2+=10;
          					 towers.remove(j);
          				 }
          			 }
          			 if (bombs.get(i).getY() > 600) {
          				 bombs.remove(i);
          			 }
          		 }
          		 // The mountain is re-drawn and re-filled so that the bombs do not overlap and paint over the background.
          		 mountain.drawPolygon(g);
          		 mountain.fillPolygon(g, Color.BLACK);
    		 }
    	 }
    	 if (runTimer == true) {
    		 if (cloudsXPos < 600) {
    			 g.drawImage(clouds_sunset, cloudsXPos, cloudsYPos, 800, 110, null);
    			 cloudsXPos++;
    		 } else {
    			 cloudsXPos = -850;
    			 g.drawImage(clouds_sunset, cloudsXPos, cloudsYPos, 800, 110, null);
    		 }
    	 }
         int tanksWidth = tank_enemy.getWidth(null) / 4;
         int tanksHeight = tank_enemy.getHeight(null) / 4;
         if (runTimer == true)
        	 if (tanksXPos < 600) {
        		 g.drawImage(tank_enemy, tanksXPos, tanksYPos, tanksWidth, tanksHeight, null);
        		 tanksXPos+=2;
        	 } else {
        		 tanksXPos = -850;
        		 g.drawImage(tank_enemy, tanksXPos, tanksYPos, tanksWidth, tanksHeight, null);
        	 }
         g.setColor(Color.WHITE);
         
         // these actions will be triggered by the timer
         
         int xPos2 = 40;
         int xPos3 = 60;
         int xPos4 = 80;
         
         // These are where the texts and load-up screens are displayed, for each mini-game.
         if (loadUp == 1) {
            g.setFont(bigFont);
            if (currentPlayer == 1) {
            	g.drawString("MINIGAME #1", 150, 220);
            } else if (currentPlayer == 2) {
            	g.drawString("MINIGAME #2", 150, 220);
            }
         } else if (loadUp == 2) {
            g.setFont(smallFont);
            if (currentPlayer == 1) { // Mini-game #1 for player #1
            	g.drawString("Your army is needed at the battlefront,", xPos2, 180);
            	g.drawString("requiring your expertise and protection", xPos2, 210);
            	g.drawString("against the Nazi war tanks that are on", xPos2, 240);
            	g.drawString("their way towards you in Stalingrad.", xPos2, 270);
            } else if (currentPlayer == 2) { // Mini-game #2 for player #2
            	g.drawString("You are piloting a Nazi bombing,", xPos4, 180);
            	g.drawString("helicopter at night, attacking.", xPos4, 210);
            	g.drawString("hidden Soviet bases in order to", xPos4, 240);
            	g.drawString("clear a land invasion in the day.", xPos4, 270);
            }
         } else if (loadUp == 3) {
            g.setFont(smallFont);
            if (currentPlayer == 1) { // Mini-game #1 for player #1
            	g.drawString("You are the first line of defense", xPos3, 180);
            	g.drawString("and must protect sufficiently against", xPos3, 210);
            	g.drawString("from the German troops incoming", xPos3, 240);
            	g.drawString("towards your way.", xPos3, 270);
            } else { // Mini-game #2 for player #2
            	g.drawString("Aim accurately and be aware", xPos4, 180);
            	g.drawString("of the missiles incoming and", xPos4, 210);
            	g.drawString("dodge them. Good luck!", xPos4, 240);
            }
         } else if (loadUp == 4) {
        	 // Here is where Game 1/mini-game1 is painted.
        	 g.setFont(smallFont);
        	 if (currentPlayer == 1) {
        		g.drawString("PROTECTING THE CARGO", 10, 25);
        	 
        	 	g.drawString("Time: " + clock.toString() + " s", 380, 30);
        	 	g.drawString("Score: " + score1.toString() + " |", 250, 30);
        	 	g.setFont(smallestFont);
        	 
        	 	String healthPrint = health.toString() + "/100";
        	 	g.drawString(healthPrint, 10, 60);
        	 
        	 	int tankTVWidth = tank_top_view.getWidth(null) / 4;
        	 	int tankTVHeight = tank_top_view.getHeight(null) / 4;
        	 	int tankTVWidth2 = tank_top_view_u.getWidth(null) / 4;
        	 	int tankTVHeight2 = tank_top_view_d.getHeight(null) / 4;
        	 
        	 	int healthBarWidth = healthbar.getWidth(null) / 2;
        	 	int healthBarHeight = healthbar.getHeight(null) / 2;
             
        	 	int enemyTankW = tank_top_view_enemy_l.getWidth(null) / 4;
        	 	int enemyTankH = tank_top_view_enemy_l.getHeight(null) / 4;
 
        	 	g.drawImage(healthbar, 10, 30, healthBarWidth, healthBarHeight, null); // border
        	 	if ((healthWidth <= 0) || (health <= 0)) {
        	 		loadUp = 5;
        	 	} else {
        	 		g.drawImage(healthbar_green, 10, 30, healthWidth, healthBarHeight, null);
        	 	}
        	 	for (int m=0; m<enemyTanks.size(); m++) {
        	 		if (enemyTanks.get(m) == null) {
        	 			continue;
        	 		}
        	 		enemyTanks.get(m).move(TANK_SPEED);		 
        	 		g.drawImage(tank_top_view_enemy_l, enemyTanks.get(m).getX(), enemyTanks.get(m).getY(), enemyTankW, enemyTankH, null);
        	 		if ((enemyTanks.get(m).getX() < -10) || (user.collides(enemyTanks.get(m)))) {
        	 			health-=5;
        	 			healthWidth-=7;
        	 			enemyTanks.remove(m);
        	 		}
        	 	}
        	 // print fired TankBalls here.
        	 	for (int i=0; i<tankballs.size(); i++) {
        	 		if (tankballs.get(i) == null) {
        	 			continue;
        	 		} 
        	 		tankballs.get(i).move(TANKBALL_SPEED);
        	 		if (direction.equals("R")) {
        	 			g.drawImage(tankballs.get(i).getImg(), tankballs.get(i).getX()+25 , tankballs.get(i).getY()+8, tankballs.get(i).getWidth(), tankballs.get(i).getHeight(), null);
        	 		} else if (direction.equals("L")) {
        	 			g.drawImage(tankballs.get(i).getImg(), tankballs.get(i).getX(), tankballs.get(i).getY()+7, tankballs.get(i).getWidth(), tankballs.get(i).getHeight(), null);
        	 		} else if (direction.equals("U")) {
        	 			g.drawImage(tankballs.get(i).getImg(), tankballs.get(i).getX()+7, tankballs.get(i).getY()-2, tankballs.get(i).getWidth(), tankballs.get(i).getHeight(), null);
        	 		} else if (direction.equals("D")) {
        	 			g.drawImage(tankballs.get(i).getImg(), tankballs.get(i).getX()+7, tankballs.get(i).getY()+25, tankballs.get(i).getWidth(), tankballs.get(i).getHeight(), null);
        	 		}
        	 		for (int j=0; j<enemyTanks.size(); j++) {
        	 			if (tankballs.get(i).collides(enemyTanks.get(j)) == true) {
        	 				enemyTanks.remove(j);
        	 				tanksDestroyed1++;
        	 				score1+=10;
        	 			}
        	 		}
        	 		if (tankballs.get(i).getX() > 510) { // this is to minimize space that the game tanks
        	 			tankballs.remove(i);
        	 		} else if (tankballs.get(i).getX() < -10) {
        	 			tankballs.remove(i);
        	 		} else if (tankballs.get(i).getY() < 95) {
        	 			tankballs.remove(i);
        	 		} else if (tankballs.get(i).getY() > 540) {
        	 			tankballs.remove(i);
        	 		}	
        	 	}
        	 	if (enemyTanks.size() <= 1) {
           	  	  	TANK_SPEED--;
           	  	  	for (int z=0; z<INIT_TANKS; z++) {
           	  	  		int randomNum = ThreadLocalRandom.current().nextInt(60, 460 + 1);
           	  	  		int randomNumX = ThreadLocalRandom.current().nextInt(520, 650 + 1);
           	  	  		enemyTanks.add(new Tank("tank", tankDamage, tankSpace, randomNumX, randomNum));
           	  	  	}
        	 	}
        	 	if (direction.equals("R")) {
        	 		g.drawImage(tank_top_view, tankXPos, tankYPos, tankTVWidth, tankTVHeight, null);
        	 	} else if (direction.equals("L")) {
        	 		g.drawImage(tank_top_view_l, tankXPos, tankYPos, tankTVWidth, tankTVHeight, null);
        	 	} else if (direction.equals("U")) {
        	 		g.drawImage(tank_top_view_u, tankXPos, tankYPos, tankTVWidth2, tankTVHeight2, null);
        	 	} else if (direction.equals("D")) {
        	 		g.drawImage(tank_top_view_d, tankXPos, tankYPos, tankTVWidth2, tankTVHeight2, null);
        	 	}
        	 } else {
        		 // ...
        	 }
         } else if (loadUp == 5) {
        	if (currentPlayer == 1) {
        		g.drawImage(bg_overlay, 0, 0, (bg_overlay.getWidth() / 2), (bg_overlay.getHeight() / 2), null);
        		g.setFont(bigFont);
        		g.setColor(Color.WHITE);
        		g.drawString("Game over.", 160, 240);
         		g.drawString("Score: " + score1, 160, 280);
         		g.setFont(smallestFont);
         		g.drawString("Press enter for Player 2 to begin playing.", 100, 310);
         		scores_1.add(score1);
         		
         		mediaPlayer.stop();
         		mediaPlayer2.stop();
         	} else if (currentPlayer == 2) {
         		g.drawImage(bg_overlay, 0, 0, (bg_overlay.getWidth() / 2), (bg_overlay.getHeight() / 2), null);
        		g.setFont(bigFont);
        		g.setColor(Color.WHITE);
        		g.drawString("Game over.", 160, 240);
         		g.drawString("Score: " + score2, 160, 280);
         		g.setFont(smallestFont);
         		g.drawString("Press enter for Player 1 to resume playing.", 100, 310);
         		scores_2.add(score2);
         		
         		mediaPlayer3.stop();
         	}
         }
      }		
   } 
   
   // Here is where the KeyListener comes into action with the graphics and games.
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP) { 
    	 if (screenName.equals("troop_selection")) {
    		 if (cursorTrackSelection == 4) {
    			 cursorTrackSelection--;
    		 }
    		 if (cursorTrackSelection == 1) {
    			 if (currentPlayer == 1) {
    				 soldiers1++;
    				 army1.addTroop(new Troop("Soldier", soldierDamage, soldierSpace));
    			 } else {
    				 army2.addTroop(new Troop("Soldier", soldierDamage, soldierSpace));
    				 soldiers2++;
    			 }
    		 }
    		 if (cursorTrackSelection == 2) {
    			 if (currentPlayer == 1) {
    				 tanks1++;
    				 army1.addTroop(new Troop("Tank", tankDamage, tankSpace));
    			 } else {
    				 army2.addTroop(new Troop("Tank", tankDamage, tankSpace));
    				 tanks2++;
    			 }
    		 }
    		 if (cursorTrackSelection == 3) {
    			 if (currentPlayer == 1) {
    				 cavalry1++;
    				 army1.addTroop(new Troop("Cavalry", cavalryDamage, cavalrySpace));
    			 } else {
    				 army2.addTroop(new Troop("Cavalry", cavalryDamage, cavalrySpace));
    				 cavalry2++;
    			 }
    		 }	
    	 } else if (screenName.equals("airfight") && (loadUp == 4)) {
        	 direction = "U";
        	 if (currentPlayer == 1) {
        		 if (tankYPos - 5 >= 50) {
        			 tankYPos-=5;
        		 }
        	 } else if (currentPlayer == 2) {
        		 heliY+=-5;
        		 fuel--;
        		 
        	 }
         } else if (screenName.equals("airfight") && (loadUp == 5)) {
        	 // ... disable
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
    	 if (screenName.equals("troop_selection")) {
    		 if (cursorTrackSelection == 1) {
    			 if ((currentPlayer == 1) && (soldiers1 > 0)) {
    				 soldiers1--;
    				 boolean foundSoldier = false;
    				 int a = 0;
    				 while (foundSoldier == false) {
    					 if (army1.getTroop(a).getType().equals("Soldier")) {
    						 foundSoldier = true;
    						 army1.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 } else if ((currentPlayer == 2) && (soldiers2 > 0)) {
    				 soldiers2--;
    				 boolean foundSoldier = false;
    				 int a = 0;
    				 while (foundSoldier == false) {
    					 if (army2.getTroop(a).getType().equals("Soldier")) {
    						 foundSoldier = true;
    						 army2.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 }
    		 }
    		 if (cursorTrackSelection == 2) {
    			 if ((currentPlayer == 1) && (tanks1 > 0)) {
    				 tanks1--;
    				 boolean foundTank = false;
    				 int a = 0;
    				 while (foundTank == false) {
    					 if (army1.getTroop(a).getType().equals("Tank")) {
    						 foundTank = true;
    						 army1.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 } else if ((currentPlayer == 2) && (tanks2 > 0)) {
    				 tanks2--;
    				 boolean foundTank = false;
    				 int a = 0;
    				 while (foundTank == false) {
    					 if (army2.getTroop(a).getType().equals("Tank")) {
    						 foundTank = true;
    						 army2.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 }
    		 }
    		 if (cursorTrackSelection == 3) {
    			 if ((currentPlayer == 1) && (cavalry1 > 0)) {
    				 cavalry1--;
    				 boolean foundCavalry = false;
    				 int a = 0;
    				 while (foundCavalry == false) {
    					 if (army1.getTroop(a).getType().equals("Cavalry")) {
    						 foundCavalry = true;
    						 army1.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 } else if ((currentPlayer == 2) && (cavalry2 > 0)) {
    				 cavalry2--;
    				 boolean foundCavalry = false;
    				 int a = 0;
    				 while (foundCavalry == false) {
    					 if (army2.getTroop(a).getType().equals("Cavalry")) {
    						 foundCavalry = true;
    						 army2.removeTroop(a);
    					 } else {
    						 a++;
    					 }
    				 }
    			 }
    		 }
    	 } else if (screenName.equals("airfight") && (loadUp == 4)) {
    		 direction = "D";
    		 if (currentPlayer == 1) {
    			 if (tankYPos + 5 <= 480) {
    				 tankYPos+=5;
    			 }
    	 	 } else if (currentPlayer == 2) {
    	 		 heliY+=5;
    	 		 fuel--;
    	 	 }
         } else if (screenName.equals("airfight") && (loadUp == 5)) {
        	 // ...
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
         if (currentClicked.equals("about") && screenName.equals("home")) {
            currentClicked = "start";
         } else if (currentClicked.equals("howto")  && screenName.equals("home")) {
            currentClicked = "about";
         }
         if (currentCountry.equals("france")) {
            currentCountry = "us";
         } else if (currentCountry.equals("us")) {
            currentCountry = "ussr";
         }
         if (screenName.equals("troop_selection")) {
        	 if (cursorTrackSelection == 3) {
        		 cursorTrackSelection--;
        	 } else if (cursorTrackSelection == 2) {
        		 cursorTrackSelection--;
        	 } else if (cursorTrackSelection == 4) {
        		 cursorTrackSelection--;
        	 }
         } else if (screenName.equals("airfight") && (loadUp == 4)) {
        	 direction = "L";
        	 if (currentPlayer == 1) {
        		 tankXPos-=5;
        	 } else if (currentPlayer == 2) {
        		 heliX-=5;
        		 fuel--;
        		 isPressed = true;
        		 int origPos = stars.get(closestToLeft).getX();
         		 if (((heliX >= 250) && (isPressed == true)) || (stars.get(closestToLeft).getX() >= origPos)) {
         			hidden.translate(5, 0);
         			if (x1[0]- 5 < 0) {
         				for (int b=0; b<stars.size(); b++) {
         					stars.get(b).changeX(5);
         				}
         				for (int r=0; r<towers.size(); r++) {
         					towers.get(r).moveX(5);
         				}
         				for (int k=0; k<bombs.size(); k++) {
         					bombs.get(k).moveX(5);
         				}
         				for (int j=0; j<x1.length; j++ ) {
         					x1[j]+=5;
         				}
         			}
         		 }
        	 }
         } else if (screenName.equals("airfight") && (loadUp == 5)) {
        	 // ... disable
         }
         lastClicked = "left";          
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
         if (currentClicked.equals("start")  && screenName.equals("home")) {
            currentClicked = "about";
         } else if (currentClicked.equals("about")  && screenName.equals("home")) {
            currentClicked = "howto";
         }
         if (currentCountry.equals("ussr")) {
            currentCountry = "us";
         } else if (currentCountry.equals("us")) {
            currentCountry = "france";
         }
         if (cursorTrackSelection == 1) {
            cursorTrackSelection++;
         } else if (cursorTrackSelection == 2) {
            cursorTrackSelection++;
         } else if (cursorTrackSelection == 3) {
            cursorTrackSelection++;
         }
         if (screenName.equals("airfight") && (loadUp == 4)) {
        	 direction = "R";
        	 if (currentPlayer == 1) {
        		 tankXPos+=5;
        	 } else {
        		fuel--;
        		if ((heliX <= 250) || (x1[48] - 5 <= 500)) {
        			heliX+=5;
        		}
        		isPressed = true;
        		if ((heliX >= 250) && (isPressed == true))  {
        			hidden.translate(-5, 0);
        			if (x1[48] - 5 > 500) {
        				for (int b=0; b<stars.size(); b++) {
        					stars.get(b).changeX(-5);
        				}
        				for (int r=0; r<towers.size(); r++) {
         					towers.get(r).moveX(-5);
         				}
        				for (int k=0; k<bombs.size(); k++) {
         					bombs.get(k).moveX(-5);
         				}
        				for (int j=0; j<x1.length; j++ ) {
        					x1[j] -=5;
        				}
        			}
        		}
        	 }
         } else if (screenName.equals("airfight") && (loadUp == 5)) {
        	 // ... disable
         }
         lastClicked = "right";
      }
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         if (currentClicked.equals("start") && (!screenName.equals("airfight"))) {
        	if (currentPlayer == 1) {
        		screenName = "selection";
        	}
         } else if (currentClicked.equals("about")) {
            screenName = "about";
         } else if (currentClicked.equals("howto")) {
            screenName = "howto";
         } else if (screenName.equals("troop_selection")) {
            // ... do nothing
         } else if (screenName.equals("airfight")) {
        	 if ((loadUp < 4) && (loadUp != 3)) {
        		 if (loadUp == 1) {
        			 textTimerValue = 500;
        		 } else if (loadUp == 2) {
        			 textTimerValue = 2000;
        		 }
        		 loadUp++;
        	 } else if (loadUp == 3) {
        		 textTimerValue = 3000;
        		 runTimer = false;
        		 loadUp++;
        	 } else if ((loadUp == 5) && (currentPlayer == 1)) {
        		 currentPlayer = 2;
        		 turns++;
        		 screenName = "troop_selection";
        	 } else if ((loadUp == 5) && (currentPlayer == 2)) {
        		 currentPlayer = 1;
        		 screenName = "troop_selection";
        		 turns++;
        	 } else if (loadUp == 4) {
        		 loadUp++;		 
        	 }
         }
            
      }
      if (e.getKeyCode() == KeyEvent.VK_U) {
    	  if (screenName.equals("troop_selection")) {
    		  if ((score2 > score1) && (currentPlayer == 2) && (turns > 1)) {
    			  army2 = ArmyPack.processArmy(army2);
    		  } else if ((score1 > score2) && (currentPlayer == 1) && (turns > 1)) {
    			  army1 = ArmyPack.processArmy(army1);
    		  }
    	  }
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
         if (screenName.equals("airfight") && (loadUp == 4)) {
        	 if (currentPlayer == 1) {
        		 tankballs.add(new TankBall("", tankDamage, 0, direction, tankXPos, tankYPos));
        	 } else if (currentPlayer == 2) {
        		 bombs.add(new Bomb(10, 10, heliX+10, heliY+5, Color.WHITE));
        		 fuel-=20;
        	 }
         } else if (screenName.equals("airfight") && (loadUp == 5)) {
        	 // do nothing...
         }
      }
      if (screenName.equals("troop_selection") && (e.getKeyCode() == KeyEvent.VK_S)) {
    	 if (currentPlayer == 1) {
    		 // Potential errors that are covered here...
    		 // if the army is empty, if space is a negative number, or if space is < 100.
    		 if ((army1.size() == 0) || (space1 < 0) || (space1 != 0)) {
    			 spaceError = true;
    		 } else {
    			 spaceError = false;
    			 loadUp = 1;
    			 runTimer = true; // starts sequencing again
    			 textTimerValue = 0; // resets the render() time variable
    			 screenName = "airfight";
    		 }
    	 } else if (currentPlayer == 2) {
    		 if ((army2.size() == 0) || (space2 < 0) || (space2 != 0)) {
    			 spaceError = true;
    		 } else {
    			 spaceError = false;
    			 loadUp = 1;
    			 runTimer = true; // starts sequencing again
    			 textTimerValue = 0; // resets the render() time variable
    			 screenName = "airfight";
    		 }
    	 }
      }
      if ((e.getKeyCode() == KeyEvent.VK_M) && (musicOn == true)) {
         musicOn = false;
         mediaPlayer.stop();
         mediaPlayer2.stop();
         mediaPlayer3.stop();
      }
      if ((e.getKeyCode() == KeyEvent.VK_M) && (musicOn == false)) {
         musicOn = true;
         if (!screenName.equals("airfight")) {
            mediaPlayer.setAutoPlay(true);
         } else {
            mediaPlayer2.setAutoPlay(true);
         }
      }
   }
   
   public void keyReleased(KeyEvent e) {
      if ((screenName.equals("troop_selection")) && (e.getKeyCode() == KeyEvent.VK_E)) {
    	 if (currentPlayer == 1)
    		 screenName = "selection";
      } else if (screenName.equals("airfight")) {
    	  // ...
      } else  if ((currentClicked.equals("about")) && (e.getKeyCode() == KeyEvent.VK_E)) {
         screenName = "home";
      } else if ((currentClicked.equals("start")) && (e.getKeyCode() == KeyEvent.VK_E)) {
         screenName = "home";
      } else if ((currentClicked.equals("howto")) && (e.getKeyCode() == KeyEvent.VK_E)) {
         screenName = "home";
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    	  isPressed = false;
      }
      if ((screenName.equals("selection")) && (e.getKeyCode() == KeyEvent.VK_Y)) {
         screenName = "troop_selection";
         if (currentCountry.equals("USSR")) {
        	 army1.changeArmy(1);
         } else if (currentCountry.equals("US")) {
        	 army1.changeArmy(2);
         } else {
        	 army1.changeArmy(3);
         }
      }
   }
   public void keyTyped(KeyEvent e) {
      // ... nothing here. This is just to fulfill the interface's abstract methods.
   }
   public void render() { // taken from the Asteroids game to help with rendering frame rate
                          //renders # of frames in the background then shows them in order
                          //the parameter is the number of frames, that are cycled through
      createBufferStrategy(2);
      strategy = getBufferStrategy();
      Graphics g = null;
      do {
         try{
            g =  strategy.getDrawGraphics();

         } finally {
            paint(g);
         }
         strategy.show();
         g.dispose();
      } while (strategy.contentsLost());
      Toolkit.getDefaultToolkit().sync();
   }

   public static void main(String[] args) throws IOException {
      new Main();  
  }
}