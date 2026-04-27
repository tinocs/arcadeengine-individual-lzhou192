package breakout;

import java.util.Scanner;

import engine.World;
import javafx.scene.Node;

public class BallWorld extends World{
	
	private int level = 1;
	private Score score;
	
	public BallWorld() {
		setPrefSize(600, 400);
	}

	@Override
	public void act(long now) {
		if (score.getValue() < 0) {
			Breakout.showTitle();
			score.setValue(0);
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
		Ball ball = new Ball();
		
		score = new Score();
		
		score.setX(10);
	    score.setY(20);

	    getChildren().add(score);
		
		double centerX = getWidth()/2 - ball.getImage().getWidth()/2;
        double centerY = getHeight()/2 - ball.getImage().getHeight()/2;
        ball.setX(centerX);
        ball.setY(centerY);

        add(ball);
		
        Paddle paddle = new Paddle(); 
        paddle.setX(centerX);
        paddle.setY(centerY*3/2);
        
        add(paddle);
       
        
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
	
	public int getLevel() {
		return level; 
	}

}
