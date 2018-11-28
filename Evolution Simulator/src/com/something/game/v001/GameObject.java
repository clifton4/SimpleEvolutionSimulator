package com.something.game.v001;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected Handler handler;
	protected int x, y;
	protected ID id;
	
	public GameObject(int x, int y, ID id, Handler handler) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.id = id;
		
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public abstract void render(Graphics g) ;

	public abstract void tick();
	
	public abstract Rectangle getBounds();
	
}
