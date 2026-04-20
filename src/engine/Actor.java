package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{

	public void move(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	public World getWorld() {
		if (getParent() instanceof World) {
		    return (World) getParent();
		} else {
		    return null;
		}
	}
	
	public double getWidth() {
	    return getBoundsInParent().getWidth();
	}
	
	public double getHeight() {
	    return getBoundsInParent().getHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
		List<A> results = new ArrayList<>();
		World world = getWorld();
		if (world == null) {
		    return results;
		}

		List<A> actors = world.getObjects(cls);
		for(int i = 0; i < actors.size(); i++) {
			if(actors.get(i) != this && getBoundsInParent().intersects(actors.get(i).getBoundsInParent())) {
				results.add(actors.get(i));
			}
		}
		
		return results; 
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		List<A> actors = getIntersectingObjects(cls);
		return actors.size() == 0 ? null : actors.get(0); 
	}
	
	public abstract void act(long now);
	
	public void addedToWorld() {
	}
}
