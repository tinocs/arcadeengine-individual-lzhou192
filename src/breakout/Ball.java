package breakout;

import engine.Actor;
import engine.World;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Actor{
	
	double dx;
	double dy; 
	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/Ball.png").toString();
		Image img = new Image(path);
		this.setImage(img);
		dx = 5;
		dy = 5;
	}

	@Override
	public void act(long now) {
		super.move(dx, dy);
		
		World world = getWorld();
	    if (world == null) return;
		
		if (getX() <= 0 || getX()+getWidth() >= world.getWidth()) {
	        dx *= -1;
	    }

	    if (getY() <= 0 || getY()+getHeight() >= world.getHeight()) {
	        dy *= -1;
	    }
		
	    if (getOneIntersectingObject(Paddle.class) != null) {
	    	dy *= -1; 
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
	    }
	}

}
