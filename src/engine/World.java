package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class World extends Pane{
	private AnimationTimer timer;
	private boolean timerRunning; 
	private Set<KeyCode> keysPressed = new HashSet<>();
	private boolean widthInitialized;
	private boolean heightInitialized; 
	private boolean dimensionsInitialized; 
	
	public World() {
		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.doubleValue() > 0 && !widthInitialized) {
		            widthInitialized = true;

		            if (widthInitialized && heightInitialized && !dimensionsInitialized) {
		                dimensionsInitialized = true;
		                onDimensionsInitialized();
		            }
		        }
				
			}
		});

	    heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.doubleValue() > 0 && !heightInitialized) {
		            heightInitialized = true;

		            if (widthInitialized && heightInitialized && !dimensionsInitialized) {
		                dimensionsInitialized = true;
		                onDimensionsInitialized();
		            }
		        }
				
			}
		});

	    sceneProperty().addListener(new ChangeListener<Scene>() {
	        @Override
	        public void changed(ObservableValue<? extends Scene> obs, Scene oldScene, Scene newScene) {
	            if (newScene != null) {
	                requestFocus();
	            }
	        }
	    });

	    setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            keysPressed.add(e.getCode());
	        }
	    });
	    setOnKeyReleased(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            keysPressed.remove(e.getCode());
	        }
	    });

	    timer = new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	            act(now);

	            List<Actor> list = getObjects(Actor.class);
	            for (int i = 0; i < list.size(); i++) {
	                if (list.get(i).getWorld() == World.this) {
	                	list.get(i).act(now);
	                }
	            }
	        }
	    };

	    start();
	    timerRunning = true;
	}
	
	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls){
		List<A> list = new ArrayList<>();
		for(int i = 0; i < getChildren().size(); i++) {
			Node n = getChildren().get(i);
			A a = (A) n; 
			
			if(cls.isInstance(a)) {
				list.add(a);
			}
		}
		
		return list;
		
	}
	
	public <A extends Actor> java.util.List<A> getObjectsAt(double x, double y, java.lang.Class<A> cls){
		List<A> list = new ArrayList<>();
		for(int i = 0; i < getChildren().size(); i++) {
			Node n = getChildren().get(i);
			A a = (A) n; 
			
			if(cls.isInstance(a) && a.contains(x, y)) {
				list.add(a);
			}
		}
		
		return list;
		
	}
	
	public boolean isKeyPressed(javafx.scene.input.KeyCode code) {
		return keysPressed.contains(code);
	}
	
	public void start() {
		timer.start(); 
	}
	
	public void stop() {
		timer.stop();
	}
	
	public boolean isStopped() {
		return !timerRunning; 
	}
	
	public void add(Actor actor) {
		getChildren().add(actor); 
		actor.addedToWorld(); 
	}
	
	public void remove(Actor actor) {
		getChildren().remove(actor); 
	}

	public abstract void act(long now);
	public abstract void onDimensionsInitialized();
}
