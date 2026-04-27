package breakout;

import java.util.Scanner;

import engine.Sound;
import engine.World;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BallWorld extends World{
	
	private int level = 1;
	private Score score;
	private int lives = 3;
	private Text livesText;
	private Ball ball; 
	private boolean isPaused = true; 
	private Paddle paddle; 
	
	private Sound loseLifeSound = new Sound("/breakoutresources/lose_life.wav");
	private Sound gameLostSound = new Sound("/breakoutresources/game_lost.wav");
	private Sound gameWonSound = new Sound("/breakoutresources/game_won.wav");
	
	public BallWorld() {
		setPrefSize(600, 400);
	}

	@Override
	public void act(long now) {
		
		if (ball != null && ball.getY() > getHeight()-ball.getHeight()) { 

		    lives--;
		    livesText.setText("Lives: " + lives);

		    if (lives <= 0) {
		    	Breakout.showTitle(); 
		    } else {
		    	resetBall();
		    }
		}
		
		boolean noBricksLeft = true;

		for (Node node : getChildren()) {
		    if (node instanceof Brick) {
		        noBricksLeft = false;
		        break;
		    }
		}
	}
	
	@Override
	public void onDimensionsInitialized() {
		ball = new Ball();
		
		score = new Score();
		
		score.setX(10);
	    score.setY(20);

	    getChildren().add(score);
		
		double centerX = getWidth()/2 - ball.getImage().getWidth()/2;
        double centerY = getHeight()/2 - ball.getImage().getHeight()/2;
        ball.setX(centerX);
        ball.setY(centerY);
        ball.setDX(0);
        ball.setDY(0);

        add(ball);
		
        paddle = new Paddle(); 
        paddle.setX(centerX);
        paddle.setY(centerY*3/2);
        
        add(paddle);
       
        livesText = new Text("Lives: " + lives);
        livesText.setFont(new Font(24));
        livesText.setX(getWidth()/2);
        livesText.setY(20);

        getChildren().add(livesText);
        
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
            	if (e.getCode() == KeyCode.SPACE && isPaused) {
            		ball.setDX(2);
            		ball.setDY(2);
                    isPaused = false;
                }
            }
        });
        
	}
	
	public Score getScore() {
	    return score;
	}
	
	public void loadLevel(int levelNum) {

	    String filename = "/breakoutresources/level" + levelNum + ".txt";

	    try {
	    	
	    	Scanner s = new Scanner(getClass().getResourceAsStream(filename));;
	        int rows = s.nextInt();
	        int cols = s.nextInt();
	        s.nextLine();

	        double brickWidth = 600.0/cols;
	        double brickHeight = 20;

	        for (int r = 0; r < rows; r++) {
	            String line = s.nextLine();

	            for (int c = 0; c < cols; c++) {
	                int type = Character.getNumericValue(line.charAt(c));

	                if (type != 0) {
	                    Brick brick = new Brick();
	                    brick.setColor(type);
	                    brick.setX(c * brickWidth + 30);
	                    brick.setY(r * brickHeight + 50);

	                    add(brick);
	                }
	            }
	        }
	        
	        s.close();

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void resetBall() {
	    isPaused = true;

	    ball.setDX(0);
	    ball.setDY(0);
	    double centerX = getWidth()/2 - ball.getImage().getWidth()/2;
        double centerY = getHeight()/2 - ball.getImage().getHeight()/2;
        ball.setX(centerX);
        ball.setY(centerY);
	}
	
	public int getLevel() {
		return level; 
	}

	public boolean isPaused() {
	    return isPaused;
	}

	public void setPaused(boolean paused) {
	    isPaused = paused;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
}
