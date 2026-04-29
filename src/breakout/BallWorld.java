package breakout;

import java.util.Scanner;

import engine.Sound;
import engine.World;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BallWorld extends World{
	
	private int level = 1;
	private int maxLevel = 2;
	private Score score;
	private int lives = 3;
	private Text livesText;
	private Ball ball; 
	private boolean isPaused = true; 
	private Paddle paddle; 
	private boolean isGameOver = false;
	private Text message;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private ImageView background;
	
	private Sound loseLifeSound = new Sound("breakoutresources/lose_life.wav");
	private Sound gameLostSound = new Sound("breakoutresources/game_lost.wav");
	private Sound gameWonSound = new Sound("breakoutresources/game_won.wav");
	
	public BallWorld() {
		setPrefSize(600, 400);
		setFocusTraversable(true);
	    requestFocus();
	}

	@Override
	public void act(long now) {
		
		if (ball != null && ball.getY() >= getHeight()-ball.getHeight()) { 

		    lives--;
	    	loseLifeSound.play();
		    livesText.setText("Lives: " + lives);

		    if (lives <= 0) {
		        showGameOver(false);
		        gameLostSound.play();
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
		
		if (level >= maxLevel && noBricksLeft) {
		    gameWonSound.play();
		    showGameOver(true);
		}
		
		if (leftPressed) {
		    paddle.moveLeft();
		}
		if (rightPressed) {
		    paddle.moveRight();
		}
		
        
	}
	
	@Override
	public void onDimensionsInitialized() {
		Image img = new Image("/breakoutresources/background.png");
		background = new ImageView(img);
		
		background.setX((getWidth() - img.getWidth())/2);
		background.setY(35);
		getChildren().add(background);
		
		ball = new Ball();
		
		score = new Score();
		
		score.setX(10);
	    score.setY(30);

	    getChildren().add(score);
		
		double centerX = getWidth()/2;
        double centerY = getHeight()/2;
        ball.setX(centerX - ball.getWidth()/2);
        ball.setY(centerY);
        ball.setDX(0);
        ball.setDY(0);

        add(ball);
		
        paddle = new Paddle(); 
        paddle.setX(centerX-paddle.getWidth()/2);
        paddle.setY(centerY*3/2);
        
        add(paddle);
       
        livesText = new Text("Lives: " + lives);
        livesText.setFont(new Font(24));
        livesText.setX(getWidth()/2);
        livesText.setY(30);

        getChildren().add(livesText);
        
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
            	if (e.getCode() == KeyCode.SPACE && isPaused && !isGameOver) {
            		ball.setDX(2);
            		ball.setDY(2);
                    isPaused = false;
                } else if (isGameOver && e.getCode() == KeyCode.SPACE) {
            		score.setValue(0);
            		lives = 3;
            		if (level >= maxLevel) {
	            		level = 1; 
	                    Breakout.showTitle();
                	} else {
                		level++;
                		loadLevel(level);
                	}	
                } 
            	
            	if (e.getCode() == KeyCode.LEFT) leftPressed = true;
                if (e.getCode() == KeyCode.RIGHT) rightPressed = true;
            }
        });
        
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
            	if (e.getCode() == KeyCode.LEFT) leftPressed = false;
                if (e.getCode() == KeyCode.RIGHT) rightPressed = false;
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
	                    brick.setX(c * brickWidth + 30 - brick.getWidth()/2);
	                    brick.setY(r * brickHeight + 65);

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
        
        score.setValue(0);
        score.updateDisplay();
	}
	
	public void showGameOver(boolean won) {
	    isGameOver = true;
	    isPaused = true;

	    if (won) {
	    	message = new Text("YOU WIN!");
	    } else {
	    	message = new Text("GAME OVER.");
	    	 
	    }
	    
	    resetBall();
	    message.setX(getWidth()/2 - message.getLayoutBounds().getWidth()/2);
	    message.setY(getHeight()*3/5);
	    message.setFont(new Font(30));

	    getChildren().add(message);
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
	
	public void scroll(double dx) {

	    double newX = background.getX() - dx;

	    double minX = getWidth() - background.getImage().getWidth();
	    double maxX = 0;

	    if (newX >= minX && newX <= maxX) {
	        background.setX(newX);
	    }
	    
	    for (Node node : getChildren()) {
	        if (node != background && !(node instanceof Text) && newX != minX && newX != maxX) {
	            node.setLayoutX(node.getLayoutX() - dx);
	        }
	    }
	}
}
