package breakout;

import engine.World;

public class BallWorld extends World{
	
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
		
		double centerX = getWidth()/2 - ball.getImage().getWidth()/2;
        double centerY = getHeight()/2 - ball.getImage().getHeight()/2;
        ball.setX(centerX);
        ball.setY(centerY);

        add(ball);
		
        Paddle paddle = new Paddle(); 
        paddle.setX(centerX);
        paddle.setY(centerY/2);
        
        add(paddle);
	}

}
