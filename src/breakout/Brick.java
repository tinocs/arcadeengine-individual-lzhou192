package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Brick extends Actor {

	public Brick() {
		String path = getClass().getClassLoader().getResource("breakoutresources/brick.png").toString();
		Image img = new Image(path);
		this.setImage(img);
	}
	
	public void setColor(int c) {
		if (c != 1 || c != 2) {
			return; 
		}
		
		if (c == 2) {
			String path = getClass().getClassLoader().getResource("breakoutresources/brick2.png").toString();
			Image img = new Image(path);
			this.setImage(img);
		}
		
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}

}
