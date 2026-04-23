package breakout;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor{

	double dx;
	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image img = new Image(path);
		this.setImage(img);
		
		dx = 4; 
	}
	@Override
	public void act(long now) {
		if (getWorld().isKeyPressed(KeyCode.LEFT)) {
			move(-dx, 0);
		}
		
		if (getWorld().isKeyPressed(KeyCode.RIGHT)) {
			move(dx, 0);
		}
		
	}
	
}
