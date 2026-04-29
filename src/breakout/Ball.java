package breakout;

import engine.Actor;
import engine.Sound;
import engine.World;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Actor{
	
	private Sound bounceSound = new Sound("breakoutresources/ball_bounce.wav");
	private Sound brickSound = new Sound("breakoutresources/brick_hit.wav");
	
	double dx;
	double dy; 
	
	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/Ball.png").toString();
		Image img = new Image(path);
		this.setImage(img);
		dx = 2;
		dy = 2;
	}

	@Override
	public void act(long now) {
		super.move(dx, dy);
		
		World world = getWorld();
	    if (world == null) return;
	    
	    BallWorld bworld = (BallWorld) world;
		
		if (getX() <= 0 || getX()+getWidth() >= world.getWidth()) {
	        dx *= -1;
	        bounceSound.play();
	    }

	    if (getY() <= 35 || getY()+getHeight() >= world.getHeight()) {
	        dy *= -1;
	        bounceSound.play();
	    }
		
	    if (getOneIntersectingObject(Paddle.class) != null) {
	    	dy *= -1; 
	    	bounceSound.play();
	    }
	    
	    if (getOneIntersectingObject(Brick.class) != null) {
	    	Brick brick = getOneIntersectingObject(Brick.class);
	    	
	    	if (getX()+getWidth() < brick.getX()+brick.getWidth() && getX() > brick.getX()) {
	    		dy *= -1;
	    	} else if (getY()+getHeight() < brick.getY()+brick.getHeight() && getY() > brick.getY()) {
	    		dx *= -1;
	    	} else {
	    		dx *= -1;
	    		dy *= -1;
	    	}
	    	
	    	getWorld().remove(brick);
	    	
	    	Score s = bworld.getScore(); 
	    	s.setValue(s.getValue() + 100); 
	    	
	    	if (bworld.isPaused()) {
	    	    setX(bworld.getPaddle().getX() + bworld.getPaddle().getWidth() / 2);
	    	    setY(bworld.getPaddle().getY() - 15);
	    	    return;
	    	}
	    	
	    	brickSound.play();
	    }
	   
	    if (getY() >= bworld.getHeight()-getHeight()) {
	    	Score s = bworld.getScore(); 
	    	s.setValue(s.getValue() - 1000); 
	    }
	}
	
	public void setDX(double x) {
		dx = x; 
	}	
	
	public void setDY(double y) {
		dy = y; 
	}

}
