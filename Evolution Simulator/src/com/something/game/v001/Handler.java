package com.something.game.v001;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * @author something
 * A handler is a linked list of GameObjects. It is able to tick and render all 
 * GameObjects it contains.
 */
public class Handler extends LinkedList<GameObject> {

	
	private static final long serialVersionUID = -1191268590539247913L;


	public Handler() {
		super();
	}
	
	
	public void tick() {
		for (int i = 0; i < this.size(); i ++) {
			GameObject object = this.get(i);
			object.tick();
		}
	}


	public void render(Graphics g) {
		for (int i = 0; i < this.size(); i ++) {
			GameObject object = this.get(i);
			object.render(g);
		}
	}
	
}
