package breakout;

import engine.World;

public class BallWorld extends World{
	
	private Score score;
	
	public BallWorld() {
		setPrefSize(600, 400);
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
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
        paddle.setY(centerY/2);
        
        add(paddle);
        
        Brick brick = new Brick(); 
        brick.setX(centerX/2);
        brick.setY(centerY*3/4);
        
        add(brick);
        
        Brick brick2 = new Brick(); 
        brick2.setX(centerX*3/4);
        brick2.setY(centerY*3/4);
        
        add(brick2);
        
	}
	
	public Score getScore() {
	    return score;
	}
	

}
