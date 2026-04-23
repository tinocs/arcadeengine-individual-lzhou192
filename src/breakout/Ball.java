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
	}

}
