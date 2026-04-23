package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Paddle extends Actor{

	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image img = new Image(path);
		this.setImage(img);
	}
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	
}
